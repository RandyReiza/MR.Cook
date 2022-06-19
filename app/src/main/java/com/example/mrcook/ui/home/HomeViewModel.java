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
import com.example.mrcook.restapi.foodrecipe.responses.search.ResponseFoodSearch;

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

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading = _isLoading;

    private MutableLiveData<Boolean> _isShowInfo = new MutableLiveData<>();
    public final LiveData<Boolean> isShowInfo = _isShowInfo;

    public String query = "";

    private ArrayList<Food> listFood = new ArrayList<>();

    public void fetchAllFoodData() {
        listFood.clear();
        this.query = "";
        _isLoading.postValue(true);

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
                        _isLoading.postValue(false);
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

    public void searchFood(String query) {
        listFood.clear();
        this.query = query;
        showInfo(false);

        Call<ResponseFoodSearch> client = foodRepository.searchFood(query);
        client.enqueue(new Callback<ResponseFoodSearch>() {
            @Override
            public void onResponse(Call<ResponseFoodSearch> call, Response<ResponseFoodSearch> response) {
                try {
                    ResponseFoodSearch responseBody = response.body();
                    if (response.isSuccessful() && responseBody != null) {
                        for (com.example.mrcook.restapi.foodrecipe.responses.search.ResultsItem item : responseBody.getResults()) {
                            Food food = new Food(item.getTitle(), item.getThumb(), item.getTimes(), item.getServing(), item.getDifficulty(), item.getKey(), null);
                            listFood.add(food);
                        }

                        if (!listFood.isEmpty()) {
                            fetchFoodDetail(listFood);
                        } else {
                            _listFoodLiveData.postValue(listFood);
                            showInfo(true);
                        }

                        // fetch detail
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseFoodSearch> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void showInfo(Boolean state) {
        if (state) {
            _isShowInfo.postValue(true);
            _isLoading.postValue(false);
        } else {
            _isShowInfo.postValue(false);
            _isLoading.postValue(true);
        }
    }

}
