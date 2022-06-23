package com.example.mrcook.database;

import static com.example.mrcook.database.DatabaseContract.FoodColumns.*;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = TABLE_NAME, indices = {@Index(value = {"key"}, unique = true)})
public class FoodData implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id = 0;

    @ColumnInfo(name = TITLE)
    private String title;

    @ColumnInfo(name = THUMB)
    private String thumb;

    @ColumnInfo(name = TIMES)
    private String times;

    @ColumnInfo(name = PORTION)
    private String portion;

    @ColumnInfo(name = DIFICULTY)
    private String dificulty;

    @ColumnInfo(name = KEY)
    private String key;

    @ColumnInfo(name = DESC)
    private String desc;

    @ColumnInfo(name = INGREDIENT)
    private String ingredient;

    @ColumnInfo(name = STEP)
    private String step;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public String getDificulty() {
        return dificulty;
    }

    public void setDificulty(String dificulty) {
        this.dificulty = dificulty;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }


    public FoodData(int id, String title, String thumb, String times, String portion, String dificulty, String key, String desc, String ingredient, String step) {
        this.id = id;
        this.title = title;
        this.thumb = thumb;
        this.times = times;
        this.portion = portion;
        this.dificulty = dificulty;
        this.key = key;
        this.desc = desc;
        this.ingredient = ingredient;
        this.step = step;
    }


    protected FoodData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        thumb = in.readString();
        times = in.readString();
        portion = in.readString();
        dificulty = in.readString();
        key = in.readString();
        desc = in.readString();
        ingredient = in.readString();
        step = in.readString();
    }

    public static final Creator<FoodData> CREATOR = new Creator<FoodData>() {
        @Override
        public FoodData createFromParcel(Parcel in) {
            return new FoodData(in);
        }

        @Override
        public FoodData[] newArray(int size) {
            return new FoodData[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(thumb);
        parcel.writeString(times);
        parcel.writeString(portion);
        parcel.writeString(dificulty);
        parcel.writeString(key);
        parcel.writeString(desc);
        parcel.writeString(ingredient);
        parcel.writeString(step);
    }
}
