package com.livefyre.android.core;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;

import android.net.Uri;
import android.net.Uri.Builder;

import com.loopj.android.http.JsonHttpResponseHandler;

public class StreamClient {
	public static String generateStreamUrl(String networkId, String collectionId, String eventId) throws MalformedURLException {
		Builder uriBuilder = new Uri.Builder();
		uriBuilder.appendPath(Config.scheme)
		.appendPath(Config.streamDomain).appendPath(".")
		.appendPath(Config.getHostname(networkId))
		.appendPath("/v3.0/collection/")
		.appendPath(collectionId)
		.appendPath("/")
        .appendPath(eventId);
		return uriBuilder.toString();
	}
	
	public static void pollStreamEndpoint(String networkId, String collectionId, String eventId, JsonHttpResponseHandler handler) throws IOException, JSONException {
		String streamEndpoint = generateStreamUrl(networkId, collectionId, eventId);
        HttpClient.client.get(streamEndpoint, handler);
	}
}
