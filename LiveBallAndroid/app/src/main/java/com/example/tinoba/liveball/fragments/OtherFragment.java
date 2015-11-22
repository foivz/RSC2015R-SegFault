package com.example.tinoba.liveball.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinoba.liveball.CustomAdapterGumbi;
import com.example.tinoba.liveball.MainActivity;
import com.example.tinoba.liveball.R;
import com.example.tinoba.liveball.retrofit.LoginService;
import com.example.tinoba.liveball.retrofit.ServiceGenerator;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by antonio on 21/11/15.
 */
public class OtherFragment extends Fragment {
    String text;
    String[] poruke = {"Napadni", "Cover fire", "Lijevo od tebe", "Pomoć", "Ispred tebe", "Iza tebe"};
    ArrayList<String> text1;
    ArrayList<String> text2;
    ListView lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_chat, container, false);
        text1 = new ArrayList<>();
        text2 = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
            text1.add(poruke[i]);
            text2.add(poruke[i + 3]);
        }
        //Toast.makeText(Chat.this,text1.get(0),Toast.LENGTH_LONG).show();
        lista = (ListView) rootView.findViewById(R.id.lista);
        ListAdapter customAdapter = new CustomAdapterGumbi(getContext(), text1, text2);
        lista.setAdapter(customAdapter);
        Button gumb1 = (Button) rootView.findViewById(R.id.gumb1);

        Thread thread = new Thread() {
            @Override
            public void run() {
                LoginService loginService =
                        ServiceGenerator.createService(LoginService.class);
                while (true) {
                    Call<ArrayList<String>> call = loginService.loop("1", /*MainActivity.id_*/"6");
                    call.enqueue(new Callback<ArrayList<String>>() {
                        @Override
                        public void onResponse(Response<ArrayList<String>> response, Retrofit retrofit) {
                            if (response.body() != null) {
                                Log.i("TAG", response.body().toString());
                                for (String s : response.body()) {
                                    Toast toast = Toast.makeText(getContext(), s, Toast.LENGTH_LONG);
                                    RelativeLayout toastLayout = (RelativeLayout) toast.getView();
                                    TextView toastTV = (TextView) toastLayout.getChildAt(0);
                                    toastTV.setTextSize(70);
                                    toast.show();
                                }
                            } else Log.i("TAG", response.message());

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("TAG", t.getMessage());
                            Toast.makeText(getContext(), "ne radi", Toast.LENGTH_LONG).show();
                        }
                    });
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        thread.start();

        return rootView;
    }
}
