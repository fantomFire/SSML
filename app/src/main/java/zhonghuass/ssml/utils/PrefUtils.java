package zhonghuass.ssml.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference工具类封装
 */
public class PrefUtils {

    public static void putBoolean(Context ctx, String key, boolean value) {

        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static String putString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
        return key;
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void putInt(Context ctx, String key, int value) {

        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    //删除某个字段
    public static void remove(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }


}