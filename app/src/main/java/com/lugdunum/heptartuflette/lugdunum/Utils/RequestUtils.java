package com.lugdunum.heptartuflette.lugdunum.Utils;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class RequestUtils {
    private JSONObject params = null;
    private URL url = null;

    public RequestUtils(String request,JSONObject params) {
        this.params = params;
        try {
            this.url = new URL(JsonUtils.protocol, JsonUtils.host, JsonUtils.port, request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public RequestUtils(String request) {
        try {
            this.url = new URL(JsonUtils.protocol, JsonUtils.host, JsonUtils.port, request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getParams() {
        return params;
    }

    public URL getUrl() {
        return url;
    }

    public boolean hasParams(){
        return params != null;
    }
}
