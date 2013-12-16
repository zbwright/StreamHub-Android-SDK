package com.livefyre.android.test;

import android.content.res.Resources;

import com.livefyre.android.core.test.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;


public class MockURLStreamHandler extends URLStreamHandler implements URLStreamHandlerFactory
{
    public Resources res;

    public MockURLStreamHandler(Resources res)
    {
        this.res = res;
    }

    @Override
    protected URLConnection openConnection(URL inUrl)
            throws IOException, NullPointerException
    {
        System.out.println("Mocking opening a connection.");
        return new MockHttpURLConnection(inUrl);
    }

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol)
    {
        // ???
        return this;
    }

    // This is silly to nest the classes like this, but easiest way to load the resources we want.
    public class MockHttpURLConnection extends HttpURLConnection {
        private Integer mockFile;

        protected MockHttpURLConnection(URL inUrl)
                throws NullPointerException
        {
            super(inUrl);
            System.out.println("Mocking the Url...");
            String inString = inUrl.toString();
            System.out.println(inString);

            // AdminClient
            if (inString.equals("http://admin.mockDomain/api/v3.0/auth/?collectionId=mockCollId&lftoken=mocklftoken"))
            {
                System.out.println("Setting mock to auth sample");
                setMockFile(R.raw.auth_sample);
            }
            else if (inString.equals("http://admin.mockDomain/api/v3.0/auth/?siteId=mockSiteId&articleId=bW9ja0FydGljbGVJZA%3D%3D&lftoken=mocklftoken"))
            {
                System.out.println("Setting mock to auth sample");
                setMockFile(R.raw.auth_sample);
            }
            // BootstrapClient
            else if (inString.equals("http://bootstrap.mockDomain/bs3/mockDomain/mockSiteId/bW9ja0FydGljbGVJZA%3D%3D/init"))
            {
                System.out.println("Setting mock to bootstrap sample");
                setMockFile(R.raw.init_sample);
            }
            /*
            // Public API Client
            else if (inString.equals("http://bootstrap.mockDomain/api/v3.0/hottest/?tag=mockTag&number=22"))
            {
                System.out.println("Setting mock to hotness sample");
                setMockFile(R.raw.hottest_sample);
            }
            else if (inString.equals("http://bootstrap.mockDomain/api/v3.0/author/mockUserId/comments/?lftoken=mocklftoken"))
            {
                System.out.println("Setting mock to user content sample");
                setMockFile(R.raw.usercontent_sample);
            }
            */
            else if (inString.equals("http://bootstrap.mockTag/api/v3.0/hottest/?tag=mockDomain&number=22"))
            {
                System.out.println("Setting mock to hottest sample");
                setMockFile(R.raw.hottest_sample);
            }
            else if (inString.equals("http://bootstrap.mockDomain/api/v3.0/author/mockUserId/comments/?lftoken=mocklftoken"))
            {
                System.out.println("Setting mock to user comments sample");
                // TODO: check that this endpoint corresponds to a currently existing one
                setMockFile(R.raw.usercontent_sample);
            }
            else if (inString.equals("http://stream1.mockDomain/v3.0/collection/mockCollId/mockEventId"))
            {
                System.out.println("Setting mock to stream sample");
                setMockFile(R.raw.stream_sample);
            }
            if (this.mockFile == null) {
                throw new NullPointerException();
            }
            // Write Client...
        }

        @Override
        public InputStream getInputStream()
                throws IOException
        {
            System.out.println("Loading the mock file...");
            System.out.println(mockFile.toString());
            System.out.println("Setting mock resources");
            System.out.println("Opening mock resources");
            return res.openRawResource(mockFile);
        }

        @Override
        public void connect() throws IOException {
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        public Integer getMockFile() {
            return mockFile;
        }

        public void setMockFile(Integer mockFile) {
            this.mockFile = mockFile;
        }
    }
}