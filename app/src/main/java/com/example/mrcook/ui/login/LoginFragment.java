package com.example.mrcook.ui.login;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mrcook.R;
import com.example.mrcook.databinding.FragmentLoginBinding;
import com.example.mrcook.helper.ViewModelFactory;
import com.example.mrcook.repository.LoginRepository;
import com.example.mrcook.restapi.user.responses.ResponseUser;
import com.example.mrcook.ui.MainActivity;
import com.google.gson.Gson;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnSignIn;
    private Button btnSignUp;
    private LoginViewModel viewModel;
    private LoginRepository loginRepository;
    String username;
    String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnSignIn = view.findViewById(R.id.btn_signIn);
        btnSignUp = view.findViewById(R.id.btn_signUp);
        inputUsername= view.findViewById(R.id.inputUsername);
        inputPassword = view.findViewById(R.id.inputPassword);

        loginRepository = new LoginRepository(this.getActivity().getApplication());
        viewModel = new LoginViewModel(loginRepository);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = inputUsername.getText().toString();
                password = inputPassword.getText().toString();
                viewModel.userLogin(username, password);
                if(!viewModel.status == true) {
                    System.out.println(viewModel.status);
                    Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }
                else {
                    System.out.println(viewModel.status);
                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_LONG).show();
                }
            }
        }
        );

//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //for sign up process
//            }
//        });

    }

    private LoginViewModel obtainViewModel(FragmentActivity activity){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(LoginViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}