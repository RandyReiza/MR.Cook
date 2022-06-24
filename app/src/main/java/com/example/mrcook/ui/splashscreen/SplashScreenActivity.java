package com.example.mrcook.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mrcook.R;
import com.example.mrcook.helper.Helper;
import com.example.mrcook.session.SessionManagerUtil;
import com.example.mrcook.ui.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkSession();

                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }

    private void checkSession() {
        if (Helper.isSessionExpired(getApplicationContext())) {
            SessionManagerUtil sessionManagerUtil = new SessionManagerUtil(getApplicationContext());
            sessionManagerUtil.setLogin(false);
            sessionManagerUtil.setUsername(null);
            sessionManagerUtil.setFullName(null);
            sessionManagerUtil.setEmail(null);
            sessionManagerUtil.setToken(null);
        }
    }
}