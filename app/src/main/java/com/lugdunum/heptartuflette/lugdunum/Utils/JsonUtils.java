package com.lugdunum.heptartuflette.lugdunum.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class JsonUtils {

    private static final String TAG ="JsonUtils";

    private JsonUtils() {
    }

    /**
     * Retourne le JSON donné par un GET au format String
     * Doit être appelé dans une HttpRequestTask (hérite d'AsyncTask) ou un nouveau Thread
     *
     * @param url      URL
     * @return String
     */
    public static String getJsonToString(URL url) {
        if (url == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();

        try {
            /* Le code ci-dessous est peut-être optionel mais peut être utile
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            System.setProperty("http.maxRedirects", "100");
            HttpURLConnection.setFollowRedirects(true);*/
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            /* Authentification (pas pour v0)
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password.toCharArray());
                }
            });*/
            //con.setInstanceFollowRedirects(false);
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                builder.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            Log.e(TAG,"Problème d'accès à l'URL " + url.getPath());
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
