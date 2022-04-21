package com.example.rapp.networking;

import android.content.Context;
import android.util.Log;

import androidx.activity.result.contract.ActivityResultContracts;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rapp.R;
import com.example.rapp.entities.Page;
import com.example.rapp.entities.Recipe;
import com.example.rapp.entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.FormatFlagsConversionMismatchException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkManager {
    private static final String BASE_URL = "http://10.0.2.2:8080/";
    //private static final String BASE_URL = "https://rapplication.herokuapp.com/";

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

    public void login(String username, String password, final iNetworkCallback<String> callback) {
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("username", username);
        postParam.put("password", password);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_URL + "REST/login", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String test = response.getString("response");
                    callback.onSuccess(test);
                } catch (JSONException e) {
                    Log.e(TAG, "Failed to convert JSON: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        mQueue.add(request);
    }

    public void signup(String email, String username, String password, final iNetworkCallback<String> callback) {
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("email", email);
        postParam.put("userName", username);
        postParam.put("password", password);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_URL + "REST/signup", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess("approved");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        mQueue.add(request);
    }

    public void createPage(String title, String description, String username, final iNetworkCallback<Page> callback) {
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("title", title);
        postParam.put("description", description);
        postParam.put("userName", username);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_URL + "REST/createPage", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String test = response.toString();
                        Page page = gson.fromJson(response.toString(), Page.class);
                        callback.onSuccess(page);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        mQueue.add(request);
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
        Gson gson = new Gson();
        String s = gson.toJson(recipe);
        Log.d(TAG, s);
        try {
            body.put("recipe", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "body: " + body.toString());
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
