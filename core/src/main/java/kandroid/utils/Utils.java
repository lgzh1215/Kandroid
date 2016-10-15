package kandroid.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    volatile int i = 1;

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault());

    public static int[] listToArray(List<Integer> list) {
        int size = list.size();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    public static String getDateString(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String md5(String s) {
        try {
            return new BigInteger(1, MessageDigest.getInstance("MD5").digest(s.getBytes())).toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("All")
    @Deprecated
    public static boolean isAssertOpen() {
        boolean isAssertionOpen = false;
        assert isAssertionOpen = true;
        return isAssertionOpen;
    }
}