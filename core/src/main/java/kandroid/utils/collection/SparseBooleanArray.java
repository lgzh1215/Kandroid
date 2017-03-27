package kandroid.utils.collection;


public class SparseBooleanArray implements Cloneable {
    /**
     * Creates a new SparseBooleanArray containing no mappings.
     */
    public SparseBooleanArray() {
        this(10);
    }

    private int[] mKeys;
    private boolean[] mValues;
    private int mSize;

    /**
     * Creates a new SparseBooleanArray containing no mappings that will not
     * require any additional memory allocation to store the specified
     * number of mappings.  If you supply an initial capacity of 0, the
     * sparse array will be initialized with a light-weight representation
     * not requiring any additional array allocations.
     */
    public SparseBooleanArray(int initialCapacity) {
        mKeys = new int[initialCapacity];
        mValues = new boolean[initialCapacity];
        mSize = 0;
    }

    @Override
    public SparseBooleanArray clone() {
        SparseBooleanArray clone = null;
        try {
            clone = (SparseBooleanArray) super.clone();
            clone.mKeys = mKeys.clone();
            clone.mValues = mValues.clone();
        } catch (CloneNotSupportedException cnse) {
            /* ignore */
        }
        return clone;
    }

    /**
     * Gets the boolean mapped from the specified key, or <code>false</code>
     * if no such mapping has been made.
     */
    public boolean get(int key) {
        return get(key, false);
    }

    /**
     * Gets the boolean mapped from the specified key, or the specified value
     * if no such mapping has been made.
     */
    public boolean get(int key, boolean valueIfKeyNotFound) {
        int i = binarySearch(mKeys, mSize, key);

        if (i < 0) {
            return valueIfKeyNotFound;
        } else {
            return mValues[i];
        }
    }

    /**
     * Removes the mapping from the specified key, if there was any.
     */
    public void delete(int key) {
        int i = binarySearch(mKeys, mSize, key);

        if (i >= 0) {
            System.arraycopy(mKeys, i + 1, mKeys, i, mSize - (i + 1));
            System.arraycopy(mValues, i + 1, mValues, i, mSize - (i + 1));
            mSize--;
        }
    }

    /**
     * @hide
     */
    public void removeAt(int index) {
        System.arraycopy(mKeys, index + 1, mKeys, index, mSize - (index + 1));
        System.arraycopy(mValues, index + 1, mValues, index, mSize - (index + 1));
        mSize--;
    }

    /**
     * Adds a mapping from the specified key to the specified value,
     * replacing the previous mapping from the specified key if there
     * was one.
     */
    public void put(int key, boolean value) {
        int i = binarySearch(mKeys, mSize, key);

        if (i >= 0) {
            mValues[i] = value;
        } else {
            i = ~i;

            mKeys = insert(mKeys, mSize, i, key);
            mValues = insert(mValues, mSize, i, value);
            mSize++;
        }
    }

    /**
     * Returns the number of key-value mappings that this SparseBooleanArray
     * currently stores.
     */
    public int size() {
        return mSize;
    }

    /**
     * Given an index in the range <code>0...size()-1</code>, returns
     * the key from the <code>index</code>th key-value mapping that this
     * SparseBooleanArray stores.
     * <p>
     * <p>The keys corresponding to indices in ascending order are guaranteed to
     * be in ascending order, e.g., <code>keyAt(0)</code> will return the
     * smallest key and <code>keyAt(size()-1)</code> will return the largest
     * key.</p>
     */
    public int keyAt(int index) {
        return mKeys[index];
    }

    /**
     * Given an index in the range <code>0...size()-1</code>, returns
     * the value from the <code>index</code>th key-value mapping that this
     * SparseBooleanArray stores.
     * <p>
     * <p>The values corresponding to indices in ascending order are guaranteed
     * to be associated with keys in ascending order, e.g.,
     * <code>valueAt(0)</code> will return the value associated with the
     * smallest key and <code>valueAt(size()-1)</code> will return the value
     * associated with the largest key.</p>
     */
    public boolean valueAt(int index) {
        return mValues[index];
    }

