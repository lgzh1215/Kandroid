package kandroid.utils;

public class Utils {

    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    @Deprecated
    @SuppressWarnings("All")
    public static boolean isAssertOpen() {
        boolean isAssertionOpen = false;
        assert isAssertionOpen = true;
        return isAssertionOpen;
    }
}