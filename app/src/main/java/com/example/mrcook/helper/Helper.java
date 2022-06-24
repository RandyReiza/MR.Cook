package com.example.mrcook.helper;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.mrcook.session.SessionManagerUtil;

public class Helper {
    // put your helper method here

    public static void showView(View view, Boolean state) {
        if (state) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static boolean isSessionExpired(Context context) {
        SessionManagerUtil sessionManagerUtil = new SessionManagerUtil(context);
        return (System.currentTimeMillis() > sessionManagerUtil.getSessionExpiredTime()) &&
                (sessionManagerUtil.getSessionExpiredTime() != 0);
    }
}
