package com.lugdunum.heptartuflette.lugdunum.Utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;


public class JsonUtils extends AsyncTask<URL, Integer, JSONArray> {

    public static String host = "c662cd1d.ngrok.io";
    public static String protocol = "http";

    protected JSONArray doInBackground(URL... urls) {
        int count = urls.length;
        long totalSize = 0;
        HttpURLConnection urlConnection = null;
        for (URL url:urls) {
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String inputLine;
                String s = "";
                while ((inputLine = br.readLine()) != null) {
                    s += inputLine;
                }

                //Converting to json
                try {
                    JSONArray json = new JSONArray(s);
                    return json;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}

//public final class JsonUtils {
//
//    private static final String TAG ="JsonUtils";
//    public static String protocol = "http";
////    public static String host = "398691f7.ngrok.io";
//public static String host = "www.google.com";
//    public static int port = 80;
//
//    private JsonUtils() {
//    }
//
//    /**
//     * Retourne le JSON donné par un GET au format String
//     * Doit être appelé dans une HttpRequestTask (hérite d'AsyncTask) ou un nouveau Thread
//     *
//     * @param url      URL
//     * @return String
//     */
//
//    public static String getJsonToString(URL url) {
//        if (url == null) {
//            return null;
//        }
//        StringBuilder builder = new StringBuilder();
//
//        try {
//            /* Le code ci-dessous est peut-être optionel mais peut être utile
//            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
//            System.setProperty("http.maxRedirects", "100");
//            HttpURLConnection.setFollowRedirects(true);*/
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//
//            /* Authentification (pas pour v0)
//            Authenticator.setDefault(new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(user, password.toCharArray());
//                }
//            });*/
//            //con.setInstanceFollowRedirects(false);
//            con.connect();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
//            String inputLine;
//            while ((inputLine = br.readLine()) != null) {
//                builder.append(inputLine);
//            }
//            br.close();
//        } catch (IOException e) {
//            Log.e(TAG,"Problème d'accès à l'URL " + url.getPath());
//            throw new RuntimeException(e);
//        }
//        return builder.toString();
//    }
//}
