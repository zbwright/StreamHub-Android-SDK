package com.livefyre.android.test;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zjj
 */
public class GETJSON {
    /**
     * Wrapper around fetchData(URL endpoint)
     *
     * @param endpoint a string representing URL.
     * @return A JSONObject with the result
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject fetchData(String endpoint)
            throws IOException, JSONException
    {
        return fetchData(new URL(endpoint));
    }

    /**
     * Performs a GET request for the given endpoint and returns a JSON object derived from
     * the results.
     *
     * @param endpoint An HTTP endpoint to request.
     * @return A JSONObject with the results.
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject fetchData(URL endpoint)
            throws IOException, JSONException
    {
        JSONObject data = null;
        HttpURLConnection urlConnection = (HttpURLConnection)endpoint.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder jsonBuilder = new StringBuilder();
            int inChar;
            while ((inChar = reader.read()) != -1) {
                char character = (char)inChar;
                jsonBuilder.append(character);
            }
            in.close();
            data = new JSONObject(jsonBuilder.toString());
        } finally {
            urlConnection.disconnect();
        }
        return data;
    }
}
