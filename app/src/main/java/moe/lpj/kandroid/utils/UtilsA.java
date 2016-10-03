package moe.lpj.kandroid.utils;

import android.app.Activity;
import android.view.View;

public class UtilsA {

    public static <T> T findViewById(View v, int id) {
        return (T) v.findViewById(id);
    }

    public static <T> T findViewById(Activity a, int id) {
        return (T) a.findViewById(id);
    }
}
