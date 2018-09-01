package com.ntaylor.lessonscheduler.tasks.aws.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 * Stores data related to and provides access to REST functions.
 * Created by Nathan Taylor on 8/15/2018
 */
public class RestClient {

    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String GET = "GET";

    private static final String SEND_COOKIES = "Cookie";
    private static final String RECEIVE_COOKIES = "Set-Cookie";

    private static final int TIMEOUT = 5000; // 5 second timeout

    private static SSLSocketFactory socketFactory;
    private static CookieManager cookieManager;

    /**
     * Creates a connection with the given uri and sets its properties, such as timeouts.
     * @param uri The location to connect to
     * @param method The type of HTTP connection (e.g. POST, GET)
     * @return The connection, or null if there was an error creating the connection
     */
    private static HttpURLConnection getConnection(String uri, String method) throws MalformedURLException {
        if (cookieManager == null){
            cookieManager = new CookieManager();
        }

        String noSpaces = uri.replace(" ", "");
        URL url  = new URL(noSpaces);
        try{
            // Set up the connection
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setDoInput(true);
            connection.setRequestMethod(method);

            // Set content type for POST requests
            if (method.equals(POST)){
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            }

            // Add cookies, if available
            List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
            if (cookies != null && cookies.size() > 0){
                connection.setRequestProperty(SEND_COOKIES, TextUtils.join(";", cookies));
            }

            return connection;
        }
        catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Sends the given POST request to the uri specified and retrieves its response.
     * @param uri The URI to send the message to
     * @param json The body of the POST request
     * @return The message received from the server, or null if no message was received or an error occurred
     */
    public static String getPostResponse(String uri, String json){
        try {
            HttpURLConnection connection = getConnection(uri, RestClient.POST);

            if (connection == null) {
                return null;
            }

            // Send the JSON
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(json);

            connection.connect();
            writer.flush(); // Gets the message to the

            int code = connection.getResponseCode();

            // Get the message
            InputStream input = ((code / 200) == 1) ? connection.getInputStream() : connection.getErrorStream();
            String message = readResponse(input);

            // Close resources
            closeConnection(connection);
            writer.close();

            // Return the message
            return message;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Sends the given POST request to the uri specified and retrieves its response.
     * @param uri The URI to send the message to
     * @param json The body of the POST request
     * @return The message received from the server, or null if no message was received or an error occurred
     */
    public static String getPutResponse(String uri, String json){
        try {
            HttpURLConnection connection = getConnection(uri, RestClient.PUT);

            if (connection == null) {
                return null;
            }

            // Send the JSON
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(json);

            connection.connect();
            writer.flush(); // Gets the message to the

            int code = connection.getResponseCode();

            // Get the message
            InputStream input = ((code / 200) == 1) ? connection.getInputStream() : connection.getErrorStream();
            String message = readResponse(input);

            // Close resources
            closeConnection(connection);
            writer.close();

            // Return the message
            return message;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Sends the given GET request to the uri specified and retrieves its response.
     * @param uri The URI to send the message to
     * @return The message received from the server, or null if no message was received or an error occurred
     */
    public static String getGetResponse(String uri){
        try {
            HttpURLConnection connection = getConnection(uri, RestClient.GET);

            if (connection == null) {
                return null;
            }

            connection.connect();

            int code = connection.getResponseCode();

            // Get the message
            InputStream input = ((code / 200) == 1) ? connection.getInputStream() : connection.getErrorStream();
            String message = readResponse(input);

            // Close resources
            closeConnection(connection);

            // Return the message
            return message;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Stores any cookies returned and closes the connection.
     * @param connection The connection to close
     */
    private static void closeConnection(HttpURLConnection connection){
        Map<String, List<String>> params = connection.getHeaderFields();
        if (params != null && cookieManager != null){
            List<String> cookies = params.get(RECEIVE_COOKIES);
            if (cookies != null){
                for (String cookie : cookies){
                    List<HttpCookie> parsed = HttpCookie.parse(cookie);
                    cookieManager.getCookieStore().add(null, parsed.get(0));
                }
            }
        }

        connection.disconnect();
    }

    /**
     * Reads the response from the given InputStream
     * @param in Usually either connection.getInputStream() or connection.getErrorStream()
     * @return The String response, or null if an error occurred
     */
    private static String readResponse(InputStream in){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            StringBuilder response = new StringBuilder();

            while((line = reader.readLine()) != null){
                response.append(line);
            }

            return response.toString();
        }
        catch(IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
}