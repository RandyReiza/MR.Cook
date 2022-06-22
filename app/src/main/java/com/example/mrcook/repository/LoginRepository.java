package com.example.mrcook.repository;

import android.app.Application;

import com.example.mrcook.BuildConfig;
import com.example.mrcook.restapi.foodrecipe.ApiConfig;
import com.example.mrcook.restapi.user.responses.ResponseUser;

import retrofit2.Call;

public class LoginRepository {

    private Application application;
    static final String BASE_URL = BuildConfig.BASE_URL;


    public LoginRepository(Application application) {
        this.application = application;
    }

    public Call<ResponseUser> userLogin(String username, String password){
        return new ApiConfig(BASE_URL).getApiService().userLogin(username, password);
    }
}
