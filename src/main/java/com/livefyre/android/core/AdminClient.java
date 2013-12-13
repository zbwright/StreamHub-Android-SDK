package com.livefyre.android.core;

import android.net.Uri.Builder;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * @author zjj
 */
public class AdminClient {
    /**
     * Performs a network request on a different thread and delivers a message to the callback.
     * A JSON object with the results will be bound to the message.
     * <p/>
     * It is necessary to provide either a collectionId or a siteId combined with an articleId.
     *
     * @param userToken    The lftoken representing a user.
     * @param collectionId The Id of the collection to auth against.
     * @param articleId    The Id of the collection's article.
     * @param siteId       The Id of the article's site.
     * @param networkId    The collection's network as identified by domain, i.e. livefyre.com.
     * @param handler      Implement "handleMessage" for this callback.
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     */
    public static void authenticateUser(String userToken,
                                        String collectionId,
                                        String articleId,
                                        String siteId,
                                        String networkId,
                                        JsonHttpResponseHandler handler)
            throws UnsupportedEncodingException
    {
        String authEndpoint =
                generateAuthEndpoint(userToken, collectionId, articleId, siteId, networkId);
        HttpClient.client.get(authEndpoint, handler);
    }

    /**
     * Generates an auth endpoint with the specified parameters.
     *
     * @param userToken    The lftoken representing a user.
     * @param collectionId The Id of the collection to auth against.
     * @param articleId    The Id of the collection's article.
     * @param siteId       The Id of the article's site.
     * @param networkId    The collection's network as identified by domain, i.e. livefyre.com.
     * @return The auth endpoint with the specified parameters.
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     */
    public static String generateAuthEndpoint(String userToken,
                                              String collectionId,
                                              String articleId,
                                              String siteId,
                                              String networkId)
            throws UnsupportedEncodingException
    {
        Builder paramsBuilder = new Builder();
        if (collectionId != null) {
            paramsBuilder.appendQueryParameter("collectionId", collectionId);
            paramsBuilder.appendQueryParameter("lftoken", userToken);
        } else {
            String article64 = Helpers.generateBase64String(articleId);
            paramsBuilder.appendQueryParameter("siteId", siteId);
            paramsBuilder.appendQueryParameter("articleId", article64);
            paramsBuilder.appendQueryParameter("lftoken", userToken);
        }

        // Build the URL
        StringBuilder urlStringBuilder = new StringBuilder(Config.scheme)
                .append(Config.adminDomain).append(".")
                .append(Config.getHostname(networkId))
                .append("/api/v3.0/auth/")
                .append(paramsBuilder.toString());

        return urlStringBuilder.toString();
    }
}
