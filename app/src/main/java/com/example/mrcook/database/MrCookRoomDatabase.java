package com.example.mrcook.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FoodData.class}, version = 1, exportSchema = false)
public abstract class MrCookRoomDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();

    private static volatile MrCookRoomDatabase INSTANCE;

    public static MrCookRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MrCookRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MrCookRoomDatabase.class, "mr_cook")
                        .build();
            }
        }
        return INSTANCE;
    }
}
