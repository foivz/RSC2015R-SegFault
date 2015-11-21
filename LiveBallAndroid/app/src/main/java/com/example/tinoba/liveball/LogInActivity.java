package com.example.tinoba.liveball;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tinoba.liveball.models.UserLoginRequest;
import com.example.tinoba.liveball.models.UserModel;
import com.example.tinoba.liveball.retrofit.LoginService;
import com.example.tinoba.liveball.retrofit.ServiceGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LogInActivity extends AppCompatActivity{
    EditText userNameEditText;
    EditText userPassEditText;
    Button logInButton;
    Button registerButton;

    //View coordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //coordinator = (View) findViewById(R.id.login_coordinator);

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
                Log.i("BAAAAA", "KURAAAC");
                LoginService loginService =
                        ServiceGenerator.createService(LoginService.class);

                UserLoginRequest user = new UserLoginRequest(userNameEditText.getText().toString(), userPassEditText.getText().toString());

                Call<JSONObject> call = loginService.basicLogin(user);
                call.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Response<JSONObject> response, Retrofit retrofit) {
                        Log.i("LOG IN", response.message());
                        loginSuccess();
                        if (response.body() != null) {
                            Log.i("LOG IN", response.body().toString());
                        } else Log.i("LOG IN", "no body");
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("LOG IN failure", t.getMessage());
                        loginFailedMiserably();
                    }
                });
            }
        });

    }

    private void loginFailedMiserably() {
        //Snackbar.make(coordinator, "Server error", Snackbar.LENGTH_SHORT).show();
    }

    private void loginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
