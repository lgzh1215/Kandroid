package kandroid.utils.collection

import kandroid.utils.exception.CatException
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater

@Suppress("UNCHECKED_CAST", "unused", "NOTHING_TO_INLINE")
open class IDDictionary<E : Identifiable> : AbstractMutableSet<E> {

    @Volatile
    private lateinit var root: Any
    private val readOnly: Boolean
    private val name: String

    @JvmOverloads
    constructor(name: String = "") : this(name, INode.newRootNode<E>(), readOnly = false)

    private constructor(name: String, readOnly: Boolean) {
        this.readOnly = readOnly
        this.name = name
    }

    private constructor(name: String, root: Any, readOnly: Boolean) : this(name, readOnly) {
        this.root = root
    }

    companion object {
        private val ROOT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(IDDictionary::class.java, Any::class.java, "root")

        internal val RESTART = Any()
    }

    private interface Leaf<out E : Identifiable> {
        val e: E
    }

    private abstract class Branch<E : Identifiable>

    private class INode<E : Identifiable>(val gen: Any) : Branch<E>() {
        @Volatile lateinit
        var mainNode: MainNode<E>

        constructor(mainNode: MainNode<E>, gen: Any) : this(gen) {
            WRITE_MainNode(mainNode)
        }

        fun WRITE_MainNode(newValue: MainNode<E>) = updater.set(this, newValue)

        fun CAS_MainNode(old: MainNode<E>, n: MainNode<E>) = updater.compareAndSet(this, old, n)

        fun GCAS_READ(ct: IDDictionary<E>): MainNode<E> {
            val m = mainNode
            val prev = m.prev
            return if (prev == null) m else GCAS_Commit(m, ct)
        }

        tailrec fun GCAS_Commit(m: MainNode<E>, ct: IDDictionary<E>): MainNode<E> {
            val ctr = ct.RDCSS_READ_ROOT(true)
            val prev = m.prev ?: return m

            when (prev) {
                is FailedNode -> {
                    return if (CAS_MainNode(m, prev.p)) prev.p else GCAS_Commit(mainNode, ct)
                }
                is MainNode -> {
                    if (ctr.gen == gen && !ct.readOnly) {
                        return if (m.CAS_PREV(prev, null)) m else GCAS_Commit(m, ct)
                    } else {
                        m.CAS_PREV(prev, FailedNode(prev))
                        return GCAS_Commit(mainNode, ct)
                    }
                }
                else -> throw RuntimeException("Should not happen")
            }
        }

        fun GCAS(old: MainNode<E>, n: MainNode<E>, ct: IDDictionary<E>): Boolean {
            n.WRITE_PREV(old)
            if (CAS_MainNode(old, n)) {
                GCAS_Commit(n, ct)
                return n.prev == null
            } else {
                return false
            }
        }

        fun iNode(cn: MainNode<E>): INode<E> {
            val nin = INode<E>(gen)
            nin.WRITE_MainNode(cn)
            return nin
        }

        fun copyToGen(newGen: Any, ct: IDDictionary<E>): INode<E> {
            val nin = INode<E>(newGen)
            val main = GCAS_READ(ct)
            nin.WRITE_MainNode(main)
            return nin
        }

        fun cachedSize(ct: IDDictionary<E>): Int = GCAS_READ(ct).cachedSize(ct)

        companion object {
            private val updater = AtomicReferenceFieldUpdater.newUpdater(INode::class.java, MainNode::class.java, "mainNode")

            internal fun <E : Identifiable> newRootNode(): INode<E> {
                val gen = Any()
                val cn = CNode(0, arrayOf<Branch<E>?>(), gen)
                return INode(cn, gen)
            }
        }
    }

    private class SNode<E : Identifiable>(override val e: E, val id: Int) : Branch<E>(), Leaf<E> {
        fun copyTombed(): TNode<E> = TNode(e, id)
    }

