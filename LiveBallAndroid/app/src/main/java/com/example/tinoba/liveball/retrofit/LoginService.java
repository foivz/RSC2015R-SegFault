package com.example.tinoba.liveball.retrofit;

import com.example.tinoba.liveball.models.UserLoginRequest;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by antonio on 21/11/15.
 */
public interface LoginService {
    @POST("/user/index/createapp")
    Call<JSONObject> basicLogin(@Body UserLoginRequest object);

    @FormUrlEncoded
    @POST("/game/index/messages")
    Call<String> sendMessage(@Field("text") String text);
}