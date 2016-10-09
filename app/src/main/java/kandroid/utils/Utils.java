package kandroid.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault());

    public final static Gson GSON = new GsonBuilder().registerTypeAdapter(Date.class, new GsonDateParser()).create();

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

    private static class GsonDateParser implements JsonSerializer<Date>, JsonDeserializer<Date> {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsLong());
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