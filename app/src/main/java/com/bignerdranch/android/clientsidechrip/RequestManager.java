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
import com.android.volley.NetworkResponse;
import android.widget.Toast;

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
    //private static final String BASE_URL = "http://192.168.1.2:5000";
    private static final String BASE_URL = "http://chirp-env-1.rgkwwzpdqw.us-east-2.elasticbeanstalk.com";

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
                NetworkResponse n = error.networkResponse;
                int status = n.statusCode;
            }
        });

        queue.add(stringRequest);
    }

    public void sendUserVerificationRequest(String e, String p, Context c, final VerifyResponseHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users/verifyEmailAndPassword/"+e+"/"+p;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("HTTP", "Response is: "+ response);
                        Gson gson = new Gson();
                        boolean isValid = gson.fromJson(response, Boolean.class);
                        handler.handleResponse(isValid);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP","That didn't work!");

                NetworkResponse n = error.networkResponse;
                int status = n.statusCode;
                if(status==402||status==401)
                {
                    int messageResId = R.string.invalidEmailPassword;
                    Toast.makeText(c.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }
                if(status==500)
                {
                    int messageResId = R.string.try_again;
                    Toast.makeText(c.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }

            }
        });

        queue.add(stringRequest);
    }

    public void sendUserRegistrationRequest(String e, String u, String p, Context c, final RegistrationResponseHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users/createUser/"+e+"/"+u+"/"+p;
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
                NetworkResponse n = error.networkResponse;
                int status = n.statusCode;

                if(status==409)
                {
                    int messageResId = R.string.email_used;
                    Toast.makeText(c.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }

                if(status==411)
                {
                    int messageResId = R.string.username_used;
                    Toast.makeText(c.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }

            }
        });

        queue.add(stringRequest);
    }
    public void addChirpRequest(String e, String ch, Context c, final SendChirpsResponseHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/chirps/addChirp/"+e+"/"+ch;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP", "Response is: "+ response);
                        Gson gson = new Gson();
                        boolean added = gson.fromJson(response, Boolean.class);
                        handler.handleResponse(added);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP","That didn't work!");
                NetworkResponse n = error.networkResponse;
                int status = n.statusCode;
            }
        });

        queue.add(stringRequest);
    }

        public void removeUserWatchlistRequest(String e, String u, Context c, final RemoveUserWatchlistHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users/removeFromWatchlist/"+e+"/"+u;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP", "Response is: "+ response);
                        Gson gson = new Gson();
                        boolean added = gson.fromJson(response, Boolean.class);
                        handler.handleResponse(added);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP","That didn't work!");
                NetworkResponse n = error.networkResponse;
                int status = n.statusCode;

                if(status==402)
                {
                    int messageResId = R.string.user_not_exist;
                    Toast.makeText(c.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }
                if(status==500)
                {
                    int messageResId = R.string.try_again;
                    Toast.makeText(c.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }
            }
        });

        queue.add(stringRequest);
    }

    public void addUserWatchlistRequest(String e, String u, Context c, final AddUserWatchlistHandler handler) {
        RequestQueue queue = getRequestQueue(c);
        String url = BASE_URL+"/users/addToWatchlist/"+e+"/"+u;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HTTP", "Response is: "+ response);
                        Gson gson = new Gson();
                        boolean added = gson.fromJson(response, Boolean.class);
                        handler.handleResponse(added);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HTTP","That didn't work!");
                NetworkResponse n = error.networkResponse;
                int status = n.statusCode;

                if(status==402)
                {
                    int messageResId = R.string.user_not_exist;
                    Toast.makeText(c.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }
                if(status==412)
                {
                    int messageResId = R.string.on_watch_list;
                    Toast.makeText(c.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }
                if(status==500)
                {
                    int messageResId = R.string.try_again;
                    Toast.makeText(c.getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }
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
