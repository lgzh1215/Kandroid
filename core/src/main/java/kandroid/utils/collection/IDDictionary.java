package kandroid.utils.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

//这其实只是个Map（手动斜眼
public class IDDictionary<V extends Identifiable> extends AbstractSet<V> implements Cloneable {

    private static final int DEFAULT_CAPACITY = 8;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;

    private final float loadFactor;

    private int[] keys;
    private V[] values;
    private int maxSize;
    private int size;
    private int mask;

    public IDDictionary() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public IDDictionary(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public IDDictionary(int initialCapacity, float loadFactor) {
        if (loadFactor <= 0.0f || loadFactor > 1.0f) {
            throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
        }

        this.loadFactor = loadFactor;

        // Adjust the initial capacity if necessary.
        int capacity;
        if (initialCapacity <= 0) {
            capacity = 1;
        } else if (initialCapacity >= 0x40000000) {
            capacity = 0x40000000;
        } else {
            capacity = 1 << (32 - Integer.numberOfLeadingZeros(initialCapacity - 1));
        }
        mask = capacity - 1;

        // Allocate the arrays.
        keys = new int[capacity];
        //noinspection unchecked
        values = (V[]) new Identifiable[capacity];

        // Initialize the maximum size value.
        maxSize = calcMaxSize(capacity);
    }

    @Override
    public boolean contains(Object o) {
        return containsValue(o);
    }

    @Override
    public boolean add(V v) {
        put(v);
        return true;
    }

    //@Nullable
    public V get(int key) {
        int index = indexOf(key);
        return index == -1 ? null : values[index];
    }

    @Nullable
    public V put(@NotNull V value) {
        return put(value.getId(), value);
    }

    @Nullable
    public V put(int key, @NotNull V value) {
        int startIndex = hashIndex(key);
        int index = startIndex;

        for (; ; ) {
            if (values[index] == null) {
                // Found empty slot, use it.
                keys[index] = key;
                values[index] = value;
                growSize();
                return null;
            }
            if (keys[index] == key) {
                // Found existing entry with this key, just replace the value.
                V previousValue = values[index];
                values[index] = value;
                return previousValue;
            }

            // Conflict, keep probing ...
            if ((index = probeNext(index)) == startIndex) {
                // Can only happen if the map was full at MAX_ARRAY_SIZE and couldn't grow.
                throw new IllegalStateException("Unable to insert");
            }
        }
    }

    @Nullable
    public V remove(int key) {
        int index = indexOf(key);
        if (index == -1) {
            return null;
        }

        V prev = values[index];
        removeAt(index);
        return prev;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        Arrays.fill(keys, 0);
        Arrays.fill(values, null);
        size = 0;
    }

    public boolean containsKey(int key) {
        return indexOf(key) >= 0;
    }

    public boolean containsValue(@NotNull Object value) {
        @SuppressWarnings("unchecked")
        V v1 = (V) value;
        for (V v2 : values) {
            // The map supports null values; this will be matched as NULL_VALUE.equals(NULL_VALUE).
            if (v2 != null && v2.equals(v1)) {
                return true;
            }
        }
        return false;
    }

    //region Objects Method

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //noinspection unchecked
        IDDictionary<V> newMap = (IDDictionary<V>) super.clone();
        newMap.keys = keys.clone();
        newMap.values = values.clone();
        return newMap;
    }

    @NotNull
    public IDDictionary<V> snapshot() {
        try {
            //noinspection unchecked
            return (IDDictionary<V>) clone();
        } catch (CloneNotSupportedException ignored) {
            throw new AssertionError();
        }
    }

