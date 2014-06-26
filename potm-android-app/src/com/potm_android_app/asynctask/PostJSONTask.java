
package com.potm_android_app.asynctask;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.potm_android_app.utils.MyLog;
import com.potm_android_app.utils.PotmUtils;

public class PostJSONTask extends AsyncTask<String, Void, String> {
    private final Context mContext;

    public PostJSONTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... paramns) {

        int trys = 10;
        String result = null;

        while (trys > 0) {
            trys--;

            try {

                MyLog.info("POST: " + PotmUtils.getServerURL() + " - " + paramns[0]);

                result = PotmUtils.sendPOST(PotmUtils.getServerURL(), paramns[0]);

                if (result != null) {
                    break;
                }

            } catch (final IOException e) {
                MyLog.debug("Unable to retrieve web page. URL may be invalid: "
                        + PotmUtils.getServerURL() + " - " + paramns[0]);
            }

            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                MyLog.error(e.getMessage());
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String msg) {
        try {
            ((PostJSONInterface) mContext).callbackPostJSON(new JSONObject(msg));
        } catch (JSONException e) {
            MyLog.error("Error when creating JSON on PostJSONTask", e);
        }

    }

    public interface PostJSONInterface {
        public void callbackPostJSON(JSONObject msg);
    }
}
