package com.livefyre.android.test;

import android.content.res.Resources;
import android.test.InstrumentationTestCase;

import com.livefyre.android.core.AdminClient;
import com.livefyre.android.core.BootstrapClient;
import com.livefyre.android.core.PublicAPIClient;
import com.livefyre.android.core.StreamClient;
import com.livefyre.android.core.WriteClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientTest extends InstrumentationTestCase {
    private String articleId = "mockArticleId";
    private String siteId = "mockSiteId";
    private String collectionId = "mockCollId";
    private String networkDomain = "mockDomain";
    private String userToken = "mocklftoken";
    private String environment = null;
    private static MockURLStreamHandler mockHandler = null;

    public ClientTest() {
        super();
    }

    @Override
    public void setUp() {
        if (mockHandler == null) {
            Resources res = getInstrumentation().getContext().getResources();
            MockURLStreamHandler handler = new MockURLStreamHandler(res);
            System.out.println("Setting URL mock factory");
            URL.setURLStreamHandlerFactory(handler);
            mockHandler = handler;
        }
    }

    @Override
    public void tearDown() {
        //
    }

    public void testBootstrapGetInit() {
        String endpoint = null;
        try {
            endpoint = BootstrapClient.generateInitEndpoint(
                    networkDomain, siteId, articleId);
        } catch (UnsupportedEncodingException e) {
            fail("UnsupportedEncodingException");
        }
        //catch (MalformedURLException e) {
        //    fail("MalformedURLException");
        //}

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(endpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testAdminClientWithCollId() {
        String endpoint = null;

        try {
            endpoint = AdminClient.generateAuthEndpoint(
                    userToken, collectionId, articleId, siteId, networkDomain);
        } catch (UnsupportedEncodingException e) {
            fail("UnsupportedEncodingException");
        }
        //catch (MalformedURLException e) {
        //    fail("MalformedURLException");
        //}

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(endpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testAdminClientWithoutCollId() {
        String endpoint = null;
        try {
            endpoint = AdminClient.generateAuthEndpoint(
                    userToken, null, articleId, siteId, networkDomain);
        } catch (UnsupportedEncodingException e) {
            fail("UnsupportedEncodingException");
        }
        //catch (MalformedURLException e) {
        //    fail("MalformedURLException");
        //}

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(endpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testPublicAPIClientUserContent() {
        String endpoint = null;
        try {
            endpoint = PublicAPIClient.generateUserContentEndpoint(
                    networkDomain, "mockUserId", userToken, null, null);
        } catch (MalformedURLException e) {
            fail("MalformedURLException");
        }

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(endpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testPublicAPIClientHotness() {
        String endpoint = null;
        try {
            endpoint = PublicAPIClient.generateHottestCollectionsEndpoint(
                    "mockTag", null, networkDomain, 22);
        } catch (MalformedURLException e) {
            fail("MalformedURLException");
        }

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(endpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testStreamClientFetch() {
        String endpoint = null;
        try {
            endpoint = StreamClient.generateStreamUrl(
                    networkDomain, collectionId, "mockEventId");
        } catch (MalformedURLException e) {
            fail("MalformedURLException");
        }

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(endpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testWriteClientPost() {
        String endpoint = null;
        try {
            endpoint = WriteClient.generateWriteURL(
                    networkDomain, collectionId, userToken);
        } catch (MalformedURLException e) {
            fail("MalformedURLException");
        }

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(endpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }
}
