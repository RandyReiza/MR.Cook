package com.example.mrcook.ui.login;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mrcook.entity.User;
import com.example.mrcook.repository.LoginRepository;
import com.example.mrcook.restapi.user.responses.ResponseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private final LoginRepository loginRepository;

    private MutableLiveData<User> _user = new MutableLiveData<>();
    public final LiveData<User> user = _user;

    private MutableLiveData<Boolean> _isLoggedIn = new MutableLiveData<>();
    public final LiveData<Boolean> isLoggedIn = _isLoggedIn;

    private User userData;

    public LoginViewModel(Application application) {
       loginRepository = new LoginRepository(application);
    }

    private static final String TAG = LoginViewModel.class.getSimpleName();

    public void userLogin(String username, String password) {
        Call<ResponseUser> client = loginRepository.userLogin(username, password);
        client.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                try {
                    ResponseUser responseBody = response.body();
                    if (response.isSuccessful() && responseBody != null) {
                        userData = new User(responseBody.getData().getUsername(), responseBody.getData().getEmail(), responseBody.getData().getFullName(), responseBody.getToken());

                        _user.postValue(userData);
                        _isLoggedIn.postValue(true);
                    }
                } catch (Exception e) {
                    _isLoggedIn.postValue(false);
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
