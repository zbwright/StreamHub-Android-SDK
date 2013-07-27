package com.livefyre.streamhub_android_sdk;

import org.json.JSONObject;

public interface StreamhubResponseHandler {
    void handle(JSONObject obj);
}
