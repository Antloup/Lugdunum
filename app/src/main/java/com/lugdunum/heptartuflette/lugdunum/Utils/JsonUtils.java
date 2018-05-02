package com.lugdunum.heptartuflette.lugdunum.Utils;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
//                    urlConnection.setRequestMethod("POST");
//                    urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//                    urlConnection.setRequestProperty("Accept","application/json");
//                    urlConnection.setDoOutput(true);
//                    urlConnection.setDoInput(true);

                    try {
                        // 1. create HttpClient
                        HttpClient httpclient = new DefaultHttpClient();

                        // 2. make POST request to the given URL
                        HttpPost httpPost = new HttpPost(request.getUrl().toString());

                        // 3. set json to StringEntity
                        StringEntity se = new StringEntity(request.getParams().toString());
                        // 4. set httpPost Entity
                        httpPost.setEntity(se);

                        // 5. Set some headers to inform server about the type of the content
                        httpPost.setHeader("Accept", "application/json");
                        httpPost.setHeader("Content-type", "application/json");
                        // 6. Execute POST request to the given URL
                        HttpResponse httpResponse = httpclient.execute(httpPost);

//                        // 7. receive response as inputStream
//                        inputStream = httpResponse.getEntity().getContent();
//                        // 8. convert inputstream to string
//                        if(inputStream != null)
//                            result = convertInputStreamToString(inputStream);
//                        else
//                            result = "Did not work!";

                    } catch (Exception e) {
                        Log.d("InputStream", e.getLocalizedMessage());
                    }

//                    try {
//                        DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
//                        //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
//                        os.writeBytes(request.getParams().toString());
//
//                        os.flush();
//                        os.close();
////                        out = new BufferedOutputStream(urlConnection.getOutputStream());
////                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
////                        String params = request.getParams().toString();
//                        Log.e("PARAM",request.getParams().toString());
////                        writer.write(request.getParams().toString());
////                        writer.flush();
////                        writer.close();
////                        out.flush();
////                        out.close();
//                    }catch (Exception e) {
//                        e.printStackTrace();
//                    }

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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
