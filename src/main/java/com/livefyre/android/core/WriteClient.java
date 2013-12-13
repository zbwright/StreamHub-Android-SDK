package com.livefyre.android.core;

import android.net.Uri.Builder;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

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
                                   String token,
                                   String body,
                                   JsonHttpResponseHandler handler)
    {
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

        RequestParams bodyParams = new RequestParams();
        bodyParams.put("body", body);

        if (parentId != null && parentId.length() != 0) {
            bodyParams.put("parent_id", parentId);
        }
        HttpClient.client.post(urlStringBuilder.toString(), bodyParams, handler);
    }

}
