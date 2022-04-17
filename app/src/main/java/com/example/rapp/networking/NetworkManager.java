package com.example.rapp.networking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rapp.entities.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class NetworkManager {
    //private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static final String BASE_URL = "https://rapplication.herokuapp.com/";

    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    public static synchronized NetworkManager getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context) {
        mContext = context;
        mQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mQueue;
    }

    public void getRecipes(final iNetworkCallback<List<Recipe>> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "REST/publishedRecipes", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Recipe>>(){}.getType();
                List<Recipe> recipelist = gson.fromJson(response, listType);
                callback.onSuccess(recipelist);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
        mQueue.add(request);
    }
}
