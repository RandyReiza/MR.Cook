package com.example.mrcook.restapi.foodrecipe;


import com.example.mrcook.BuildConfig;
import com.example.mrcook.restapi.foodrecipe.responses.detail.ResponseFoodDetail;
import com.example.mrcook.restapi.foodrecipe.responses.getall.ResponseFoodRecipe;
import com.example.mrcook.restapi.foodrecipe.responses.search.ResponseFoodSearch;
import com.example.mrcook.restapi.user.responses.ResponseUser;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/recipes")
    Call<ResponseFoodRecipe> getAllFood();

    @GET("/api/recipe/{key}")
    Call<ResponseFoodDetail> getFoodDetail(@Path("key") String key);

    @GET("/api/search")
    Call<ResponseFoodSearch> searchFood(@Query("q") String food);

    @Headers("x-api-key:"+ BuildConfig.API_AUTH)
//    @FormUrlEncoded
    @POST("/api/user/login")
    Call<ResponseUser> userLogin();

}
