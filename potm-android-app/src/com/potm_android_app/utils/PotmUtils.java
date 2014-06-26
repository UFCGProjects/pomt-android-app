
package com.potm_android_app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

import com.potm_android_app.R;

public class PotmUtils {

    //    private static final String URL = "http://pomt.herokuapp.com/api/ti";
    private static final String URL = "http://192.168.1.244:5000/api/ti";

    /**
     * The Constant mFormat.
     */
    private static final String FORMAT_SHORT = "HH:mm";

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

    public static String sendPOST(String myurl, String paramns)
            throws IOException {

        final URL url = new URL(myurl);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        final OutputStreamWriter osw = new OutputStreamWriter(
                conn.getOutputStream());
        osw.write(paramns);
        osw.flush();

        final BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));

        String contentAsString = "";
        String line;

        while ((line = br.readLine()) != null) {
            contentAsString += line;
        }

        osw.close();
        br.close();

        return contentAsString;
    }

    public static boolean isConnected(Activity act) {
        final ConnectivityManager connMgr = (ConnectivityManager) act
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }

    public static void showNotConnected(Context context) {
        Toast.makeText(context,
                context.getString(R.string.connection_unavailable),
                Toast.LENGTH_LONG).show();
    }

    public static void showCantWithoutConnection(Context context) {
        Toast.makeText(context,
                context.getString(R.string.cant_without_connection),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets the date time format.
     * 
     * @return the date time format
     */
    public static DateTimeFormatter getDateTimeFormat() {
        return DateTimeFormat.forPattern(FORMAT_SHORT);
    }

    public static String formatDouble(double num) {
        DecimalFormat df = new DecimalFormat("#,###.00");

        boolean hasFloating = false;

        if (num - (int) num != 0) {
            hasFloating = true;
        }

        if (hasFloating) {
            return df.format(num);
        } else {
            return String.valueOf((int) num);
        }

    }

}
