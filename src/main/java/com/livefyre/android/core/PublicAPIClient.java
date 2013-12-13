package com.livefyre.android.core;

import android.net.Uri.Builder;
import android.text.TextUtils;

import com.loopj.android.http.JsonHttpResponseHandler;

import java.net.MalformedURLException;
import java.util.List;

/**
 * @author zjj
 */
public class PublicAPIClient {
    /**
     * Performs a network request on a different thread and delivers a message to the callback. A JSON object with the results will be bound to the message.
     *
     * @param networkId      The network to query against as identified by domain, i.e. livefyre.com.
     * @param siteId         Site ID to filter on. Optional.
     * @param tag            Tag to filter on. Optional.
     * @param requestResults Number of results to be returned. The default is 10 and the maximum is 100. Optional.
     * @param handler        Implement "handleMessage" for this callback.
     * @throws MalformedURLException
     * @see "https://github.com/Livefyre/livefyre-docs/wiki/Hottest-Collection-API"
     */
    public static void getHottestCollections
    (String networkId, String siteId, String tag, Integer requestResults, JsonHttpResponseHandler handler) throws MalformedURLException {
        String hottestCollectionsEndpoint = generateHottestCollectionsEndpoint(networkId, siteId, tag, requestResults);
        HttpClient.client.get(hottestCollectionsEndpoint, handler);
    }

    /**
     * Generates a user content endpoint with the specified parameters.
     *
     * @param networkId      The network to query against as identified by domain, i.e. livefyre.com.
     * @param siteId         Site ID to filter on. Optional.
     * @param tag            Tag to filter on. Optional.
     * @param requestResults Number of results to be returned. The default is 10 and the maximum is 100. Optional.
     * @return Trending Collections endpoint with the specified parameters.
     * @throws MalformedURLException
     */
    public static String generateHottestCollectionsEndpoint
    (String networkId, String siteId, String tag, Integer requestResults) throws MalformedURLException {
        // Build the Query Params
        Builder paramsBuilder = new Builder();
        if (tag != null) {
            paramsBuilder.appendQueryParameter("tag", tag);
        }
        if (siteId != null) {
            paramsBuilder.appendQueryParameter("site", siteId);
        }
        if (requestResults != null) {
            paramsBuilder.appendQueryParameter("number", Integer.toString(requestResults));
        }

        // Build the URL
        StringBuilder urlStringBuilder = new StringBuilder(Config.scheme)
                .append(Config.bootstrapDomain).append(".")
                .append(Config.getHostname(networkId))
                .append("/api/v3.0/hottest/")
                .append(paramsBuilder.toString());

        return urlStringBuilder.toString();
    }

    /**
     * Performs a network request on a different thread and delivers a message to the callback. A JSON object with the results will be bound to the message.
     *
     * @param networkId The network to query against as identified by domain, i.e. livefyre.com.
     * @param userId    The Id of the user whose content is to be fetched.
     * @param userToken The lftoken of the user whose content is to be fetched. This parameter is required by default unless the network specifies otherwise.
     * @param statuses  CSV of comment states to return. Optional.
     * @param offset    Number of results to skip, defaults to 0. 25 items are returned at a time. Optional.
     * @throws MalformedURLException
     * @see "https://github.com/Livefyre/livefyre-docs/wiki/User-Content-API"
     */
    public static void getUserContent
    (String networkId, String userId, String userToken, List<String> statuses, Integer offset, JsonHttpResponseHandler handler) throws MalformedURLException {
        String userContentEndpoint = generateUserContentEndpoint(networkId, userId, userToken, statuses, offset);
        HttpClient.client.get(userContentEndpoint, handler);
    }

    /**
     * @param networkId The network to query against as identified by domain, i.e. livefyre.com.
     * @param userId    The Id of the user whose content is to be fetched.
     * @param userToken The lftoken of the user whose content is to be fetched. This parameter is required by default unless the network specifies otherwise.
     * @param statuses  CSV of comment states to return. Optional.
     * @param offset    Number of results to skip, defaults to 0. 25 items are returned at a time. Optional.
     * @return User Content endpoint with the specified parameters.
     * @throws MalformedURLException
     */
    public static String generateUserContentEndpoint
    (String networkId, String userId, String userToken, List<String> statuses, Integer offset) throws MalformedURLException {
        //Build the query params
        Builder paramsBuilder = new Builder();
        if (userToken != null) {
            paramsBuilder.appendQueryParameter("lftoken", userToken);
        }
        if (statuses != null) {
            paramsBuilder.appendQueryParameter("status", TextUtils.join(",", statuses));
        }
        if (offset != null) {
            paramsBuilder.appendQueryParameter("offset", Integer.toString(offset));
        }

        //Build the URL
        StringBuilder urlStringBuilder = new StringBuilder(Config.scheme)
                .append(Config.bootstrapDomain).append(".")
                .append(Config.getHostname(networkId))
                .append("/api/v3.0/author/")
                .append(userId)
                .append("/comments/")
                .append(paramsBuilder.toString());

        return urlStringBuilder.toString();
    }
}
