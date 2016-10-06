package moe.lpj.kandroid.utils;

import android.app.Activity;
import android.view.View;

import kandroid.utils.Utils;

@SuppressWarnings("unchecked")
public class UtilsA extends Utils {

    public static <T> T findViewById(View v, int id) {
        return requireNonNull((T) v.findViewById(id));
    }

    public static <T> T findViewById(Activity a, int id) {
        return requireNonNull((T) a.findViewById(id));
    }
}
