package com.example.tinoba.liveball.retrofit;

import com.example.tinoba.liveball.Igrac;
import com.example.tinoba.liveball.models.UserLoginRequest;
import org.json.JSONObject;

import java.util.ArrayList;

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
    @FormUrlEncoded
    @POST("/user/index/createapp")
    Call<String> basicLogin(@Field("user") String user, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("/game/index/messages")
    Call<String> sendMessage(@Field("text") String text, @Field("id") String id, @Field("game") String game);

    @FormUrlEncoded
    @POST("/game/index/location")
    Call<ArrayList<Igrac>> pozicije(@Field("lat") String lat,@Field("lng") String lng,@Field("id") String id);

    @FormUrlEncoded
    @POST("/game/index/messagesget")
    Call<ArrayList<String>> loop (@Field("game") String game, @Field("id") String id);

    @FormUrlEncoded
    @POST("/game/index/start")
    Call<String> pocetak(@Field("id") String id);

    @FormUrlEncoded
    @POST("/game/index/score")
    Call<String> flagCaptured(@Field("game") String game, @Field("id") String id);


}