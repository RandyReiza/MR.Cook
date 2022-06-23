package com.example.mrcook.ui.detail;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mrcook.database.FoodData;
import com.example.mrcook.entity.Food;
import com.example.mrcook.repository.FoodRepository;

import java.util.List;

public class DetailViewModel extends ViewModel {

    private final FoodRepository foodRepository;

    public DetailViewModel(Application application) {
        foodRepository = new FoodRepository(application);
    }

    private static final String TAG = DetailViewModel.class.getSimpleName();

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading = _isLoading;


    
    LiveData<FoodData> findFoodByKey(String key) {
        return foodRepository.getOneByKey(key);
    }

    public void insertFood(Food food) {
        _isLoading.postValue(true);
        try {
            FoodData foodData = new FoodData(0, food.getTitle(), food.getThumb(),
                    food.getTimes(), food.getPortion(), food.getDificulty(), food.getKey(),
                    food.getDesc(), food.getIngredient().toString(), food.getStep().toString());
            foodRepository.insert(foodData);
            _isLoading.postValue(false);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void updateFood(FoodData foodData) {
        _isLoading.postValue(true);
        try {
            foodRepository.update(foodData);
            _isLoading.postValue(false);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void deleteFood(FoodData foodData) {
        _isLoading.postValue(true);
        try {
            foodRepository.delete(foodData);
            _isLoading.postValue(false);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