    private abstract class MainNode<E : Identifiable> {
        @Volatile
        var prev: MainNode<E>? = null

        abstract fun cachedSize(ct: Any): Int

        fun CAS_PREV(oldValue: MainNode<E>, newValue: MainNode<E>?) = updater.compareAndSet(this, oldValue, newValue)

        fun WRITE_PREV(newValue: MainNode<E>) = updater.set(this, newValue)

        companion object {
            private val updater = AtomicReferenceFieldUpdater.newUpdater(MainNode::class.java, MainNode::class.java, "prev")
        }
    }

    private class CNode<E : Identifiable>(val bitmap: Int, val array: Array<Branch<E>?>, val gen: Any) : MainNode<E>() {
        @Volatile var size = -1

        override fun cachedSize(ct: Any): Int {
            val currentSize = size
            if (currentSize != -1)
                return currentSize
            else {
                val newSize = computeSize(ct as IDDictionary<E>)
                while (size == -1)
                    CAS_SIZE(-1, newSize)
                return size
            }
        }

        fun computeSize(ct: IDDictionary<E>): Int {
            var i = 0
            var sz = 0

            val offset = 0
            while (i < array.size) {
                val pos = (i + offset) % array.size
                val elem = array[pos]
                if (elem is SNode)
                    sz += 1
                else if (elem is INode)
                    sz += elem.cachedSize(ct)
                i += 1
            }
            return sz
        }

        fun insertedAt(pos: Int, flag: Int, nn: Branch<E>, gen: Any): CNode<E> {
            val len = array.size
            val bmp = bitmap
            val narr = arrayOfNulls<Branch<E>>(len + 1)
            System.arraycopy(array, 0, narr, 0, pos)
            narr[pos] = nn
            System.arraycopy(array, pos, narr, pos + 1, len - pos)
            return CNode(bmp or flag, narr, gen)
        }

        fun updatedAt(pos: Int, nn: Branch<E>, gen: Any): CNode<E> {
            val len = array.size
            val narr = arrayOfNulls<Branch<E>>(len)
            System.arraycopy(array, 0, narr, 0, len)
            narr[pos] = nn
            return CNode(bitmap, narr, gen)
        }

        fun removedAt(pos: Int, flag: Int, gen: Any): CNode<E> {
            val arr = array
            val len = arr.size
            val narr = arrayOfNulls<Branch<E>>(len - 1)
            System.arraycopy(arr, 0, narr, 0, pos)
            System.arraycopy(arr, pos + 1, narr, pos, len - pos - 1)
            return CNode(bitmap xor flag, narr, gen)
        }

        fun renewed(newGen: Any, ct: IDDictionary<E>): CNode<E> {
            var i = 0
            val arr = array
            val len = arr.size
            val newArray = arrayOfNulls<Branch<E>>(len)
            while (i < len) {
                val elem = arr[i]
                if (elem is INode) {
                    val `in` = elem
                    newArray[i] = `in`.copyToGen(newGen, ct)
                } else if (elem is Branch<E>)
                    newArray[i] = elem
                i += 1
            }
            return CNode(bitmap, newArray, newGen)
        }

        fun resurrect(iNode: INode<E>, iNodeMain: Any): Branch<E> {
            if (iNodeMain is TNode<*>) {
                val tn = iNodeMain as TNode<E>
                return tn.copyUntombed()
            } else return iNode
        }

        fun toContracted(lev: Int): MainNode<E> = if (array.size == 1 && lev > 0) {
            if (array[0] is SNode) {
                val sn = array[0] as SNode<E>
                sn.copyTombed()
            } else this
        } else this