    @Override
    public int hashCode() {
        int hash = size;
        for (int key : keys) {
            hash ^= key;
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IDDictionary)) {
            return false;
        }
        IDDictionary other = (IDDictionary) obj;
        if (size != other.size()) {
            return false;
        }
        for (int i = 0; i < values.length; ++i) {
            V value = values[i];
            if (value != null) {
                int key = keys[i];
                Object otherValue = other.get(key);
                if (!value.equals(otherValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(4 * size);
        sb.append('{');
        boolean first = true;
        for (int i = 0; i < values.length; ++i) {
            V value = values[i];
            if (value != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(keyToString(keys[i])).append('=').append(value == this ? "(this Map)" : value);
                first = false;
            }
        }
        return sb.append('}').toString();
    }

    /**
     * Helper method called by {@link #toString()} in order to convert a single map key into a string. This is protected
     * to allow subclasses to override the appearance of a given key.
     */
    protected String keyToString(int key) {
        return Integer.toString(key);
    }
    //endregion

    //region private method

    private int indexOf(int key) {
        int startIndex = hashIndex(key);
        int index = startIndex;

        for (; ; ) {
            if (values[index] == null) {
                // It's available, so no chance that this value exists anywhere in the map.
                return -1;
            }
            if (key == keys[index]) {
                return index;
            }

            // Conflict, keep probing ...
            if ((index = probeNext(index)) == startIndex) {
                return -1;
            }
        }
    }

    @Contract(pure = true)
    private int hashIndex(int key) {
        // The array lengths are always a power of two, so we can use a bitmask to stay inside the array bounds.
        return key & mask;
    }

    @Contract(pure = true)
    private int probeNext(int index) {
        // The array lengths are always a power of two, so we can use a bitmask to stay inside the array bounds.
        return (index + 1) & mask;
    }

    private void growSize() {
        size++;

        if (size > maxSize) {
            if (keys.length == Integer.MAX_VALUE) {
                throw new IllegalStateException("Max capacity reached at size=" + size);
            }

            // Double the capacity.
            rehash(keys.length << 1);
        }
    }

    private boolean removeAt(final int index) {
        --size;
        // Clearing the key is not strictly necessary (for GC like in a regular collection),
        // but recommended for security. The memory location is still fresh in the cache anyway.
        keys[index] = 0;
        values[index] = null;

        // In the interval from index to the next available entry, the arrays may have entries
        // that are displaced from their base position due to prior conflicts. Iterate these
        // entries and move them back if possible, optimizing future lookups.
        // Knuth Section 6.4 Algorithm R, also used by the JDK's IdentityHashMap.

        boolean movedBack = false;
        int nextFree = index;
        for (int i = probeNext(index); values[i] != null; i = probeNext(i)) {
            int bucket = hashIndex(keys[i]);
            if (i < bucket && (bucket <= nextFree || nextFree <= i) || bucket <= nextFree && nextFree <= i) {
                // Move the displaced entry "back" to the first available position.
                keys[nextFree] = keys[i];
                values[nextFree] = values[i];
                movedBack = true;
                // Put the first entry after the displaced entry
                keys[i] = 0;
                values[i] = null;
                nextFree = i;
            }
        }
        return movedBack;
    }

    @Contract(pure = true)
    private int calcMaxSize(int capacity) {
        // Clip the upper bound so that there will always be at least one available slot.
        int upperBound = capacity - 1;
        return Math.min(upperBound, (int) (capacity * loadFactor));
    }

    private void rehash(int newCapacity) {
        int[] oldKeys = keys;
        V[] oldValues = values;

        keys = new int[newCapacity];
        //noinspection unchecked
        values = (V[]) new Identifiable[newCapacity];

        maxSize = calcMaxSize(newCapacity);
        mask = newCapacity - 1;

        // Insert to the new arrays.
        for (int i = 0; i < oldValues.length; ++i) {
            V oldVal = oldValues[i];
            if (oldVal != null) {
                // Inlined put(), but much simpler: we don't need to worry about
                // duplicated keys, growing/rehashing, or failing to insert.
                int oldKey = oldKeys[i];
                int index = hashIndex(oldKey);

                for (; ; ) {
                    if (values[index] == null) {
                        keys[index] = oldKey;
                        values[index] = oldVal;
                        break;
                    }

                    // Conflict, keep probing. Can wrap around, but never reaches startIndex again.
                    index = probeNext(index);
                }
            }
        }
    }
    //endregion

    //region Boxing Method
    public boolean containsKey(@NotNull Object key) {
        return containsKey(objectToKey(key));
    }

    @Nullable
    public V get(@NotNull Object key) {
        return get(objectToKey(key));
    }

    public V put(Integer key, V value) {
        return put(objectToKey(key), value);
    }

    @Override
    public boolean remove(@NotNull Object key) {
        return remove(objectToKey(key)) != null;
    }

    @Contract(pure = true)
    private int objectToKey(@NotNull Object key) {
        return (Integer) key;
    }
    //endregion

    //region Iterators and Set views

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return new ValueIterator();
    }

    @NotNull
    public KeySet keySet() {
        return new KeySet();
    }

    @NotNull
    public Values values() {
        return new Values();
    }

    @NotNull
    public EntrySet entrySet() {
        return new EntrySet();
    }

    public final class MapEntry implements Map.Entry<Integer, V> {
        private final int entryIndex;

        private MapEntry(int entryIndex) {
            this.entryIndex = entryIndex;
        }

        @NotNull
        @Override
        @Contract(pure = true)
        public Integer getKey() {
            return keys[entryIndex];
        }

        @NotNull
        @Override
        @Contract(pure = true)
        public V getValue() {
            return values[entryIndex];
        }

        @Contract("_ -> fail")
        @NotNull
        @Override
        public V setValue(@NotNull V value) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        @Override
        public String toString() {
            return "" + getKey() + '=' + getValue();
        }
    }

    private abstract class MapIterator {
        //TODO improve it
        private int prevIndex = -1;
        private int nextIndex = -1;
        private int entryIndex = -1;

        private void scanNext() {
            while (true) {
                ++nextIndex;
                if (nextIndex >= values.length || values[nextIndex] != null) {
                    break;
                }
            }
        }

        private void forwardNext() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            prevIndex = nextIndex;
            scanNext();
            entryIndex = prevIndex;
        }

        final int nextKey() {
            forwardNext();
            return keys[entryIndex];
        }

        @NotNull
        final V nextValue() {
            forwardNext();
            return values[entryIndex];
        }

        @NotNull
        final MapEntry nextEntry() {
            forwardNext();
            return new MapEntry(entryIndex);
        }

        public final boolean hasNext() {
            if (nextIndex == -1) {
                scanNext();
            }
            return nextIndex < keys.length;
        }

        @Contract(" -> fail")
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public final class KeyIterator extends MapIterator implements Iterator<Integer> {
        private KeyIterator() {
        }

        @NotNull
        public final Integer next() {
            return nextKey();
        }
    }

    public final class ValueIterator extends MapIterator implements Iterator<V> {
        private ValueIterator() {
        }

        @NotNull
        public final V next() {
            return nextValue();
        }
    }

    public final class EntryIterator extends MapIterator implements Iterator<Map.Entry<Integer, V>> {
        private EntryIterator() {
        }

        @NotNull
        public final MapEntry next() {
            return nextEntry();
        }
    }

    public final class KeySet extends AbstractSet<Integer> {
        private KeySet() {
        }

        @Contract(pure = true)
        @Override
        public int size() {
            return size;
        }

        @NotNull
        @Override
        public KeyIterator iterator() {
            return new KeyIterator();
        }

        @Override
        public final boolean contains(Object o) {
            return containsKey(o);
        }
    }

    public final class Values extends AbstractCollection<V> {
        private Values() {
        }

        @Contract(pure = true)
        @Override
        public final int size() {
            return size;
        }

        @NotNull
        @Override
        public final ValueIterator iterator() {
            return new ValueIterator();
        }

        @Override
        public final boolean contains(Object o) {
            return containsValue(o);
        }
    }

    public final class EntrySet extends AbstractSet<Map.Entry<Integer, V>> {
        private EntrySet() {
        }

        @Contract(pure = true)
        public final int size() {
            return size;
        }

        @NotNull
        @Override
        public EntryIterator iterator() {
            return new EntryIterator();
        }
    }
    //endregion
}