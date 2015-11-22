package com.example.tinoba.liveball;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinoba.liveball.retrofit.LoginService;
import com.example.tinoba.liveball.retrofit.ServiceGenerator;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tinoba on 21.11.2015..
 */
public class CustomAdapterGumbi extends ArrayAdapter<String>{
    private final ArrayList<String> text1;
    private final ArrayList<String> text2;
    Context context;

    public CustomAdapterGumbi(Context context, ArrayList<String> text1, ArrayList<String> text2){
        super(context,R.layout.gumbi,text1);
        this.context = context;
        this.text1 = text1;
        this.text2 = text2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.gumbi, parent, false);
        final Button button1 = (Button)customView.findViewById(R.id.gumb1);
        Button button2 = (Button)customView.findViewById(R.id.gumb2);
        button1.setText(text1.get(position).toString());
        button2.setText(text2.get(position).toString());
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginService loginService =
                        ServiceGenerator.createService(LoginService.class);

                Call<String> call = loginService.sendMessage(text1.get(position).toString(),"6", "1");
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        Log.i("TAG", "BLA");
                        if (response.body() != null) {
                            Log.i("TAG", response.body().toString());
                        } else Log.i("TAG", "no body");
                        Toast.makeText(context,"radi",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("TAG", t.getMessage());
                        Toast.makeText(context, "ne radi", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginService loginService =
                        ServiceGenerator.createService(LoginService.class);

                Call<String> call = loginService.sendMessage(text2.get(position).toString(),"6","1");
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
        });



        return customView;
    }
}
