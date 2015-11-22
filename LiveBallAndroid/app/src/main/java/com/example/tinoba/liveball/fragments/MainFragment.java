package com.example.tinoba.liveball.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tinoba.liveball.R;
import com.example.tinoba.liveball.SharedSingleton;
import com.example.tinoba.liveball.db.DBHelper;
import com.example.tinoba.liveball.models.UserModel;

public class MainFragment extends Fragment {
    TextView userNameTextView;
    TextView userStatusTextView;
    TextView userRoleTextView;
    TextView nfcTextView;
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
        if(user.getIsAdmin() == 0) role = "Igrač";
        else role = "Sudac";

        userRoleTextView.setText(role);

        if(userStatus) userStatusTextView.setText("Nije pogođen");
        else userStatusTextView.setText("Pogođen");

        return rootView;
    }

    public void setNfcTextView(int view){
        if(view == 0) prvi.setBackgroundColor(Color.parseColor("#2222CC"));
        if(view == 1) drugi.setBackgroundColor(Color.parseColor("#2222CC"));
        if(view == 2) treci.setBackgroundColor(Color.parseColor("#2222CC"));
        if(view == 3) cetvrti.setBackgroundColor(Color.parseColor("#2222CC"));



    }

}