        fun toCompressed(ct: IDDictionary<E>, lev: Int, gen: Any): MainNode<E> {
            val bmp = bitmap
            val arr = array
            val tempArray = arrayOfNulls<Branch<E>>(arr.size)
            for (i in arr.indices) { // construct new bitmap
                val sub = arr[i]
                when (sub) {
                    is INode -> {
                        val `in` = sub
                        val iNodeMain = `in`.GCAS_READ(ct)
                        tempArray[i] = resurrect(`in`, iNodeMain)
                    }
                    is SNode -> tempArray[i] = sub
                }
            }

            return CNode(bmp, tempArray, gen).toContracted(lev)
        }

        fun CAS_SIZE(oldValue: Int, newValue: Int) = updater.compareAndSet(this, oldValue, newValue)

        companion object {
            private val updater = AtomicIntegerFieldUpdater.newUpdater(CNode::class.java, "size")

            internal fun <E : Identifiable> dual(x: SNode<E>, xid: Int, y: SNode<E>, yid: Int, lev: Int, gen: Any): CNode<E> {
                val xIndex = xid ushr lev and 0b11111
                val yIndex = yid ushr lev and 0b11111
                val bmp = 1 shl xIndex or (1 shl yIndex)

                if (xIndex == yIndex) {
                    val subINode = INode<E>(gen)
                    subINode.mainNode = dual(x, xid, y, yid, lev + 5, gen)
                    return CNode(bmp, arrayOf<Branch<E>?>(subINode), gen)
                } else {
                    if (xIndex < yIndex)
                        return CNode(bmp, arrayOf<Branch<E>?>(x, y), gen)
                    else
                        return CNode(bmp, arrayOf<Branch<E>?>(y, x), gen)
                }
            }
        }
    }

    private class TNode<E : Identifiable>(override val e: E, val id: Int) : MainNode<E>(), Leaf<E> {
        fun copyUntombed(): SNode<E> = SNode(e, id)

        override fun cachedSize(ct: Any): Int = 1
    }

    private class FailedNode<E : Identifiable>(val p: MainNode<E>) : MainNode<E>() {
        init {
            WRITE_PREV(p)
        }

        override fun cachedSize(ct: Any): Int = throw UnsupportedOperationException()
    }

    private class RDCSS_Descriptor<E : Identifiable>(var old: INode<E>, var expectedMain: MainNode<E>, var new: INode<E>) {
        @Volatile internal var committed = false
    }

    private fun CAS_ROOT(ov: Any, nv: Any): Boolean {
        if (readOnly) {
            throw IllegalStateException("Attempted to modify a read-only snapshot")
        }
        return ROOT_UPDATER.compareAndSet(this, ov, nv)
    }

    private fun RDCSS_READ_ROOT(abort: Boolean = false): INode<E> {
        val r = root
        when (r) {
            is INode<*> -> return r as INode<E>
            is RDCSS_Descriptor<*> -> return RDCSS_Complete(abort)
            else -> throw RuntimeException("Should not happen")
        }
    }

    private tailrec fun RDCSS_Complete(abort: Boolean): INode<E> {
        val v = root
        when (v) {
            is INode<*> -> return v as INode<E>
            is RDCSS_Descriptor<*> -> {
                val desc = v as RDCSS_Descriptor<E>

                val oldValue = desc.old
                val exp = desc.expectedMain
                val newValue = desc.new

                if (abort) {
                    return if (CAS_ROOT(desc, oldValue)) oldValue else RDCSS_Complete(abort)
                } else {
                    val oldMain = oldValue.GCAS_READ(this)
                    if (oldMain === exp) {
                        if (CAS_ROOT(desc, newValue)) {
                            desc.committed = true
                            return newValue
                        } else return RDCSS_Complete(abort)
                    } else {
                        return if (CAS_ROOT(desc, oldValue)) oldValue else RDCSS_Complete(abort)
                    }

                }
            }
            else -> throw RuntimeException("Should not happen")
        }
    }

    private fun RDCSS_ROOT(ov: INode<E>, expectedMain: MainNode<E>, nv: INode<E>): Boolean {
        val desc = RDCSS_Descriptor(ov, expectedMain, nv)
        if (CAS_ROOT(ov, desc)) {
            RDCSS_Complete(false)
            return desc.committed
        } else return false
    }

