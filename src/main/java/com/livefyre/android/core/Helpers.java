package com.livefyre.android.core;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import android.net.Uri.Builder;

import android.util.Base64;

public class Helpers {
	public static String generateBase64String(String instring) throws UnsupportedEncodingException {
		byte[]byteTransform = null;
		String string64 = null; 
		try {
			byteTransform = instring.getBytes("UTF-8");			
		} catch (UnsupportedEncodingException e) {
			boolean DEBUG = BuildConfig.DEBUG;
		    if (DEBUG) System.err.println("Caught UnsupportedEncodingException in com.livefyre.core.core.Helpers.base64String: " + e.getStackTrace());
		    throw e; 
		} finally {
			string64 = Base64.encodeToString(byteTransform, Base64.NO_WRAP);
		}
		
		return string64;
	}
}
