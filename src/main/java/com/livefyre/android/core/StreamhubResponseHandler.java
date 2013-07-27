package com.livefyre.android.core;

import org.json.JSONObject;

public interface StreamhubResponseHandler {
    void handle(JSONObject obj);
}
