package com.example.mrcook.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mrcook.R;
import com.example.mrcook.helper.Helper;
import com.example.mrcook.helper.ViewModelFactory;
import com.example.mrcook.session.SessionManagerUtil;
import com.example.mrcook.ui.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnSignIn;
    private LoginViewModel viewModel;
    String username;
    String password;
    SessionManagerUtil sessionManagerUtil;

    private final long ONE_MONTH_IN_MILLIS = 2629800000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManagerUtil = new SessionManagerUtil(getApplicationContext());

        checkSession();

        if (sessionManagerUtil.getLogin() &&
                (!sessionManagerUtil.getToken().isEmpty() || sessionManagerUtil.getToken() != null)){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btnSignIn = findViewById(R.id.btn_signIn);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);

        viewModel = obtainViewModel(LoginActivity.this);
        viewModel.user.observe(this, it -> {
            if (!sessionManagerUtil.getToken().isEmpty() || sessionManagerUtil.getToken() != null) {
                sessionManagerUtil.setLogin(true);
                sessionManagerUtil.setToken(it.getToken());
                sessionManagerUtil.setUsername(it.getUsername());
                sessionManagerUtil.setFullName(it.getFullName());
                sessionManagerUtil.setEmail(it.getEmail());
                sessionManagerUtil.setSessionExpiredTime(System.currentTimeMillis() + ONE_MONTH_IN_MILLIS);

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
        viewModel.isLoggedIn.observe(this, it -> {
            if (it) {
                Helper.showToast(getApplicationContext(), "Login Success");
            } else {
                Helper.showToast(getApplicationContext(), "Username or Password do not match.");
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = inputUsername.getText().toString();
                password = inputPassword.getText().toString();
                viewModel.userLogin(username, password);
            }
        });
    }

    private LoginViewModel obtainViewModel(AppCompatActivity activity){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(LoginViewModel.class);
    }


    private void checkSession() {
        if (Helper.isSessionExpired(getApplicationContext())) {
            Helper.showToast(getApplicationContext(), "Session Time out, Please Re-Login");
        }
    }
}