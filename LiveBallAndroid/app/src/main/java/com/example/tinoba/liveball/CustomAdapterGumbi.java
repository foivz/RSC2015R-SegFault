package com.example.tinoba.liveball;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.gumbi, parent, false);
        Button button1 = (Button)customView.findViewById(R.id.gumb1);
        Button button2 = (Button)customView.findViewById(R.id.gumb2);
        button1.setText(text1.get(position).toString());
        button2.setText(text2.get(position).toString());



        return customView;
    }
}
