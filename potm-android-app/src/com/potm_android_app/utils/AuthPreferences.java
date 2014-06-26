
package com.potm_android_app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public final class AuthPreferences {

    private static final String KEY_USER = "user";
    private static final String KEY_TOKEN = "token";

    private static SharedPreferences preferences;

    private AuthPreferences(Context context) {
    }

    public static SharedPreferences getInstance(Activity activity) {
        synchronized (activity) {
    	if (preferences == null) {
            preferences = activity
                    .getSharedPreferences("auth", Context.MODE_PRIVATE);
        }

        return preferences;
        }
    }

    public static void setUser(Activity activity, String user) {
        getInstance(activity).edit().putString(KEY_USER, user).commit();
    }

    public static void setToken(Activity activity, String password) {
        getInstance(activity).edit().putString(KEY_TOKEN, password).commit();
    }

    public static String getUser(Activity activity) {
        return getInstance(activity).getString(KEY_USER, null);
    }

    public static String getToken(Activity activity) {
        return getInstance(activity).getString(KEY_TOKEN, null);
    }
}
