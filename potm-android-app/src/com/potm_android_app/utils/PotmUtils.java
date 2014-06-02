package com.potm_android_app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class PotmUtils {

    private static final String URL = "http://pomt.herokuapp.com/api/ti";

    public static void showToast(Context context, String message) {
        Toast makeText = Toast.makeText(context, message, Toast.LENGTH_LONG);
        makeText.setGravity(Gravity.TOP, 0, 0);
        makeText.show();
    }

    public static String getServerURL() {
        return URL;
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    public static String downloadUrl(String myurl) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            final URL url = new URL(myurl);
            final HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            is = conn.getInputStream();
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);

            String line;
            StringBuffer buf = new StringBuffer();

            while ((line = br.readLine()) != null) {
                buf.append(line);
            }

            is.close();
            isr.close();
            br.close();

            return buf.toString();

        } catch (IOException e) {
            MyLog.error("Error when downloading url", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                MyLog.error("Error when downloading url", e);
            }
        }

        return null;
    }

    public static String getUrl() {
        return URL;
    }

}
