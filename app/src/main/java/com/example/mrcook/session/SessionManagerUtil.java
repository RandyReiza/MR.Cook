package com.example.mrcook.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagerUtil {
    public static final String SESSION_PREFERENCE = "com.example.mrcook.session.SessionManagerUtils.SESSION_PREFERENCE";
//    public static final String SESSION_TOKEN = "com.example.mrcook.session.SessionManagerUtils.SESSION_TOKEN";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    //set constructor
    public SessionManagerUtil(Context context){
//        mSharedPreferences = context.getSharedPreferences("AppKey", 0);
        mSharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE,Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();
    }

    public void setLogin(boolean login){
        mEditor.putBoolean("KEY_LOGIN", login);
        mEditor.commit();
    }

    public boolean getlogin(){
        return mSharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    public void setUsername(String username){
        mEditor.putString("KEY_USERNAME", username);
        mEditor.commit();
    }

    public String getUsername(){
        return mSharedPreferences.getString("KEY_USERNAME", "");
    }

    public void setToken(String token){
        mEditor.putString("KEY_TOKEN", token);
        mEditor.commit();
    }

    public String getToken(){
        return mSharedPreferences.getString("KEY_TOKEN","" );
    }

//    public void storeUserToken(Context context, String token){
//    mSharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
//    mEditor = mSharedPreferences.edit();
//    mEditor.putString(SESSION_TOKEN, token);
//    mEditor.apply();
//    }


    public void logout(){
        mEditor = mSharedPreferences.edit();
        mEditor.clear().commit();
    }


}