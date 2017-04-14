package kandroid.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"unused", "WeakerAccess"})
public class JUtil {

    public static int fastHashNullable(@Nullable Object o) {
        return o == null ? 0 : fastHash(o);
    }

    public static int fastHash(@NotNull Object o) {
        return byteSwap32(o.hashCode());
    }

    @Contract(pure = true)
    public static int byteSwap32(int v) {
        return Integer.reverseBytes(v * 0x9e3775cd) * 0x9e3775cd;
    }

    private JUtil() {
    }
}
