package com.example.tinoba.liveball.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tinoba.liveball.R;

public class MainFragment extends Fragment {
    TextView userNameTextView;
    TextView userStatusTextView;
    TextView userRoleTextView;
    TextView nfcTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        userNameTextView = (TextView) container.findViewById(R.id.user_name_main_fragment);
        userRoleTextView = (TextView) container.findViewById(R.id.user_role_main_fragment);
        userStatusTextView = (TextView) container.findViewById(R.id.user_status_main_fragment);
        nfcTextView = (TextView) container.findViewById(R.id.nfc_text_main_fragment);

        return rootView;
    }

    public void setNfcTextView(String text){
        nfcTextView.setText(text);
    }

}

