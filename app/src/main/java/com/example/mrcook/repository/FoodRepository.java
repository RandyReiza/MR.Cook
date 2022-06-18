package com.example.mrcook.repository;

import android.app.Application;

import com.example.mrcook.BuildConfig;
import com.example.mrcook.restapi.foodrecipe.ApiConfig;
import com.example.mrcook.restapi.foodrecipe.responses.detail.ResponseFoodDetail;
import com.example.mrcook.restapi.foodrecipe.responses.getall.ResponseFoodRecipe;

import retrofit2.Call;

public class FoodRepository {

    private Application application;

    public FoodRepository(Application application) {
        this.application = application;
    }

    static final String FOOD_RECIPE_BASE_URL = BuildConfig.FOOD_RECIPE_BASE_URL;

    public Call<ResponseFoodRecipe> fetchAllFoodData() {
        return new ApiConfig(FOOD_RECIPE_BASE_URL).getApiService().getAllFood();
    }

    public Call<ResponseFoodDetail> fetchFoodDetail(String key) {
        return new ApiConfig(FOOD_RECIPE_BASE_URL).getApiService().getFoodDetail(key);
    }

}
