package com.potm_android_app.asynctask;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.potm_android_app.MainActivity;
import com.potm_android_app.utils.MyLog;
import com.potm_android_app.utils.PotmUtils;

public class DownloadJSONTask extends AsyncTask<String, Void, String> {
    private final Context context;
    private String[] urls;

    public DownloadJSONTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        this.urls = urls;

        // params comes from the execute() call: params[0] is the url.

        int trys = 10;
        String result = null;

        while (trys > 0) {
            trys--;

            try {

                MyLog.info("GET: " + urls[0]);

                result = PotmUtils.downloadUrl(urls[0]);

                if (result != null) {
                    break;
                }

            } catch (final IOException e) {
                MyLog.debug("Unable to retrieve web page. URL may be invalid: "
                        + urls[0]);
            }

            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                MyLog.error(e.getMessage());
            }
        }

        return result;
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        JSONArray mainObject;

        try {
            mainObject = new JSONArray(result);

            ((DownloadJSONInterface) context).callback(mainObject);

        } catch (final JSONException e) {
            MyLog.debug(result);
            MyLog.error(e.getMessage());

        }
    }

    public interface DownloadJSONInterface {
        public void callback(JSONArray json);
    }
}