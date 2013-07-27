/**
 * 
 */
package com.livefyre.streamhub_android_sdk;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.net.Uri.Builder;
import android.os.Handler.Callback;
import android.text.TextUtils;

/**
 * @author zjj
 *
 */
public class PublicAPIClient {
	/**
	 * Performs a network request on a different thread and delivers a message to the callback. A JSON object with the results will be bound to the message.
	 * @see https://github.com/Livefyre/livefyre-docs/wiki/Hottest-Collection-API
	 * 
	 * @param tag Tag to filter on. Optional.
	 * @param siteId Site ID to filter on. Optional.
	 * @param networkDomain The network to query against as identified by domain, i.e. livefyre.com.
	 * @param requestResults Number of results to be returned. The default is 10 and the maximum is 100. Optional.
	 * @param handler Implement "handleMessage" for this callback.
	 * @throws MalformedURLException
	 */
	public static void getHottestCollectionsInBackground
	(String tag, String siteId, String networkDomain, Integer requestResults, StreamhubResponseHandler handler) throws MalformedURLException {
		URL hottestCollectionsEndpoint = generateHottestCollectionsEndpoint(tag, siteId, networkDomain, requestResults);
		
		new StreamhubGetRequest(handler).execute(hottestCollectionsEndpoint);
	}
	
	/**
	 * Generates a user content endpoint with the specified parameters.
	 *
	 * @param tag Tag to filter on. Optional.
	 * @param siteId Site ID to filter on. Optional.
	 * @param networkDomain The network to query against as identified by domain, i.e. livefyre.com.
	 * @param requestResults Number of results to be returned. The default is 10 and the maximum is 100. Optional.
	 * @return Trending Collections endpoint with the specified parameters.
	 * @throws MalformedURLException
	 */
	public static URL generateHottestCollectionsEndpoint
	(String tag, String siteId, String networkDomain, Integer requestResults) throws MalformedURLException {
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
		StringBuilder urlStringBuilder = new StringBuilder(Constants.scheme)
		.append(Constants.bootstrapDomain).append(".")
		.append(networkDomain)
		.append("/api/v3.0/hottest/")
		.append(paramsBuilder.toString());

		return Helpers.generateURL(urlStringBuilder.toString());
	}
	
	/**
	 * Performs a network request on a different thread and delivers a message to the callback. A JSON object with the results will be bound to the message.
	 * @see https://github.com/Livefyre/livefyre-docs/wiki/User-Content-API
	 * 
	 * @param userId The Id of the user whose content is to be fetched. 
	 * @param userToken The lftoken of the user whose content is to be fetched. This parameter is required by default unless the network specifies otherwise.
	 * @param networkDomain The network to query against as identified by domain, i.e. livefyre.com. 
	 * @param statuses CSV of comment states to return. Optional.
	 * @param offset Number of results to skip, defaults to 0. 25 items are returned at a time. Optional.
	 * @param handler
	 * @throws MalformedURLException
	 */
	public static void getUserContentInBackground
	(String userId, String userToken, String networkDomain, List<String> statuses, Integer offset, StreamhubResponseHandler handler) throws MalformedURLException {
		URL userContentEndpoint = generateUserContentEndpoint(userId, userToken, networkDomain, statuses, offset);

		new StreamhubGetRequest(handler).execute(userContentEndpoint);
	}
	
	/**
	 * 
	 * @param userId The Id of the user whose content is to be fetched. 
	 * @param userToken The lftoken of the user whose content is to be fetched. This parameter is required by default unless the network specifies otherwise. 
	 * @param networkDomain The network to query against as identified by domain, i.e. livefyre.com.  
	 * @param statuses CSV of comment states to return. Optional. 
	 * @param offset Number of results to skip, defaults to 0. 25 items are returned at a time. Optional.
	 * @return User Content endpoint with the specified parameters.
	 * @throws MalformedURLException
	 */
	public static URL generateUserContentEndpoint
	(String userId, String userToken, String networkDomain, List<String> statuses, Integer offset) throws MalformedURLException {
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
		StringBuilder urlStringBuilder = new StringBuilder(Constants.scheme)
		.append(Constants.bootstrapDomain).append(".")
		.append(networkDomain)
		.append("/api/v3.0/author/")
		.append(userId)
		.append("/comments/")
		.append(paramsBuilder.toString());
				
		return Helpers.generateURL(urlStringBuilder.toString());
	}
}
