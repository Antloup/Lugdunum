package com.lugdunum.heptartuflette.lugdunum.Utils;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class JsonUtils extends AsyncTask<URL, Integer, JSONArray> {

    public static String host = "51.254.118.174";
    public static String protocol = "http";
    public static int port = 8080;

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
