package com.example.mrcook.ui.favorite;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mrcook.database.FoodData;
import com.example.mrcook.entity.Food;
import com.example.mrcook.repository.FoodRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FavoriteViewModel extends ViewModel {

    private final FoodRepository foodRepository;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public FavoriteViewModel(Application application) {
        foodRepository = new FoodRepository(application);
    }

    private static final String TAG = FavoriteViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<Food>> _listFoodLiveData = new MutableLiveData<>();
    public final LiveData<ArrayList<Food>> listFoodLiveData = _listFoodLiveData;

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading = _isLoading;

    private MutableLiveData<Boolean> _isShowInfo = new MutableLiveData<>();
    public final LiveData<Boolean> isShowInfo = _isShowInfo;

    private ArrayList<Food> listFood = new ArrayList<>();


    public void findAllFavFoods() {
        listFood.clear();

        Callable<List<FoodData>> callableFoodData = foodRepository::getAllFoods;
        Future<List<FoodData>> FoodDataFuture = executorService.submit(callableFoodData);

        try {
            List<FoodData> foodData = FoodDataFuture.get();

            for (FoodData fd : foodData) {
                List<String> ingredients = Collections.singletonList(fd.getIngredient());
                List<String> steps = Collections.singletonList(fd.getStep());
                Food food = new Food(fd.getTitle(), fd.getThumb(), fd.getTimes(), fd.getPortion(), fd.getDificulty(), fd.getKey(), fd.getDesc(), ingredients, steps);
                listFood.add(food);
            }

            _listFoodLiveData.postValue(listFood);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void findAllFavFoodByTitleLike(String title) {
        listFood.clear();
        showInfo(false);

        Callable<List<FoodData>> callableFoodData = () -> {
            List<FoodData> foodData = foodRepository.getAllFoodsByTitleLike(title);
            return foodData;
        };
        Future<List<FoodData>> FoodDataFuture = executorService.submit(callableFoodData);

        try {
            List<FoodData> foodData = FoodDataFuture.get();
            if (foodData != null && !foodData.isEmpty()) {
                for (FoodData fd : foodData) {
                    List<String> ingredients = Collections.singletonList(fd.getIngredient());
                    List<String> steps = Collections.singletonList(fd.getStep());
                    Food food = new Food(fd.getTitle(), fd.getThumb(), fd.getTimes(), fd.getPortion(), fd.getDificulty(), fd.getKey(), fd.getDesc(), ingredients, steps);
                    listFood.add(food);
                }

                _listFoodLiveData.postValue(listFood);
                _isLoading.postValue(false);
            } else {
                showInfo(true);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
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
