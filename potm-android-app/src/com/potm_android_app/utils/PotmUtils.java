package com.potm_android_app.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class PotmUtils {

    public static void showToast(Context context, String message) {
        Toast makeText = Toast.makeText(context, message, Toast.LENGTH_LONG);
        makeText.setGravity(Gravity.TOP, 0, 0);
        makeText.show();
    }

}
