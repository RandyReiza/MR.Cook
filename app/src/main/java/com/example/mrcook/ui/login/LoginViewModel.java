package com.example.mrcook.ui.login;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.mrcook.repository.LoginRepository;
import com.example.mrcook.restapi.user.responses.ResponseUser;
import com.example.mrcook.ui.MainActivity;
import com.example.mrcook.ui.home.HomeViewModel;

import kotlinx.coroutines.MainCoroutineDispatcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private final LoginRepository loginRepository;
    boolean status = false ;
    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    private static final String TAG = LoginViewModel.class.getSimpleName();

    public void userLogin(){
        Call<ResponseUser> client = loginRepository.userLogin();
        client.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if(response.isSuccessful()){
//                    loginRepository.userLogin();
                    status = true;
             }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Log.e(TAG, t.getMessage());
//                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