    /**
     * @hide
     */
    public void setValueAt(int index, boolean value) {
        mValues[index] = value;
    }

    /**
     * @hide
     */
    public void setKeyAt(int index, int key) {
        mKeys[index] = key;
    }

    /**
     * Returns the index for which {@link #keyAt} would return the
     * specified key, or a negative number if the specified
     * key is not mapped.
     */
    public int indexOfKey(int key) {
        return binarySearch(mKeys, mSize, key);
    }

    /**
     * Returns an index for which {@link #valueAt} would return the
     * specified key, or a negative number if no keys map to the
     * specified value.
     * Beware that this is a linear search, unlike lookups by key,
     * and that multiple keys can map to the same value and this will
     * find only one of them.
     */
    public int indexOfValue(boolean value) {
        for (int i = 0; i < mSize; i++)
            if (mValues[i] == value)
                return i;

        return -1;
    }

    /**
     * Removes all key-value mappings from this SparseBooleanArray.
     */
    public void clear() {
        mSize = 0;
    }

    /**
     * Puts a key/value pair into the array, optimizing for the case where
     * the key is greater than all existing keys in the array.
     */
    public void append(int key, boolean value) {
        if (mSize != 0 && key <= mKeys[mSize - 1]) {
            put(key, value);
            return;
        }

        mKeys = append(mKeys, mSize, key);
        mValues = append(mValues, mSize, value);
        mSize++;
    }

    @Override
    public int hashCode() {
        int hashCode = mSize;
        for (int i = 0; i < mSize; i++) {
            hashCode = 31 * hashCode + mKeys[i] | (mValues[i] ? 1 : 0);
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (!(that instanceof SparseBooleanArray)) {
            return false;
        }

        SparseBooleanArray other = (SparseBooleanArray) that;
        if (mSize != other.mSize) {
            return false;
        }

        for (int i = 0; i < mSize; i++) {
            if (mKeys[i] != other.mKeys[i]) {
                return false;
            }
            if (mValues[i] != other.mValues[i]) {
                return false;
            }
        }
        return true;
    }

    private static int binarySearch(int[] array, int size, int value) {
        int lo = 0;
        int hi = size - 1;

        while (lo <= hi) {
            final int mid = (lo + hi) >>> 1;
            final int midVal = array[mid];

            if (midVal < value) {
                lo = mid + 1;
            } else if (midVal > value) {
                hi = mid - 1;
            } else {
                return mid;  // value found
            }
        }
        return ~lo;  // value not present
    }

    private static int growSize(int currentSize) {
        return currentSize <= 4 ? 8 : currentSize * 2;
    }

    private static boolean[] insert(boolean[] array, int currentSize, int index, boolean element) {
        assert currentSize <= array.length;

        if (currentSize + 1 <= array.length) {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }

        boolean[] newArray = new boolean[growSize(currentSize)];
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        return newArray;
    }

    private static int[] insert(int[] array, int currentSize, int index, int element) {
        assert currentSize <= array.length;

        if (currentSize + 1 <= array.length) {
            System.arraycopy(array, index, array, index + 1, currentSize - index);
            array[index] = element;
            return array;
        }

        int[] newArray = new int[growSize(currentSize)];
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        return newArray;
    }

    private static boolean[] append(boolean[] array, int currentSize, boolean element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            boolean[] newArray = new boolean[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    private static int[] append(int[] array, int currentSize, int element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            int[] newArray = new int[growSize(currentSize)];
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    @Override
    public String toString() {
        if (size() <= 0) {
            return "{}";
        }

        StringBuilder buffer = new StringBuilder(mSize * 28);
        buffer.append('{');
        for (int i = 0; i < mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            int key = keyAt(i);
            buffer.append(key);
            buffer.append('=');
            boolean value = valueAt(i);
            buffer.append(value);
        }
        buffer.append('}');
        return buffer.toString();
    }
}