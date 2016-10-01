package kandroid.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.util.*;
import com.google.gson.*;


public final class Utils {

	static {
		GSON = new GsonBuilder().serializeNulls().create();
	}
	
    public static final Gson GSON;
    public static final JsonParser JSON_PARSER = new JsonParser();

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
}
