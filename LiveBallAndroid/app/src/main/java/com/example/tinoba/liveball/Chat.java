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

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Chat extends AppCompatActivity {
    String text;
    public static final String ENDPOINT = "http://tinoba.hostzi.com/";
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


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                                if (lista.getItemAtPosition(i).equals("Lijevo od tebe")) {
                                                    RestAdapter adapter = new RestAdapter.Builder()
                                                            .setEndpoint(ENDPOINT)
                                                            .build();
                                                    SlanjePoruka api =  adapter.create(SlanjePoruka.class);
                                                    api.getFeed("Lijevo od tebe", new Callback<String>() {
                                                        @Override
                                                        public void success(String s, Response response) {
                                                            Log.i("TAG",s);
                                                        }

                                                        @Override
                                                        public void failure(RetrofitError retrofitError) {
                                                            Log.i("TAG","neradi");
                                                        }
                                                    });

                                                }
                                                else if(lista.getItemAtPosition(i).equals("Desno od tebe")){
                                                    RestAdapter adapter = new RestAdapter.Builder()
                                                            .setEndpoint(ENDPOINT)
                                                            .build();
                                                    SlanjePoruka api =  adapter.create(SlanjePoruka.class);
                                                    api.getFeed("Desno od tebe", new Callback<String>() {
                                                        @Override
                                                        public void success(String s, Response response) {
                                                            Log.i("TAG",s);
                                                        }

                                                        @Override
                                                        public void failure(RetrofitError retrofitError) {
                                                            Log.i("TAG","neradi");
                                                        }
                                                    });
                                                }
                                                else{
                                                    Log.i("TAG","nista");
                                                }
                                            }
                                        }
        );



    }
    public void prvi(View view){



    }
    public void drugi(View view){

    }
}