    private fun INode<E>.cleanReadOnly(tn: TNode<E>, lev: Int, parent: INode<E>, id: Int): Any? {
        if (!this@IDDictionary.readOnly) {
            clean(parent, lev - 5)
            return RESTART
        } else {
            return if (tn.id == id) tn.e else null
        }
    }

    private fun INode<E>.clean(nd: INode<E>, lev: Int) {
        val m = nd.GCAS_READ(this@IDDictionary)
        if (m is CNode) {
            val cn = m
            nd.GCAS(cn, cn.toCompressed(this@IDDictionary, lev, gen), this@IDDictionary)
        }
    }

    private tailrec fun INode<E>.cleanParent(nonLive: Any, parent: INode<E>, id: Int, lev: Int, startgen: Any) {
        val pm = parent.GCAS_READ(this@IDDictionary)

        if (pm is CNode) {
            val cn = pm
            val idx = id.ushr(lev - 5) and 0x1f
            val bmp = cn.bitmap
            val flag = 1 shl idx
            if (bmp and flag != 0) {
                val pos = Integer.bitCount(bmp and flag - 1)
                val sub = cn.array[pos]
                if (sub === this && nonLive is TNode<*>) {
                    val tn = nonLive as TNode<E>
                    val ncn = cn.updatedAt(pos, tn.copyUntombed(), gen).toContracted(lev - 5)
                    if (!parent.GCAS(cn, ncn, this@IDDictionary) && this@IDDictionary.RDCSS_READ_ROOT(false).gen == startgen)
                        cleanParent(nonLive, parent, id, lev, startgen)
                }
            }
        }
    }

    private tailrec fun INode<E>.rec_lookup(id: Int, lev: Int, parent: INode<E>?, startGen: Any): Any? {
        val m = GCAS_READ(this@IDDictionary)

        when (m) {
            is CNode -> {
                val idx = id ushr lev and 0b11111 // (id >>> lev) & 0b11111
                val flag = 1 shl idx              // 1 << idx
                val bmp = m.bitmap
                if (bmp and flag == 0)
                    return null
                else {
                    val pos = if (bmp == -1) idx else Integer.bitCount(bmp and flag - 1)
                    val sub = m.array[pos]
                    when (sub) {
                        is INode -> {
                            if (this@IDDictionary.readOnly || startGen == sub.gen)
                                return sub.rec_lookup(id, lev + 5, this, startGen)
                            else {
                                if (GCAS(m, m.renewed(startGen, this@IDDictionary), this@IDDictionary))
                                    return rec_lookup(id, lev, parent, startGen)
                                else
                                    return RESTART
                            }
                        }
                        is SNode -> {
                            if (sub.id == id)
                                return sub.e
                            else
                                return null
                        }
                    }
                }
            }
            is TNode -> {
                return cleanReadOnly(m, lev, parent!!, id)
            }
        }
        throw RuntimeException("Should not happen")
    }

