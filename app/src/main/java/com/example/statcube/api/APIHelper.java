package com.example.statcube.api;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class APIHelper {
    public static final String BASE_URL = "https://dry-wave-41818.herokuapp.com/api/";

    private static StringRequest createRequest(
            int method,
            String url,
            @Nullable Map<String, String> jsonBody,
            Response.Listener<String> listener,
            @Nullable Response.ErrorListener errorListener
    ) {
        return new StringRequest(method, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("x-api-key", "STATCUBE");
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return jsonBody;
            }
        };
    }

    public static StringRequest createGetRequest(
            String url,
            Response.Listener<String> listener,
            @Nullable Response.ErrorListener errorListener
    ) {
        return APIHelper.createRequest(Request.Method.GET, url, null, listener, errorListener);
    }

    public static StringRequest createPostRequest(
            String url,
            Map<String, String> jsonBody,
            Response.Listener<String> listener,
            @Nullable Response.ErrorListener errorListener
    ) {
        return APIHelper.createRequest(Request.Method.POST, url, jsonBody, listener, errorListener);
    }
}
