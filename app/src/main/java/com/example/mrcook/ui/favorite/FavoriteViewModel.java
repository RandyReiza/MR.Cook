package com.example.mrcook.ui.favorite;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mrcook.database.FoodData;
import com.example.mrcook.repository.FoodRepository;

import java.util.List;

public class FavoriteViewModel extends ViewModel {

    private final FoodRepository foodRepository;

    public FavoriteViewModel(Application application) {
        foodRepository = new FoodRepository(application);
    }

    private static final String TAG = FavoriteViewModel.class.getSimpleName();




    LiveData<List<FoodData>> getAllFoods() {
        return foodRepository.getAllFoods();
    }
}
