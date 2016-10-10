package kandroid.z;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import kandroid.data.KCDatabase;
import kandroid.utils.Utils;

public class JTest {

    public static void main(String[] args) {
        System.out.println(Utils.isAssertOpen());
        System.out.println(new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create().toJson(KCDatabase.INSTANCE));
    }
}
