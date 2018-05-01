package com.lugdunum.heptartuflette.lugdunum.Utils;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class JsonUtils extends AsyncTask<RequestUtils, Integer, JSONArray> {

    public static String host = "51.254.118.174";
    public static String protocol = "http";
    public static int port = 8080;

    protected JSONArray doInBackground(RequestUtils... requests) {
        HttpURLConnection urlConnection = null;
        for (RequestUtils request:requests) {
            try {
                urlConnection = (HttpURLConnection) request.getUrl().openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept","application/json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(request.hasParams()){
                    //Sending data
                    urlConnection.setRequestMethod("POST");
                    OutputStream out = null;
                    try {
                        out = new BufferedOutputStream(urlConnection.getOutputStream());
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                        writer.write(request.getParams().toString());
                        writer.flush();
                        writer.close();
                        out.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    //Getting data
                    urlConnection.setRequestMethod("GET");
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
