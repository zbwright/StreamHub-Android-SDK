package com.livefyre.android.core;

import android.net.Uri;
import android.net.Uri.Builder;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

public class StreamClient {
    public static String generateStreamUrl(String networkId, String collectionId, String eventId)
            throws MalformedURLException
    {
        final Builder uriBuilder = new Uri.Builder()
                .appendPath(Config.scheme)
                .appendPath(Config.streamDomain + "." + Config.getHostname(networkId))
                .appendPath("v3.0")
                .appendPath("collection")
                .appendPath(collectionId)
                .appendPath("")
                .appendPath(eventId);

        return uriBuilder.toString();
    }

    /**
     * Performs a long poll request to the Livefyre's stream endpoint
     *
     * @param networkId The collection's network as identified by domain, i.e. livefyre.com.
     * @param collectionId    The Id of the collection
     * @param eventId   The last eventId that was returned from either stream or
     *                  bootstrap. Event time a new eventId is returned, it should be used
     *                  in the next stream request.
     * @param handler   Response handler
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     */
    public static void pollStreamEndpoint(String networkId,
                                          String collectionId,
                                          String eventId,
                                          AsyncHttpResponseHandler handler)
            throws IOException, JSONException
    {
        final String streamEndpoint = generateStreamUrl(networkId, collectionId, eventId);
        HttpClient.client.get(streamEndpoint, handler);
    }
}
