package com.example.rapp.networking;

public interface iNetworkCallback<T> {

    void onSuccess(T result);

    void onFailure(String errorString);
}
