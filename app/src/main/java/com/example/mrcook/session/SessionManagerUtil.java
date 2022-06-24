package com.example.mrcook.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagerUtil {
    public static final String SESSION_PREFERENCE = "com.example.mrcook.session.SessionManagerUtils.SESSION_PREFERENCE";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    //set constructor
    public SessionManagerUtil(Context context){
        mSharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE,Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();
    }

    public void setLogin(boolean login){
        mEditor.putBoolean("KEY_LOGIN", login);
        mEditor.commit();
    }

    public boolean getLogin(){
        return mSharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    public void setToken(String token){
        mEditor.putString("KEY_TOKEN", token);
        mEditor.commit();
    }

    public String getToken(){
        return mSharedPreferences.getString("KEY_TOKEN","" );
    }

    public void setUsername(String username){
        mEditor.putString("KEY_USERNAME", username);
        mEditor.commit();
    }

    public String getUsername(){
        return mSharedPreferences.getString("KEY_USERNAME", "");
    }

    public void setFullName(String fullName){
        mEditor.putString("KEY_FULL_NAME", fullName);
        mEditor.commit();
    }

    public String getFullName(){
        return mSharedPreferences.getString("KEY_FULL_NAME","" );
    }

    public void setEmail(String email){
        mEditor.putString("KEY_EMAIL", email);
        mEditor.commit();
    }

    public String getEmail(){
        return mSharedPreferences.getString("KEY_EMAIL","" );
    }
}