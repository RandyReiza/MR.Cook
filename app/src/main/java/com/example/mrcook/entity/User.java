package com.example.mrcook.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String username;
    String email;
    String fullName;
    String token;

    public User(String username, String email, String fullName, String token) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.token = token;
    }

    protected User(Parcel in) {
        username = in.readString();
        email = in.readString();
        fullName = in.readString();
        token = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(fullName);
        dest.writeString(token);
    }
}
