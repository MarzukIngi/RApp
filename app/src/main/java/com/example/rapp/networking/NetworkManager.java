package com.example.rapp.networking;

import android.content.Context;
import android.util.Log;

import androidx.activity.result.contract.ActivityResultContracts;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rapp.R;
import com.example.rapp.entities.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class NetworkManager {
    //private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static final String BASE_URL = "https://rapplication.herokuapp.com/";

    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;
    private static final String TAG = "NetworkManager";

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

    public void getTrendingRecipes(final iNetworkCallback<List<Recipe>> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "REST/trending", new Response.Listener<String>() {
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

    public void getRecipeById(long recipeId, final iNetworkCallback<Recipe> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "REST/Recipe/" + recipeId,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Recipe recipe = gson.fromJson(response, Recipe.class);
                callback.onSuccess(recipe);
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

    public void changeRecipeById(long recipeId, Recipe recipe, final iNetworkCallback<Recipe> callback) {
        final JSONObject body = new JSONObject();
        try {
            body.put("id", recipe.getID());
            body.put("recipeTitle", recipe.getTitle());
            body.put("recipeDescription", recipe.getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, body.toString());
        StringRequest request = new StringRequest(
                Request.Method.POST, BASE_URL + "REST/editRecipe/" + recipeId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        ) {
          @Override
            public byte[] getBody() throws AuthFailureError {
              return body.toString().getBytes();
          }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        mQueue.add(request);
    }
}
