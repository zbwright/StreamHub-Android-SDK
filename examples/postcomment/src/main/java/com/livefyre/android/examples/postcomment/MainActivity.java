package com.livefyre.android.examples.postcomment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.livefyre.android.core.WriteClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.editText);

                WriteClient.postContent("networkId", "collectionId", "parentId (optional)", "token",
                        editText.getText().toString(), new PostCommentCallback());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private class PostCommentCallback extends JsonHttpResponseHandler {
        public void onSuccess(JSONObject data) {
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(data.toString());
        }
    }
}