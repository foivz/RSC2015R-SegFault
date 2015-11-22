package com.example.tinoba.liveball.fragments;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        userNameTextView = (TextView) rootView.findViewById(R.id.user_name_main_fragment);
        userRoleTextView = (TextView) rootView.findViewById(R.id.user_role_main_fragment);
        userStatusTextView = (TextView) rootView.findViewById(R.id.user_status_main_fragment);
        nfcTextView = (TextView) rootView.findViewById(R.id.nfc_text_main_fragment);

        SharedSingleton prefs = SharedSingleton.getInstance(getContext());
        userId = prefs.getUserLoggedPrefs();
        DBHelper dbHelper = new DBHelper(getContext());
        UserModel user = dbHelper.getUser(userId);

        userNameTextView.setText(user.getUsername());

        String role;
        if(user.getIsAdmin() == 0) role = "Igrač";
        else role = "Sudac";

        userRoleTextView.setText(role);

        if(userStatus) userStatusTextView.setText("Nije pogođen");
        else userStatusTextView.setText("Pogođen");

        return rootView;
    }

    public void setNfcTextView(String text){
        nfcTextView.setText(text);
    }

}

