package com.gratanet.mailanovadilbek.razybol.helper;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class HttpRequest {
    private RequestQueue requestQueue;

    public HttpRequest(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void send(int method, String url, final Map<String, String> params, Response.Listener<String> onResponse, Response.ErrorListener onError) {
        StringRequest stringRequest = new StringRequest(method, url, onResponse, onError) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}