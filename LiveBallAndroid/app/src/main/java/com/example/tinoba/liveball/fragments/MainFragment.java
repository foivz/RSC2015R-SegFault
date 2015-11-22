package com.example.tinoba.liveball.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tinoba.liveball.MainActivity;
import com.example.tinoba.liveball.Mapa;
import com.example.tinoba.liveball.R;
import com.example.tinoba.liveball.SharedSingleton;
import com.example.tinoba.liveball.db.DBHelper;
import com.example.tinoba.liveball.models.UserLoginRequest;
import com.example.tinoba.liveball.models.UserModel;
import com.example.tinoba.liveball.retrofit.LoginService;
import com.example.tinoba.liveball.retrofit.ServiceGenerator;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainFragment extends Fragment {
    TextView userNameTextView;
    TextView userStatusTextView;
    TextView userRoleTextView;
    TextView nfcTextView;
    Button deadButton;
    private int userId;
    private boolean userStatus = true;
    UserModel user;
    View prvi;
    View drugi;
    View treci;
    View cetvrti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        userNameTextView = (TextView) rootView.findViewById(R.id.user_name_main_fragment);
        userRoleTextView = (TextView) rootView.findViewById(R.id.user_role_main_fragment);
        userStatusTextView = (TextView) rootView.findViewById(R.id.user_status_main_fragment);
        deadButton = (Button) rootView.findViewById(R.id.dead_button);

        prvi = (View) rootView.findViewById(R.id.prvi);
        drugi = (View) rootView.findViewById(R.id.drugi);
        treci = (View) rootView.findViewById(R.id.treci);
        cetvrti = (View) rootView.findViewById(R.id.cetvrti);


        SharedSingleton prefs = SharedSingleton.getInstance(getContext());
        userId = prefs.getUserLoggedPrefs();
        DBHelper dbHelper = new DBHelper(getContext());
        user = dbHelper.getUser(userId);

        userNameTextView.setText(user.getUsername());

        String role;
        if (user.getIsAdmin() == 0) role = "Igrač";
        else role = "Sudac";

        userRoleTextView.setText(role);

        if (userStatus) userStatusTextView.setText("Nije pogođen");
        else userStatusTextView.setText("Pogođen");

        deadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Mapa.class);
                getContext().startActivity(intent);
            }
    });


        return rootView;
    }

    public void setNfcTextView(int view) {
        if (view == 0) prvi.setBackgroundColor(Color.parseColor("#2222CC"));
        if (view == 1) drugi.setBackgroundColor(Color.parseColor("#2222CC"));
        if (view == 2) treci.setBackgroundColor(Color.parseColor("#2222CC"));
        if (view == 3) cetvrti.setBackgroundColor(Color.parseColor("#2222CC"));

        LoginService loginService =
                ServiceGenerator.createService(LoginService.class);

        Call<String> call = loginService.flagCaptured(MainActivity.game, MainActivity.id_);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {

            }

        });

    }
}