    private tailrec fun INode<E>.rec_insert(e: E, id: Int, lev: Int, parent: INode<E>?, startgen: Any): Any? {
        val m = GCAS_READ(this@IDDictionary)

        when (m) {
            is CNode -> {
                val idx = id ushr lev and 0b11111 // (id >>> lev) & 0b11111
                val flag = 1 shl idx              // 1 << idx
                val bmp = m.bitmap
                val masE = flag - 1
                val pos = Integer.bitCount(bmp and masE)
                if (bmp and flag != 0) {
                    val cnAtPos = m.array[pos]
                    when (cnAtPos) {
                        is INode -> {
                            val `in` = cnAtPos
                            if (startgen == `in`.gen)
                                return `in`.rec_insert(e, id, lev + 5, this, startgen)
                            else {
                                if (GCAS(m, m.renewed(startgen, this@IDDictionary), this@IDDictionary))
                                    return rec_insert(e, id, lev, parent, startgen)
                                else
                                    return RESTART
                            }
                        }
                        is SNode -> {
                            val sn = cnAtPos
                            if (sn.id == id) {
                                if (GCAS(m, m.updatedAt(pos, SNode(e, id), gen), this@IDDictionary))
                                    return sn.e
                                else
                                    return RESTART
                            } else {
                                val rn = if (m.gen == gen) m else m.renewed(gen, this@IDDictionary)
                                val nn = rn.updatedAt(pos, iNode(CNode.dual(sn, sn.id, SNode(e, id), id, lev + 5, gen)), gen)
                                if (GCAS(m, nn, this@IDDictionary))
                                    return null // None;
                                else
                                    return RESTART
                            }
                        }
                    }
                } else {
                    val rn = if (m.gen == gen) m else m.renewed(gen, this@IDDictionary)
                    val ncnode = rn.insertedAt(pos, flag, SNode(e, id), gen)
                    if (GCAS(m, ncnode, this@IDDictionary))
                        return null
                    else
                        return RESTART
                }
            }
            is TNode -> {
                clean(parent!!, lev - 5)
                return RESTART
            }
        }
        throw RuntimeException("Should not happen")
    }

    private fun INode<E>.rec_delete(id: Int, lev: Int, parent: INode<E>?, startgen: Any): Any? {
        val m = GCAS_READ(this@IDDictionary)

        when (m) {
            is CNode -> {
                val cn = m
                val idx = id.ushr(lev) and 0x1f
                val bmp = cn.bitmap
                val flag = 1 shl idx
                if (bmp and flag == 0)
                    return null
                else {
                    val pos = Integer.bitCount(bmp and flag - 1)
                    val sub = cn.array[pos]
                    val res: Any? = when (sub) {
                        is INode -> {
                            val `in` = sub
                            if (startgen == `in`.gen)
                                `in`.rec_delete(id, lev + 5, this, startgen)
                            else {
                                if (GCAS(cn, cn.renewed(startgen, this@IDDictionary), this@IDDictionary))
                                    rec_delete(id, lev, parent, startgen)
                                else
                                    RESTART
                            }

                        }
                        is SNode -> {
                            val sn = sub
                            if (sn.id == id) {
                                val ncn = cn.removedAt(pos, flag, gen).toContracted(lev)
                                if (GCAS(cn, ncn, this@IDDictionary))
                                    sn.e
                                else
                                    RESTART
                            } else null
                        }
                        else -> throw RuntimeException("Should not happen")
                    }

                    if (res == null || res === RESTART)
                        return res
                    else {
                        if (parent != null) {
                            val n = GCAS_READ(this@IDDictionary)
                            if (n is TNode) cleanParent(n, parent, id, lev, startgen)
                        }
                        return res
                    }
                }
            }
            is TNode -> {
                clean(parent!!, lev - 5)
                return RESTART
            }
        }
        throw RuntimeException("Should not happen")
    }

    private tailrec fun lookup(id: Int): E? {
        val root = RDCSS_READ_ROOT()
        val res = root.rec_lookup(id, 0, null, root.gen)
        return if (res !== RESTART) res as E? else lookup(id)
    }

    private tailrec fun insert(e: E, id: Int): E? {
        val root = RDCSS_READ_ROOT()
        val ret = root.rec_insert(e, id, 0, null, root.gen)
        return if (ret !== RESTART) ret as E? else insert(e, id)
    }

    private tailrec fun delete(id: Int): E? {
        val root = RDCSS_READ_ROOT()
        val res = root.rec_delete(id, 0, null, root.gen)
        return if (res !== RESTART) res as E? else delete(id)
    }

    private inline fun ensureReadWrite() {
        if (readOnly) throw UnsupportedOperationException("Attempted to modify a read-only view")
    }

