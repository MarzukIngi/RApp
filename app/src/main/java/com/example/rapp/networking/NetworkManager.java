package com.example.rapp.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rapp.entities.Page;
import com.example.rapp.entities.Recipe;
import com.example.rapp.entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasi sem sér um öll samskipti milli framenda og bakenda.
 */
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

    /**
     * Fall til að skrá inn notanda.
     * @param username Notendanafn
     * @param password Lykilorð notanda
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
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

    /**
     * Fall til að búa til nýjan notanda.
     * @param email Netfang nýs notanda
     * @param username Notendanafn nýs notanda.
     * @param password Lykilorð nýs notanda
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
    public void signup(String email, String username, String password, final iNetworkCallback<User> callback) {
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("email", email);
        postParam.put("userName", username);
        postParam.put("password", password);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_URL + "REST/signup", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        User user = gson.fromJson(response.toString(), User.class);
                        callback.onSuccess(user);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
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

    /**
     * Fall til að búa til nýja síðu.
     * @param title Titill síðu
     * @param description Lýsing síðu
     * @param username Notendanafn þess sem býr til síðuna
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
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

    /**
     * Fall sem sækir ákveðið margar síður af bakenda.
     * @param limit Takmarkið fyrir hve margar síður eru sóttar
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
    public void getPages(long limit, final iNetworkCallback<List<Page>> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "REST/pages/"+limit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Page>>() {}.getType();
                List<Page> pageList = gson.fromJson(response, listType);
                callback.onSuccess(pageList);
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

    /**
     * Fall sem sækir ákveðna síðu af bakenda.
     * @param id Auðkennisnúmer síðunnar
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
    public void getPage(long id, final iNetworkCallback<Page> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "REST/page/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Page page = gson.fromJson(response, Page.class);
                callback.onSuccess(page);
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

    /**
     * Fall sen athugar hver eigandi ákveðinnar síðu er.
     * @param id Auðkennisnúmer síðunnar
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
    public void getOwner(long id, final iNetworkCallback<User> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "REST/page/" + id + "/owner", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                User user = gson.fromJson(response, User.class);
                callback.onSuccess(user);
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


    /**
     * Fall sem sækir allar published uppskriftir.
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
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

    /**
     * Fall sem sækir vinsælustu uppskriftirnar.
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
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

    /**
     * Sækir ákveðna uppskrift
     * @param recipeId Auðkennisnúmer uppskriftarinnar
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
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

    /**
     * Býr til nýja uppskrift.
     * @param recipe Uppskriftin
     * @param pageid Auðkennisnúmer síðunnar sem uppskrifitin tengist
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     * @throws JSONException ef ekki er hægt að gera JSON hlut úr uppskriftinni
     */
    public void createRecipe(Recipe recipe, int pageid, final iNetworkCallback<Recipe> callback) throws JSONException {
        Gson gson = new Gson();
        String s = gson.toJson(recipe);
        JSONObject json = new JSONObject(s);
        json.put("page_id", pageid);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_URL + "REST/createRecipe", json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String test = response.toString();
                        Recipe recipe = gson.fromJson(response.toString(), Recipe.class);
                        callback.onSuccess(recipe);
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

    /**
     * Breytir ákveðinni uppskrift.
     * @param recipeId Auðkennisnúmer uppskriftarinnar
     * @param recipe Uppskriftin sem á að breyta í
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     * @throws JSONException Ef ekki er hægt að breyta nýju uppskriftinni í JSON hlut
     */
    public void changeRecipeById(long recipeId, Recipe recipe, final iNetworkCallback<Recipe> callback) throws JSONException {
        Gson gson = new Gson();
        String s = gson.toJson(recipe);
        JSONObject json = new JSONObject(s);
        Log.d(TAG, s);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_URL + "REST/editRecipe/" + recipeId, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        mQueue.add(request);
    }

    /**
     * Sækir síður fyrir ákveðinn notanda
     * @param username Notendanafn notanda
     * @param callback Callback sem kallað er á þegar niðurstöður fást
     */
    public void getPagesByUsername(String username, final iNetworkCallback<List<Page>> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "REST/userPages/" + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Page>>(){}.getType();
                        List<Page> pagelist = gson.fromJson(response, listType);
                        callback.onSuccess(pagelist);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error is: " + error.toString());
            }
        }
        );
        mQueue.add(request);
    }
}
