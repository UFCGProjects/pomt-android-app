package com.potm_android_app.asynctask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.potm_android_app.utils.MyLog;
import com.potm_android_app.utils.PotmUtils;

public class DownloadJSONTask extends AsyncTask<String, Void, String> {
    private final Context context;

    public DownloadJSONTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        // params comes from the execute() call: params[0] is the url.

        int trys = 10;
        String result = null;

        while (trys > 0) {
            trys--;

            MyLog.info("GET: " + urls[0]);

            result = PotmUtils.downloadUrl(urls[0]);

            if (result != null) {
                break;
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
        JSONObject mainObject;

        try {
            mainObject = new JSONObject(result);

            ((DownloadJSONInterface) context).callback(mainObject);

        } catch (final JSONException e) {
            MyLog.debug(result);
            MyLog.error(e.getMessage());

        }
    }

    public interface DownloadJSONInterface {
        public void callback(JSONObject json);
    }
}