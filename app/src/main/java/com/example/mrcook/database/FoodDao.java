package com.example.mrcook.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FoodData foodData);

    @Update()
    void update(FoodData foodData);

    @Delete()
    void delete(FoodData foodData);

    @Query("SELECT * FROM food ORDER BY id ASC")
    LiveData<List<FoodData>> getAllFoods();

    @Query("SELECT * FROM food WHERE `key` = :key")
    LiveData<FoodData> getOneByKey(String key);
}
