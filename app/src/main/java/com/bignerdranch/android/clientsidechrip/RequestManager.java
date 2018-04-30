package com.bignerdranch.android.clientsidechrip;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Arrays;

/**
 * Created by shaffer on 3/26/18.
 */

public class RequestManager {

    private static RequestManager soleInstance;

    public static RequestManager get() {
        if (soleInstance == null) soleInstance = new RequestManager();
        return soleInstance;
    }

    //private static final String BASE_URL = "http://chirp-env-1.rgkwwzpdqw.us-east-2.elasticbeanstalk.com";
    private static final String BASE_URL = "http://192.168.1.8:5000";
    private RequestQueue requestQueue;

    public void sendListChirpsRequest(String e,Context c, final ListUsersResponseHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users/watchlist"+"/"+e;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP", "Response is: "+ response);
                        Gson gson = new Gson();
                        Chirp[] chirps = gson.fromJson(response, Chirp[].class);
                        handler.handleResponse(Arrays.asList(chirps));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP","That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void sendUserVerificationRequest(String e, String p, Context c, final verifyResponseHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users/verifyUser/"+e+"/"+p;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP", "Response is: "+ response);
                        Gson gson = new Gson();
                        boolean isValid = gson.fromJson(response, Boolean.class);
                        handler.handleResponse(isValid);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP","That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void sendUserRegistrationRequest(String e, String u, String p, Context c, final RegistrationResponseHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users/"+e+"/"+u+"/"+p;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP", "Response is: "+ response);
                        Gson gson = new Gson();
                        boolean isRegistrationValid = gson.fromJson(response, Boolean.class);
                        handler.handleResponse(isRegistrationValid);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP","That didn't work!");
            }
        });

        queue.add(stringRequest);
    }



    @NonNull
    private RequestQueue getRequestQueue(Context c) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(c);
        return requestQueue;
    }
}
