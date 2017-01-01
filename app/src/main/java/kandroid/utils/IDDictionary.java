package kandroid.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractSet;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

@SuppressWarnings("All")
public class IDDictionary<T extends Identifiable> extends AbstractSet<T> {

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final int TREEIFY_THRESHOLD = 8;
    static final int UNTREEIFY_THRESHOLD = 6;
    static final int MIN_TREEIFY_CAPACITY = 64;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof IDDictionary))
            return false;
        IDDictionary<?> m = (IDDictionary<?>) o;
        if (m.size() != size())
            return false;

        try {
            for (Node<T> e : entrySet()) {
                int key = e.getKey();
                T value = e.getValue();
                if (value == null) {
                    if (!(m.get(key) == null && m.containsKey(key)))
                        return false;
                } else {
                    if (!value.equals(m.get(key)))
                        return false;
                }
            }
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (Node<T> tNode : entrySet()) h += tNode.hashCode();
        return h;
    }

    @Override
    public String toString() {
        Iterator<T> i = iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            T e = i.next();
            int key = e.getId();
            sb.append(key);
            sb.append('=');
            sb.append(e == this ? "(this Set)" : e);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }

    /* ---------------- Static utilities -------------- */

    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /* ---------------- Fields -------------- */

    transient Node<T>[] table;

    transient Set<Integer> keySet;
    transient Set<Node<T>> entrySet;

    transient int size;
    transient int modCount;

    int threshold;
    final float loadFactor;

    /* ---------------- Public operations -------------- */

    public IDDictionary(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    public IDDictionary(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public IDDictionary() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

    public IDDictionary(IDDictionary<? extends T> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Nullable
    public T get(int id) {
        Node<T> e;
        return (e = getNode(id)) == null ? null : e.value;
    }

    @Override
    @Deprecated
    public boolean add(T t) {
        return !t.equals(put(t));
    }

    @Nullable
    public T put(T value) {
        return putVal(value.getId(), value, false);
    }

    public void putAll(IDDictionary<? extends T> m) {
        putMapEntries(m);
    }

    @Nullable
    public T remove(int id) {
        Node<T> e;
        return (e = removeNode(id, null, false, true)) == null ?
                null : e.value;
    }

    @Override
    public void clear() {
        Node<T>[] tab;
        modCount++;
        if ((tab = table) != null && size > 0) {
            size = 0;
            for (int i = 0; i < tab.length; ++i)
                tab[i] = null;
        }
    }

    public boolean containsKey(int id) {
        return getNode(id) != null;
    }

    @Override
    @Deprecated
    public boolean contains(Object o) {
        return containsValue(o);
    }

    public boolean containsValue(Object o) {
        Node<T>[] tab;
        T t;
        if ((tab = table) != null && size > 0) {
            for (Node<T> aTab : tab) {
                for (Node<T> e = aTab; e != null; e = e.next) {
                    if ((t = e.value) == o ||
                            (o != null && o.equals(t)))
                        return true;
                }
            }
        }
        return false;
    }

    @NotNull
    public Set<Integer> keySet() {
        Set<Integer> ks = keySet;
        if (ks == null) {
            ks = new KeySet();
            keySet = ks;
        }
        return ks;
    }

    @NotNull
    public Set<T> valueSet() {
        return this;
    }

    @NotNull
    public Set<Node<T>> entrySet() {
        Set<Node<T>> es;
        return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new ValueIterator();
    }

    /* ------------------------------------------------------------ */
    // iterators and set views

    abstract class HashIterator {
        Node<T> next;        // next entry to return
        Node<T> current;     // current entry
        int expectedModCount;  // for fast-fail
        int index;             // current slot

        HashIterator() {
            expectedModCount = modCount;
            Node<T>[] t = table;
            current = next = null;
            index = 0;
            if (t != null && size > 0) { // advance to first entry
                do {
                } while (index < t.length && (next = t[index++]) == null);
            }
        }

        public final boolean hasNext() {
            return next != null;
        }

        final Node<T> nextNode() {
            Node<T>[] t;
            Node<T> e = next;
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (e == null)
                throw new NoSuchElementException();
            if ((next = (current = e).next) == null && (t = table) != null) {
                do {
                } while (index < t.length && (next = t[index++]) == null);
            }
            return e;
        }

        public final void remove() {
            Node<T> p = current;
            if (p == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            current = null;
            int key = p.key;
            removeNode(key, null, false, false);
            expectedModCount = modCount;
        }
    }

    final class KeyIterator extends HashIterator implements Iterator<Integer> {
        public final Integer next() {
            return nextNode().key;
        }
    }

    final class ValueIterator extends HashIterator implements Iterator<T> {
        public final T next() {
            return nextNode().value;
        }
    }

    final class EntryIterator extends HashIterator implements Iterator<Node<T>> {
        public final Node<T> next() {
            return nextNode();
        }
    }

    final class KeySet extends AbstractSet<Integer> {
        public final int size() {
            return size;
        }

        public final void clear() {
            IDDictionary.this.clear();
        }

        public final Iterator<Integer> iterator() {
            return new KeyIterator();
        }

        public final boolean contains(int o) {
            return containsKey(o);
        }

        public final boolean remove(int key) {
            return removeNode(key, null, false, true) != null;
        }

    }

    final class EntrySet extends AbstractSet<Node<T>> {
        public final int size() {
            return size;
        }

        public final void clear() {
            IDDictionary.this.clear();
        }

        public final Iterator<Node<T>> iterator() {
            return new EntryIterator();
        }

        public final boolean contains(Object o) {
            if (!(o instanceof IDDictionary.Node))
                return false;
            Node<? extends Identifiable> e = (Node<? extends Identifiable>) o;
            int key = e.getKey();
            Node<T> candidate = getNode(key);
            return candidate != null && candidate.equals(e);
        }

        public final boolean remove(Object o) {
            if (o instanceof IDDictionary.Node) {
                Node<? extends Identifiable> e = (Node<? extends Identifiable>) o;
                int key = e.getKey();
                Object value = e.getValue();
                return removeNode(key, value, true, true) != null;
            }
            return false;
        }
    }

    /* ------------------------------------------------------------ */

    final void putMapEntries(IDDictionary<? extends T> m) {
        int s = m.size();
        if (s > 0) {
            if (table == null) { // pre-size
                float ft = ((float) s / loadFactor) + 1.0F;
                int t = ((ft < (float) MAXIMUM_CAPACITY) ?
                        (int) ft : MAXIMUM_CAPACITY);
                if (t > threshold)
                    threshold = tableSizeFor(t);
            } else if (s > threshold)
                resize();
            for (Node<? extends T> e : m.entrySet()) {
                int key = e.getKey();
                T value = e.getValue();
                putVal(key, value, false);
            }
        }
    }

    final Node<T> getNode(int key) {
        Node<T>[] tab;
        Node<T> first, e;
        int n;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & key]) != null) {
            if (first.key == key) // always check first node
                return first;
            if ((e = first.next) != null) {
                if (first instanceof TreeNode)
                    return ((TreeNode<T>) first).getTreeNode(key);
                do {
                    if (e.key == key)
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    final T putVal(int key, T value, boolean onlyIfAbsent) {
        Node<T>[] tab;
        Node<T> p;
        int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & key]) == null)
            tab[i] = newNode(key, value, null);
        else {
            Node<T> e;
            if (p.key == key)
                e = p;
            else if (p instanceof TreeNode)
                e = ((TreeNode<T>) p).putTreeVal(this, tab, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, key);
                        break;
                    }
                    if (e.key == key)
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                T oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        return null;
    }

    final Node<T> removeNode(int key, Object value, boolean matchValue, boolean movable) {
        Node<T>[] tab;
        Node<T> p;
        int n, index;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (p = tab[index = (n - 1) & key]) != null) {
            Node<T> node = null, e;
            T t;
            if (p.key == key)
                node = p;
            else if ((e = p.next) != null) {
                if (p instanceof TreeNode)
                    node = ((TreeNode<T>) p).getTreeNode(key);
                else {
                    do {
                        if (e.key == key) {
                            node = e;
                            break;
                        }
                        p = e;
                    } while ((e = e.next) != null);
                }
            }
            if (node != null && (!matchValue || (t = node.value) == value ||
                    (value != null && value.equals(t)))) {
                if (node instanceof TreeNode)
                    ((TreeNode<T>) node).removeTreeNode(this, tab, movable);
                else if (node == p)
                    tab[index] = node.next;
                else
                    p.next = node.next;
                ++modCount;
                --size;
                return node;
            }
        }
        return null;
    }

    final Node<T>[] resize() {
        Node<T>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        } else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes", "unchecked"})
        Node<T>[] newTab = (Node<T>[]) new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<T> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.key & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<T>) e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<T> loHead = null, loTail = null;
                        Node<T> hiHead = null, hiTail = null;
                        Node<T> next;
                        do {
                            next = e.next;
                            if ((e.key & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            } else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    final void treeifyBin(Node<T>[] tab, int hash) {
        int n, index;
        Node<T> e;
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<T> hd = null, tl = null;
            do {
                TreeNode<T> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null)
                hd.treeify(tab);
        }
    }

    // Create a regular (non-tree) node
    Node<T> newNode(int key, T value, Node<T> next) {
        return new Node<>(key, value, next);
    }

    // For conversion from TreeNodes to plain nodes
    Node<T> replacementNode(Node<T> p, Node<T> next) {
        return new Node<>(p.key, p.value, next);
    }

    // Create a tree bin node
    TreeNode<T> newTreeNode(int key, T value, Node<T> next) {
        return new TreeNode<>(key, value, next);
    }

    // For treeifyBin
    TreeNode<T> replacementTreeNode(Node<T> p, Node<T> next) {
        return new TreeNode<>(p.key, p.value, next);
    }

    /* ------------------------------------------------------------ */

    static class Node<T> {
        final int key;
        T value;
        Node<T> next;

        Node(int key, T value, Node<T> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final int getKey() {
            return key;
        }

        public final T getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final int hashCode() {
            return key ^ (value != null ? value.hashCode() : 0);
        }

        public final T setValue(T newValue) {
            T oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof IDDictionary.Node) {
                Node<? extends Identifiable> e = (Node<? extends Identifiable>) o;
                Object b = e.getValue();
                if (key == e.getKey() && ((value == b) || (value != null && value.equals(b))))
                    return true;
            }
            return false;
        }
    }

    static final class TreeNode<T extends Identifiable> extends Node<T> {
        TreeNode<T> parent;  // red-black tree links
        TreeNode<T> left;
        TreeNode<T> right;
        TreeNode<T> prev;    // needed to unlink next upon deletion
        boolean red;

        TreeNode(int key, T val, Node<T> next) {
            super(key, val, next);
        }

        final TreeNode<T> root() {
            for (TreeNode<T> r = this, p; ; ) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }

        static <T extends Identifiable> void moveRootToFront(Node<T>[] tab, TreeNode<T> root) {
            int n;
            if (root != null && tab != null && (n = tab.length) > 0) {
                int index = (n - 1) & root.key;
                TreeNode<T> first = (TreeNode<T>) tab[index];
                if (root != first) {
                    Node<T> rn;
                    tab[index] = root;
                    TreeNode<T> rp = root.prev;
                    if ((rn = root.next) != null)
                        ((TreeNode<T>) rn).prev = rp;
                    if (rp != null)
                        rp.next = rn;
                    if (first != null)
                        first.prev = root;
                    root.next = first;
                    root.prev = null;
                }
                assert checkInvariants(root);
            }
        }

        final TreeNode<T> find(int key) {
            TreeNode<T> p = this;
            do {
                int pk;
                TreeNode<T> pl = p.left, pr = p.right;
                if ((pk = p.key) > key)
                    p = pl;
                else if (pk < key)
                    p = pr;
                else if (pk == key)
                    return p;
                else throw new RuntimeException("???");
            } while (p != null);
            return null;
        }

        final TreeNode<T> getTreeNode(int key) {
            return ((parent != null) ? root() : this).find(key);
        }

        static int tieBreakOrder(Object a, Object b) {
            int d;
            if (a == null || b == null ||
                    (d = a.getClass().getName().
                            compareTo(b.getClass().getName())) == 0)
                d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
                        -1 : 1);
            return d;
        }

        final void treeify(Node<T>[] tab) {
            TreeNode<T> root = null;
            for (TreeNode<T> x = this, next; x != null; x = next) {
                next = (TreeNode<T>) x.next;
                x.left = x.right = null;
                if (root == null) {
                    x.parent = null;
                    x.red = false;
                    root = x;
                } else {
                    int h = x.key;
                    for (TreeNode<T> p = root; ; ) {
                        int dir, ph;
                        if ((ph = p.key) > h)
                            dir = -1;
                        else if (ph < h)
                            dir = 1;
                        else dir = tieBreakOrder(x.key, p.key);

                        TreeNode<T> xp = p;
                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
                            x.parent = xp;
                            if (dir <= 0)
                                xp.left = x;
                            else
                                xp.right = x;
                            root = balanceInsertion(root, x);
                            break;
                        }
                    }
                }
            }
            moveRootToFront(tab, root);
        }

        final Node<T> untreeify(IDDictionary<T> map) {
            Node<T> hd = null, tl = null;
            for (Node<T> q = this; q != null; q = q.next) {
                Node<T> p = map.replacementNode(q, null);
                if (tl == null)
                    hd = p;
                else
                    tl.next = p;
                tl = p;
            }
            return hd;
        }

        final TreeNode<T> putTreeVal(IDDictionary<T> map, Node<T>[] tab,
                                     int key, T t) {
            boolean searched = false;
            TreeNode<T> root = (parent != null) ? root() : this;
            for (TreeNode<T> p = root; ; ) {
                int dir, ph;
                int pk;
                if ((ph = p.key) > key)
                    dir = -1;
                else if (ph < key)
                    dir = 1;
                else if ((pk = p.key) == key)
                    return p;
                else {
                    if (!searched) {
                        TreeNode<T> q, ch;
                        searched = true;
                        if (((ch = p.left) != null &&
                                (q = ch.find(key)) != null) ||
                                ((ch = p.right) != null &&
                                        (q = ch.find(key)) != null))
                            return q;
                    }
                    dir = tieBreakOrder(key, pk);
                }

                TreeNode<T> xp = p;
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                    Node<T> xpn = xp.next;
                    TreeNode<T> x = map.newTreeNode(key, t, xpn);
                    if (dir <= 0)
                        xp.left = x;
                    else
                        xp.right = x;
                    xp.next = x;
                    x.parent = x.prev = xp;
                    if (xpn != null)
                        ((TreeNode<T>) xpn).prev = x;
                    moveRootToFront(tab, balanceInsertion(root, x));
                    return null;
                }
            }
        }

        final void removeTreeNode(IDDictionary<T> map, Node<T>[] tab,
                                  boolean movable) {
            int n;
            if (tab == null || (n = tab.length) == 0)
                return;
            int index = (n - 1) & key;
            TreeNode<T> first = (TreeNode<T>) tab[index], root = first, rl;
            TreeNode<T> succ = (TreeNode<T>) next, pred = prev;
            if (pred == null)
                tab[index] = first = succ;
            else
                pred.next = succ;
            if (succ != null)
                succ.prev = pred;
            if (first == null)
                return;
            if (root.parent != null)
                root = root.root();
            if (root == null || root.right == null ||
                    (rl = root.left) == null || rl.left == null) {
                tab[index] = first.untreeify(map);  // too small
                return;
            }
            TreeNode<T> p = this, pl = left, pr = right, replacement;
            if (pl != null && pr != null) {
                TreeNode<T> s = pr, sl;
                while ((sl = s.left) != null) // find successor
                    s = sl;
                boolean c = s.red;
                s.red = p.red;
                p.red = c; // swap colors
                TreeNode<T> sr = s.right;
                TreeNode<T> pp = p.parent;
                if (s == pr) { // p was ses'ses direct parent
                    p.parent = s;
                    s.right = p;
                } else {
                    TreeNode<T> sp = s.parent;
                    if ((p.parent = sp) != null) {
                        if (s == sp.left)
                            sp.left = p;
                        else
                            sp.right = p;
                    }
                    pr.parent = s;
                }
                p.left = null;
                if ((p.right = sr) != null)
                    sr.parent = p;
                pl.parent = s;
                if ((s.parent = pp) == null)
                    root = s;
                else if (p == pp.left)
                    pp.left = s;
                else
                    pp.right = s;
                if (sr != null)
                    replacement = sr;
                else
                    replacement = p;
            } else if (pl != null)
                replacement = pl;
            else if (pr != null)
                replacement = pr;
            else
                replacement = p;
            if (replacement != p) {
                TreeNode<T> pp = replacement.parent = p.parent;
                if (pp == null)
                    root = replacement;
                else if (p == pp.left)
                    pp.left = replacement;
                else
                    pp.right = replacement;
                p.left = p.right = p.parent = null;
            }

            TreeNode<T> r = p.red ? root : balanceDeletion(root, replacement);

            if (replacement == p) {  // detach
                TreeNode<T> pp = p.parent;
                p.parent = null;
                if (pp != null) {
                    if (p == pp.left)
                        pp.left = null;
                    else if (p == pp.right)
                        pp.right = null;
                }
            }
            if (movable)
                moveRootToFront(tab, r);
        }

        final void split(IDDictionary<T> map, Node<T>[] tab, int index, int bit) {
            TreeNode<T> b = this;
            // Relink into lo and hi lists, preserving order
            TreeNode<T> loHead = null, loTail = null;
            TreeNode<T> hiHead = null, hiTail = null;
            int lc = 0, hc = 0;
            for (TreeNode<T> e = b, next; e != null; e = next) {
                next = (TreeNode<T>) e.next;
                e.next = null;
                if ((e.key & bit) == 0) {
                    if ((e.prev = loTail) == null)
                        loHead = e;
                    else
                        loTail.next = e;
                    loTail = e;
                    ++lc;
                } else {
                    if ((e.prev = hiTail) == null)
                        hiHead = e;
                    else
                        hiTail.next = e;
                    hiTail = e;
                    ++hc;
                }
            }

            if (loHead != null) {
                if (lc <= UNTREEIFY_THRESHOLD)
                    tab[index] = loHead.untreeify(map);
                else {
                    tab[index] = loHead;
                    if (hiHead != null) // (else is already treeified)
                        loHead.treeify(tab);
                }
            }
            if (hiHead != null) {
                if (hc <= UNTREEIFY_THRESHOLD)
                    tab[index + bit] = hiHead.untreeify(map);
                else {
                    tab[index + bit] = hiHead;
                    if (loHead != null)
                        hiHead.treeify(tab);
                }
            }
        }

        /* ------------------------------------------------------------ */
        // Red-black tree methods, all adapted from CLR

        static <T extends Identifiable> TreeNode<T> rotateLeft(TreeNode<T> root,
                                                               TreeNode<T> p) {
            TreeNode<T> r, pp, rl;
            if (p != null && (r = p.right) != null) {
                if ((rl = p.right = r.left) != null)
                    rl.parent = p;
                if ((pp = r.parent = p.parent) == null)
                    (root = r).red = false;
                else if (pp.left == p)
                    pp.left = r;
                else
                    pp.right = r;
                r.left = p;
                p.parent = r;
            }
            return root;
        }

        static <T extends Identifiable> TreeNode<T> rotateRight(TreeNode<T> root,
                                                                TreeNode<T> p) {
            TreeNode<T> l, pp, lr;
            if (p != null && (l = p.left) != null) {
                if ((lr = p.left = l.right) != null)
                    lr.parent = p;
                if ((pp = l.parent = p.parent) == null)
                    (root = l).red = false;
                else if (pp.right == p)
                    pp.right = l;
                else
                    pp.left = l;
                l.right = p;
                p.parent = l;
            }
            return root;
        }

        static <T extends Identifiable> TreeNode<T> balanceInsertion(TreeNode<T> root,
                                                                     TreeNode<T> x) {
            x.red = true;
            for (TreeNode<T> xp, xpp, xppl, xppr; ; ) {
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                } else if (!xp.red || (xpp = xp.parent) == null)
                    return root;
                if (xp == (xppl = xpp.left)) {
                    if ((xppr = xpp.right) != null && xppr.red) {
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    } else {
                        if (x == xp.right) {
                            root = rotateLeft(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                } else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    } else {
                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }

        static <T extends Identifiable> TreeNode<T> balanceDeletion(TreeNode<T> root,
                                                                    TreeNode<T> x) {
            for (TreeNode<T> xp, xpl, xpr; ; ) {
                if (x == null || x == root)
                    return root;
                else if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                } else if (x.red) {
                    x.red = false;
                    return root;
                } else if ((xpl = xp.left) == x) {
                    if ((xpr = xp.right) != null && xpr.red) {
                        xpr.red = false;
                        xp.red = true;
                        root = rotateLeft(root, xp);
                        xpr = (xp = x.parent) == null ? null : xp.right;
                    }
                    if (xpr == null)
                        x = xp;
                    else {
                        TreeNode<T> sl = xpr.left, sr = xpr.right;
                        if ((sr == null || !sr.red) &&
                                (sl == null || !sl.red)) {
                            xpr.red = true;
                            x = xp;
                        } else {
                            if (sr == null || !sr.red) {
                                sl.red = false;
                                xpr.red = true;
                                root = rotateRight(root, xpr);
                                xpr = (xp = x.parent) == null ?
                                        null : xp.right;
                            }
                            if (xpr != null) {
                                xpr.red = xp.red;
                                if ((sr = xpr.right) != null)
                                    sr.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateLeft(root, xp);
                            }
                            x = root;
                        }
                    }
                } else { // symmetric
                    if (xpl != null && xpl.red) {
                        xpl.red = false;
                        xp.red = true;
                        root = rotateRight(root, xp);
                        xpl = (xp = x.parent) == null ? null : xp.left;
                    }
                    if (xpl == null)
                        x = xp;
                    else {
                        TreeNode<T> sl = xpl.left, sr = xpl.right;
                        if ((sl == null || !sl.red) &&
                                (sr == null || !sr.red)) {
                            xpl.red = true;
                            x = xp;
                        } else {
                            if (sl == null || !sl.red) {
                                sr.red = false;
                                xpl.red = true;
                                root = rotateLeft(root, xpl);
                                xpl = (xp = x.parent) == null ?
                                        null : xp.left;
                            }
                            if (xpl != null) {
                                xpl.red = xp.red;
                                if ((sl = xpl.left) != null)
                                    sl.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateRight(root, xp);
                            }
                            x = root;
                        }
                    }
                }
            }
        }

        static <T extends Identifiable> boolean checkInvariants(TreeNode<T> t) {
            TreeNode<T> tp = t.parent, tl = t.left, tr = t.right,
                    tb = t.prev, tn = (TreeNode<T>) t.next;
            if (tb != null && tb.next != t)
                return false;
            if (tn != null && tn.prev != t)
                return false;
            if (tp != null && t != tp.left && t != tp.right)
                return false;
            if (tl != null && (tl.parent != t || tl.key > t.key))
                return false;
            if (tr != null && (tr.parent != t || tr.key < t.key))
                return false;
            if (t.red && tl != null && tl.red && tr != null && tr.red)
                return false;
            if (tl != null && !checkInvariants(tl))
                return false;
            return !(tr != null && !checkInvariants(tr));
        }
    }
}