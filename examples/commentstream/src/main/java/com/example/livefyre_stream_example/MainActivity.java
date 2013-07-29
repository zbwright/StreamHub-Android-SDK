package com.example.livefyre_stream_example;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.livefyre.android.core.BootstrapClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            BootstrapClient.getInitInBackground("51", "290596", "livefyre.com", "qa-ext.livefyre.com",
                    new InitCallback());

        } catch (UnsupportedEncodingException e) {

        } catch (MalformedURLException e) {

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void addTextView(String content) {
        TextView valueTV = new TextView(MainActivity.this);
        valueTV.setText(content);
        valueTV.setId(5);
        valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout1);
        layout.addView(valueTV);
    }


    private class InitCallback extends JsonHttpResponseHandler {
        public void onSuccess(JSONObject data) {
            try {
                JSONArray contentArr = data.getJSONObject("headDocument").getJSONArray("content");
                for (int idx=0; idx<contentArr.length(); idx++) {
                    JSONObject contentObj = contentArr.getJSONObject(idx);
                    if (contentObj.getInt("type") != 0) {
                        continue;
                    }
                    if (contentObj.getInt("vis") != 1) {
                        continue;
                    }
                    addTextView(contentObj.getJSONObject("content").getString("bodyHtml"));
                }
           } catch (JSONException e) {

            }

        }
    }
}