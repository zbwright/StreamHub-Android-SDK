package com.livefyre.android.test;

import android.content.res.Resources;
import android.test.InstrumentationTestCase;

import com.livefyre.android.core.AdminClient;
import com.livefyre.android.core.BootstrapClient;
import com.livefyre.android.core.PublicAPIClient;

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
        String initEndpoint = null;
        try {
            initEndpoint = BootstrapClient.generateInitEndpoint(networkDomain, siteId, articleId);
        } catch (UnsupportedEncodingException e) {
            fail("UnsupportedEncodingException");
        }
        //catch (MalformedURLException e) {
        //    fail("MalformedURLException");
        //}

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(initEndpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testAdminClientWithCollId() {
        String authEndpoint = null;

        try {
            authEndpoint = AdminClient.generateAuthEndpoint(userToken, collectionId, articleId, siteId, networkDomain);
        } catch (UnsupportedEncodingException e) {
            fail("UnsupportedEncodingException");
        }
        //catch (MalformedURLException e) {
        //    fail("MalformedURLException");
        //}

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(authEndpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testAdminClientWithoutCollId() {
        String authEndpoint = null;
        try {
            authEndpoint = AdminClient.generateAuthEndpoint(userToken, null, articleId, siteId, networkDomain);
        } catch (UnsupportedEncodingException e) {
            fail("UnsupportedEncodingException");
        }
        //catch (MalformedURLException e) {
        //    fail("MalformedURLException");
        //}

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(authEndpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testPublicAPIClientUserContent() {
        String userContentEndpoint = null;
        try {
            userContentEndpoint = PublicAPIClient.generateUserContentEndpoint("mockUserId", userToken, networkDomain, null, null);
        } catch (MalformedURLException e) {
            fail("MalformedURLException");
        }

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(userContentEndpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    public void testPublicAPIClientHotness() {
        String hotnessEndpoint = null;
        try {
            hotnessEndpoint = PublicAPIClient.generateHottestCollectionsEndpoint("mockTag", null, networkDomain, 22);
        } catch (MalformedURLException e) {
            fail("MalformedURLException");
        }

        JSONObject data = null;
        try {
            data = GETJSON.fetchData(hotnessEndpoint);
        } catch (IOException e) {
            fail("IOException");
        } catch (JSONException e) {
            fail("JSONException");
        }
        assertNotNull(data);
    }

    // TODO WriteClient not tested. StreamClient not tested.
}
