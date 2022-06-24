package com.example.mrcook.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mrcook.BuildConfig;
import com.example.mrcook.database.FoodDao;
import com.example.mrcook.database.FoodData;
import com.example.mrcook.database.MrCookRoomDatabase;
import com.example.mrcook.restapi.foodrecipe.ApiConfig;
import com.example.mrcook.restapi.foodrecipe.responses.detail.ResponseFoodDetail;
import com.example.mrcook.restapi.foodrecipe.responses.getall.ResponseFoodRecipe;
import com.example.mrcook.restapi.foodrecipe.responses.search.ResponseFoodSearch;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;

public class FoodRepository {

    static final String FOOD_RECIPE_BASE_URL = BuildConfig.FOOD_RECIPE_BASE_URL;

    private final FoodDao foodDao;
    private final ExecutorService executorService;

    public FoodRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        MrCookRoomDatabase db = MrCookRoomDatabase.getDatabase(application);
        foodDao = db.foodDao();
    }


    // LOCAL
    public void insert(final FoodData foodData) {
        executorService.execute(() -> foodDao.insert(foodData));
    }

    public void delete(final FoodData foodData) {
        executorService.execute(() -> foodDao.delete(foodData));
    }

    public void update(final FoodData foodData) {
        executorService.execute(() -> foodDao.update(foodData));
    }

    public List<FoodData> getAllFoods() {
        return foodDao.getAllFoods();
    }

    public List<FoodData> getAllFoodsByTitleLike(String title) {
        return foodDao.getAllFoodsByTitleLike(title);
    }

    public LiveData<FoodData> getOneByKey(String key) {
        return foodDao.getOneByKey(key);
    }



    // REMOTE
    public Call<ResponseFoodRecipe> fetchAllFoodData() {
        return new ApiConfig(FOOD_RECIPE_BASE_URL).getApiService().getAllFood();
    }

    public Call<ResponseFoodDetail> fetchFoodDetail(String key) {
        return new ApiConfig(FOOD_RECIPE_BASE_URL).getApiService().getFoodDetail(key);
    }

    public Call<ResponseFoodSearch> searchFood(String query) {
        return new ApiConfig(FOOD_RECIPE_BASE_URL).getApiService().searchFood(query);
    }

}
