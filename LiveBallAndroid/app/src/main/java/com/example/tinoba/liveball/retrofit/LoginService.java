package com.example.tinoba.liveball.retrofit;

import com.example.tinoba.liveball.models.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.http.POST;

/**
 * Created by antonio on 21/11/15.
 */
public interface LoginService {
    @POST("/user/index/createapp")
    Call<JSONArray> basicLogin();
}