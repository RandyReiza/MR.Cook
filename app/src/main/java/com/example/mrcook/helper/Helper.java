package com.example.mrcook.helper;

import android.view.View;

public class Helper {
    // put your helper method here

    public static void showView(View view, Boolean state) {
        if (state) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
