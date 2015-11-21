package com.example.tinoba.liveball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tinoba.liveball.models.UserLoginRequest;
import com.example.tinoba.liveball.retrofit.LoginService;
import com.example.tinoba.liveball.retrofit.ServiceGenerator;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Chat extends AppCompatActivity {
    String text;
    String[] poruke ={"Napadni", "Cover fire","Lijevo od tebe", "Pomoć","Ispred tebe", "Iza tebe", "Desno od tebe","Povlačenje"};
    ArrayList<String> text1;
    ArrayList<String> text2;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        text1 = new ArrayList<>();
        text2 = new ArrayList<>();


        for(int i = 0 ; i< 4 ; i++){
            text1.add(poruke[i]);
            text2.add(poruke[i+4]);
        }
        //Toast.makeText(Chat.this,text1.get(0),Toast.LENGTH_LONG).show();
        lista = (ListView)findViewById(R.id.lista);
        ListAdapter customAdapter = new CustomAdapterGumbi(Chat.this, text1, text2);
        lista.setAdapter(customAdapter);
        Button gumb1 = (Button)findViewById(R.id.gumb1);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {

            }
        });





    }
    public void prvi(View view){
        Log.i("TAG","BEZVEZ");
        LoginService loginService =
                ServiceGenerator.createService(LoginService.class);

        Call<String> call = loginService.sendMessage("Lijevo od tebe");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                Log.i("TAG", response.message());
                if (response.body() != null) {
                    Log.i("TAG", response.body().toString());
                } else Log.i("TAG", "no body");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("TAG", t.getMessage());
            }
        });


    }
    public void drugi(View view){
        LoginService loginService =
                ServiceGenerator.createService(LoginService.class);

        Call<String> call = loginService.sendMessage("Desno od tebe");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                Log.i("LOG IN", response.message());
                if (response.body() != null) {
                    Log.i("LOG IN", response.body().toString());
                } else Log.i("LOG IN", "no body");
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("LOG IN failure", t.getMessage());
            }
        });
    }
}
