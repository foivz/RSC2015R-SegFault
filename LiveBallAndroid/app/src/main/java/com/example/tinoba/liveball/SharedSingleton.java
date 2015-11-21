package com.example.tinoba.liveball;

/**
 * Created by antonio on 21/11/15.
 */
import android.content.Context;
import android.content.SharedPreferences;


public class SharedSingleton {
    private static final String PREF_FILE = "sharedPrefs";
    private static final String USER_NAME_PREFS = "username";
    private static final String USER_NEMAIL_PREFS = "useremail";
    private static final String USER_LOGGED_PREFS = "userlogged";

    private static SharedSingleton mInstance = null;

    private Context context;
    private SharedPreferences sharedPreferences;

    private SharedSingleton(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    }

    public static SharedSingleton getInstance(Context context){
        if(mInstance == null)
        {
            mInstance = new SharedSingleton(context);
        }
        return mInstance;
    }

    public void putUserNamePrefs(String username){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME_PREFS, username);
        editor.commit();
    }

    public void putUserEmailPrefs(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NEMAIL_PREFS, email);
        editor.commit();
    }

    public void putUserLoggedPrefs(boolean state){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(USER_LOGGED_PREFS, state);
        editor.commit();
    }

    public String getUserNamePrefs(){
        return sharedPreferences.getString(USER_NAME_PREFS, null);
    }

    public String getUserEmailPrefs(){
        return sharedPreferences.getString(USER_NEMAIL_PREFS, null);
    }

    public boolean getUserLoggedPrefs(){
        return sharedPreferences.getBoolean(USER_LOGGED_PREFS, false);
    }

}
