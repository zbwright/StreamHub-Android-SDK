package com.livefyre.android.core;
import java.io.IOException;
import android.net.Uri.Builder;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class WriteClient {
	public static void likeContent
	(String networkId, String collectionId, String contentId, String token, JsonHttpResponseHandler handler) throws IOException {
        makeOpineRequest(networkId, collectionId, contentId, token, "like", handler);
	}

    public static void unlikeContent
            (String networkId, String collectionId, String contentId, String token, JsonHttpResponseHandler handler) throws IOException {
        makeOpineRequest(networkId, collectionId, contentId, token, "unlike", handler);
    }
	
    private static void makeOpineRequest
    (String networkId, String collectionId, String contentId, String token, String action, JsonHttpResponseHandler handler) {
        Builder paramsBuilder = new Builder();
        paramsBuilder.appendQueryParameter("lftoken", token);

        // Build the URL
        StringBuilder urlStringBuilder = new StringBuilder(Config.scheme)
                .append(Config.quillDomain).append(".")
                .append(Config.getHostname(networkId))
                .append("/api/v3.0/message/")
                .append(contentId).append("/")
                .append(action)
                .append(paramsBuilder.toString());

        HttpClient.client.post(urlStringBuilder.toString(),
                new RequestParams("collection_id", collectionId), handler);
    }

	public static void postContent
	(String networkId, String collectionId, String parentId, String token, String body, JsonHttpResponseHandler handler){
        Builder paramsBuilder = new Builder();
        paramsBuilder.appendQueryParameter("lftoken", token);

        // Build the URL
        StringBuilder urlStringBuilder = new StringBuilder(Config.scheme)
                .append(Config.quillDomain).append(".")
                .append(Config.getHostname(networkId))
                .append("/api/v3.0/collection/")
                .append(collectionId)
                .append("/post/")
                .append(paramsBuilder.toString());

        HttpClient.client.post(urlStringBuilder.toString(),
                new RequestParams("body", body), handler);
    }

}
