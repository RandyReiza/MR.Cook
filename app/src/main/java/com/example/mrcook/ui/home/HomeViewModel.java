package com.example.mrcook.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mrcook.entity.Food;
import com.example.mrcook.repository.FoodRepository;
import com.example.mrcook.restapi.foodrecipe.responses.detail.ResponseFoodDetail;
import com.example.mrcook.restapi.foodrecipe.responses.getall.ResponseFoodRecipe;
import com.example.mrcook.restapi.foodrecipe.responses.getall.ResultsItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private final FoodRepository foodRepository;

    public HomeViewModel(Application application) {
        foodRepository = new FoodRepository(application);
    }

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<Food>> _listFoodLiveData = new MutableLiveData<>();
    public final LiveData<ArrayList<Food>> listFoodLiveData = _listFoodLiveData;

    private ArrayList<Food> listFood = new ArrayList<>();

    public void fetchAllFoodData() {
        listFood.clear();

        Call<ResponseFoodRecipe> client = foodRepository.fetchAllFoodData();
        client.enqueue(new Callback<ResponseFoodRecipe>() {
            @Override
            public void onResponse(Call<ResponseFoodRecipe> call, Response<ResponseFoodRecipe> response) {
                try {
                    ResponseFoodRecipe responseBody = response.body();
                    if (response.isSuccessful() && responseBody != null) {
                        for (ResultsItem item : responseBody.getResults()) {
                            Food food = new Food(item.getTitle(), item.getThumb(), item.getTimes(), item.getPortion(), item.getDificulty(), item.getKey(), null);
                            listFood.add(food);
                        }

                        // fetch detail
                        fetchFoodDetail(listFood);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseFoodRecipe> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void fetchFoodDetail(ArrayList<Food> listFood) {
        for (Food food : listFood) {
            Call<ResponseFoodDetail> client = foodRepository.fetchFoodDetail(food.getKey());
            client.enqueue(new Callback<ResponseFoodDetail>() {
                @Override
                public void onResponse(Call<ResponseFoodDetail> call, Response<ResponseFoodDetail> response) {
                    try {
                        ResponseFoodDetail responseBody = response.body();
                        if (response.isSuccessful() && responseBody != null) {
                            food.setDesc(responseBody.getResults().getDesc());
                        }

                        // post value to MutableLiveData
                        _listFoodLiveData.postValue((listFood));
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseFoodDetail> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
        }
    }

}
