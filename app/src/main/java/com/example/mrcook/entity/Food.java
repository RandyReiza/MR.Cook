package com.example.mrcook.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

    String title;
    String thumb;
    String times;
    String portion;
    String dificulty;
    String key;
    String desc;

    public Food(String title, String thumb, String times, String portion, String dificulty, String key, String desc) {
        this.title = title;
        this.thumb = thumb;
        this.times = times;
        this.portion = portion;
        this.dificulty = dificulty;
        this.key = key;
        this.desc = desc;
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

    protected Food(Parcel in) {
        title = in.readString();
        thumb = in.readString();
        times = in.readString();
        portion = in.readString();
        dificulty = in.readString();
        key = in.readString();
        desc = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(thumb);
        parcel.writeString(times);
        parcel.writeString(portion);
        parcel.writeString(dificulty);
        parcel.writeString(key);
        parcel.writeString(desc);
    }
}
