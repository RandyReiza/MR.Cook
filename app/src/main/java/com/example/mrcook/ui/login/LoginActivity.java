package com.example.mrcook.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mrcook.R;
import com.example.mrcook.helper.ViewModelFactory;
import com.example.mrcook.repository.LoginRepository;
import com.example.mrcook.session.SessionManagerUtil;
import com.example.mrcook.ui.MainActivity;
import com.example.mrcook.ui.splashscreen.SplashScreenActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnSignIn;
    private LoginViewModel viewModel;
    private LoginRepository loginRepository;
    String username;
    String password;
    SessionManagerUtil sessionManagerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManagerUtil = new SessionManagerUtil(getApplicationContext());
        //Check Session Login is True
        if(sessionManagerUtil.getlogin() == true){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btnSignIn = findViewById(R.id.btn_signIn);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);

        loginRepository = new LoginRepository(getApplication());
        viewModel = obtainViewModel(LoginActivity.this);
        viewModel.user.observe(this, it -> {
            //Menyimpan session user pada sharedPreference

            sessionManagerUtil.setLogin(true);
            //Menyimpan token pada session
            sessionManagerUtil.setToken(it.getToken());
            //Menyimpan username in session
            sessionManagerUtil.setUsername(it.getUsername());
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = inputUsername.getText().toString();
                password = inputPassword.getText().toString();
                viewModel.userLogin(username, password);
                if (viewModel.status == true) {
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();

                    //For
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    private LoginViewModel obtainViewModel(AppCompatActivity activity){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(LoginViewModel.class);
    }

}