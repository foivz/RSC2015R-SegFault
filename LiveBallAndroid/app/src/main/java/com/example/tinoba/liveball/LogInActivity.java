package com.example.tinoba.liveball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tinoba.liveball.retrofit.LoginService;
import com.example.tinoba.liveball.retrofit.ServiceGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by antonio on 21/11/15.
 */
public class LogInActivity extends AppCompatActivity{
    EditText userNameEditText;
    EditText userPassEditText;
    Button logInButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        SharedSingleton shared = SharedSingleton.getInstance(getApplicationContext());

        if (shared.getUserLoggedPrefs()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        userNameEditText = (EditText) findViewById(R.id.user_name_edit_text);
        userPassEditText = (EditText) findViewById(R.id.password_edit_text);
        logInButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginService loginService =
                        ServiceGenerator.createService(LoginService.class, userNameEditText.getText().toString(), userPassEditText.getText().toString());
                Call<JSONArray> call = loginService.basicLogin();
                call.enqueue(new Callback<JSONArray>() {
                    @Override
                    public void onResponse(Response<JSONArray> response, Retrofit retrofit) {
                        Log.i("LOG IN", response.message());
                        if (response.body() != null) try {
                            Log.i("LOG IN", response.body().getJSONArray(0).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        else Log.i("LOG IN", "no body");
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("LOG IN failure", t.getMessage());
                    }
                });
            }
        });

    }



}
