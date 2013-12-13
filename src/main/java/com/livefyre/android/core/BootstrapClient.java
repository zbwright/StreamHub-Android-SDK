package com.livefyre.android.core;

//import com.loopj.android.http.JsonHttpResponseHandler;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

/**
 * @author zjj
 */
public class BootstrapClient {
    /**
     * Performs a network request on a different thread and delivers a message to the callback.
     * A JSON object with the results will be bound to the message.
     *
     * @param networkId The collection's network as identified by domain, i.e. livefyre.com.
     * @param siteId    The Id of the article's site.
     * @param articleId The Id of the collection's article.
     * @param handler   Response handler
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     */
    public static void getInit
    (String networkId, String siteId, String articleId, AsyncHttpResponseHandler handler)
            throws UnsupportedEncodingException {
        String initEndpoint = generateInitEndpoint(networkId, siteId, articleId);
        HttpClient.client.get(initEndpoint, handler);
    }

    /**
     * Generates an init endpoint with the specified parameters.
     *
     * @param networkId The collection's network as identified by domain, i.e. livefyre.com.
     * @param siteId    The Id of the article's site.
     * @param articleId The Id of the collection's article.
     * @return The init endpoint with the specified parameters.
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     */
    public static String generateInitEndpoint(String networkId,
                                              String siteId,
                                              String articleId)
            throws UnsupportedEncodingException {
        // Casting
        String article64 = Helpers.generateBase64String(articleId);
        String urlSafeArticle64 = URLEncoder.encode(article64, "UTF-8");
        // Build the URL
        StringBuilder urlStringBuilder = new StringBuilder(Config.scheme)
                .append(Config.bootstrapDomain).append(".")
                .append(Config.getHostname(networkId))
                .append("/bs3/")
                .append(networkId).append("/")
                .append(siteId).append("/")
                .append(urlSafeArticle64).append("/")
                .append("init");
        return urlStringBuilder.toString();
    }
}
