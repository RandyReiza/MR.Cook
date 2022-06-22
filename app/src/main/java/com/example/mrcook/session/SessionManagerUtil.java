package com.example.mrcook.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagerUtil {

    private String PREF_NAME = "SIMPAN";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SessionManagerUtil(Context context){
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void logout(){
        mEditor = mSharedPreferences.edit();
        mEditor.clear().commit();
    }
}