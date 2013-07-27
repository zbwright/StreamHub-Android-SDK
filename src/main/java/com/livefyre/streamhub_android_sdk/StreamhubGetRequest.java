package com.livefyre.streamhub_android_sdk;
import java.io.IOException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Handler.Callback;
import android.os.AsyncTask;
import android.os.Message;

/**
 * 
 * @author zjj
 */
class StreamhubGetRequest extends AsyncTask<URL, Void, JSONObject>  {
    private StreamhubResponseHandler handler;

    StreamhubGetRequest(StreamhubResponseHandler handler) {
        this.handler = handler;
    }

	/**
	 * Performs a network request asynchronously. 
	 * Object is our wildcard, we expect a URL as arg0 and Callback as arg1.
	 */
	protected JSONObject doInBackground(URL... urls) {
		JSONObject data = null;
		try {
			data = GETJSON.fetchData(urls[0]);
		} catch (IOException e) {
			// Swallow the exception?
		} catch (JSONException e) {
			// Swallow the exception?
		}
		return data;
	}

    protected void onPostExecute(JSONObject result) {
        handler.handle(result);
    }
}
