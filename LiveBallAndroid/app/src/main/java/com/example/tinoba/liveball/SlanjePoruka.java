package com.example.tinoba.liveball;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by tinoba on 21.11.2015..
 */
public interface SlanjePoruka {
    @FormUrlEncoded
    @POST("/proba.php")
    public void getFeed( @Field("text") String text,Callback<String> response);
}
