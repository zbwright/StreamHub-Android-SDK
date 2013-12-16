package com.livefyre.android.core;

import android.net.Uri;
import android.net.Uri.Builder;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.net.MalformedURLException;

public class WriteClient {
    public static void likeContent(String networkId,
                                   String collectionId,
                                   String contentId,
                                   String token,
                                   JsonHttpResponseHandler handler)
            throws IOException
    {
        makeOpineRequest(networkId, collectionId, contentId, token, "like", handler);
    }

    public static void unlikeContent(String networkId,
                                     String collectionId,
                                     String contentId,
                                     String token,
                                     JsonHttpResponseHandler handler)
            throws IOException
    {
        makeOpineRequest(networkId, collectionId, contentId, token, "unlike", handler);
    }

    private static void makeOpineRequest(String networkId,
                                         String collectionId,
                                         String contentId,
                                         String token,
                                         String action,
                                         JsonHttpResponseHandler handler)
    {
        // Build the URL
        final Builder uriBuilder = new Uri.Builder()
                .scheme(Config.scheme)
                .authority(Config.quillDomain + "." + Config.getHostname(networkId))
                .appendPath("api")
                .appendPath("v3.0")
                .appendPath("message")
                .appendPath(contentId)
                .appendPath(action)
                .appendQueryParameter("lftoken", token);

        HttpClient.client.post(uriBuilder.toString(),
                new RequestParams("collection_id", collectionId), handler);
    }

    public static String generateWriteURL(String networkId,
                                          String collectionId,
                                          String userToken)
            throws MalformedURLException
    {
        final Builder uriBuilder = new Uri.Builder()
                .scheme(Config.scheme)
                .authority(Config.quillDomain + "." + Config.getHostname(networkId))
                .appendPath("api")
                .appendPath("v3.0")
                .appendPath("collection")
                .appendPath(collectionId)
                .appendPath("post")
                .appendPath("")
                .appendQueryParameter("lftoken", userToken);

        return uriBuilder.toString();
    }

    /**
     * Post content to a Livefyre collection.
     *
     * @param networkId    The collection's network as identified by domain, i.e. livefyre.com.
     * @param collectionId The Id of the collection.
     * @param parentId     The id of the content to which this content is a reply.
     *                     If not necessary (that is, this is a top level post, then set to
     *                     empty string ("").
     * @param token        The token of the logged in user.
     * @param body         A string version of the HTML body
     * @param handler      Response handler
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     */
    public static void postContent(String networkId,
                                   String collectionId,
                                   String parentId,
                                   String userToken,
                                   String body,
                                   JsonHttpResponseHandler handler)
    throws MalformedURLException
    {
        // add body parameters
        RequestParams bodyParams = new RequestParams();
        bodyParams.put("body", body);
        if (parentId != null && parentId.length() != 0) {
            bodyParams.put("parent_id", parentId);
        }
        HttpClient.client.post(generateWriteURL(networkId, collectionId, userToken),
                bodyParams, handler);
    }
}