    tailrec override fun clear() {
        ensureReadWrite()
        val root = RDCSS_READ_ROOT()
        return if (RDCSS_ROOT(root, root.GCAS_READ(this), INode.newRootNode<E>())) Unit else clear()
    }

    fun contains(id: Int): Boolean = lookup(id) != null

    override fun contains(element: E): Boolean = lookup(element.id) != null

    @Deprecated("Bridge function", ReplaceWith("add(element)"))
    fun put(element: E) = add(element)

    override fun add(element: E): Boolean {
        ensureReadWrite()
        return insert(element, element.id) == null
    }

    fun remove(id: Int) : Boolean {
        ensureReadWrite()
        return delete(id) != null
    }

    override fun remove(element: E): Boolean = remove(element.id)

    override val size: Int get() = if (!readOnly) readOnlySnapshot().size else RDCSS_READ_ROOT().cachedSize(this)

    operator fun get(id: Int): E = lookup(id) ?: throw CatException("IDDictionary $name 不包含 ID = $id 的值")

    fun getOrNull(id: Int): E? = lookup(id)

    fun isReadOnly() = readOnly

    tailrec fun snapshot(): IDDictionary<E> {
        val root = RDCSS_READ_ROOT()
        val expectedMain = root.GCAS_READ(this)
        return if (RDCSS_ROOT(root, expectedMain, root.copyToGen(Any(), this)))
            IDDictionary(name, root.copyToGen(Any(), this), readOnly = readOnly) else snapshot()
    }

    tailrec fun readOnlySnapshot(): IDDictionary<E> {
        if (readOnly) return this
        val root = RDCSS_READ_ROOT()
        val expectedMain = root.GCAS_READ(this)
        return if (RDCSS_ROOT(root, expectedMain, root.copyToGen(Any(), this)))
            IDDictionary(name, root, readOnly = true) else readOnlySnapshot()
    }

    override operator fun iterator(): MutableIterator<E> = if (!readOnly) readOnlySnapshot().iterator() else TrieMapReadOnlyIterator(this)

    private open class TrieMapIterator<E : Identifiable>(val ct: IDDictionary<E>) : MutableIterator<E> {
        private val stack = arrayOfNulls<Array<Branch<E>?>>(7)
        private val stackPos = IntArray(7)
        private var depth = -1
        private var current: Leaf<E>? = null
        private var lastReturned: E? = null

        init {
            readINode(ct.RDCSS_READ_ROOT())
        }

        override fun hasNext(): Boolean = current != null

        override fun next(): E {
            if (hasNext()) {
                val r = current?.e ?: throw NoSuchElementException()
                advance()
                lastReturned = r
                return r
            } else {
                throw NoSuchElementException()
            }
        }

        private fun readINode(`in`: INode<E>) {
            val m = `in`.GCAS_READ(ct)
            when (m) {
                is CNode -> {
                    depth += 1
                    stack[depth] = m.array
                    stackPos[depth] = -1
                    advance()
                }
                is TNode -> current = m
                else -> current = null
            }
        }

        private fun advance() {
            if (depth >= 0) {
                val nPos = stackPos[depth] + 1
                if (nPos < stack[depth]!!.size) {
                    stackPos[depth] = nPos
                    val elem = stack[depth]!![nPos]
                    when (elem) {
                        is SNode -> current = elem
                        is INode -> readINode(elem)
                    }
                } else {
                    depth -= 1
                    advance()
                }
            } else {
                current = null
            }
        }

        override fun remove() {
            val lastReturned = lastReturned
            if (lastReturned != null) {
                ct.delete(lastReturned.id)
                this.lastReturned = null
            } else throw IllegalStateException()
        }
    }

    private class TrieMapReadOnlyIterator<E : Identifiable>(ct: IDDictionary<E>) : TrieMapIterator<E>(ct) {
        init {
            assert(ct.readOnly)
        }

        override fun remove(): Unit = throw UnsupportedOperationException()
    }
}