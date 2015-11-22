package com.example.tinoba.liveball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tinoba.liveball.retrofit.LoginService;
import com.example.tinoba.liveball.retrofit.ServiceGenerator;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Pocetak_igre extends AppCompatActivity {
    Thread thread;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetak_igre);
        someThread();
    }

    private void someThread(){
        thread = new Thread(){
            @Override
            public void run() {
                synchronized(this){
                    LoginService loginService =
                            ServiceGenerator.createService(LoginService.class);
                    flag = true;
                    while (flag){
                        Call<String> call = loginService.pocetak("6");
                        call.enqueue(new Callback<String>(){
                            @Override
                            public void onResponse(Response<String> response, Retrofit retrofit) {
                                if (!response.body().toString().equals("NOT_LIVE")) {
                                    Log.i("TAG", response.body().toString());
                                   // thread.notifyAll();
                                    flag = false;
                                    startMainActivity();


                                } else Log.i("TAG", "no body");
                                Toast.makeText(Pocetak_igre.this, response.body(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.e("TAG", t.getMessage());
                                Toast.makeText(Pocetak_igre.this, "ne radi", Toast.LENGTH_LONG).show();
                            }
                        });
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        };
        thread.start();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
       // thread.interrupt();
//        synchronized(this){
//        }
        finish();
    }
}
