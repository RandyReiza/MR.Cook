package com.example.mrcook.restapi.foodrecipe;


import com.example.mrcook.restapi.foodrecipe.responses.getall.ResponseFoodRecipe;
import com.example.mrcook.restapi.foodrecipe.responses.search.ResponseFoodSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/recipes")
    Call<ResponseFoodRecipe> getAllFood();

    @GET("/api/search")
    Call<ResponseFoodSearch> searchFood(@Query("q") String food);

}
