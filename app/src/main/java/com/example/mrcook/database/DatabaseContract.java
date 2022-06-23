package com.example.mrcook.database;

import android.provider.BaseColumns;

import java.util.List;

public class DatabaseContract {

    static final class FoodColumns implements BaseColumns {
        static final String TABLE_NAME = "food";
        static final String TITLE = "title";
        static final String THUMB = "thumb";
        static final String TIMES = "times";
        static final String PORTION = "portion";
        static final String DIFICULTY = "dificulty";
        static final String KEY = "key";
        static final String DESC = "desc";
        static final String INGREDIENT = "ingredient";
        static final String STEP = "step";
    }

}
