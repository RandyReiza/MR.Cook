package com.example.mrcook.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.mrcook.databinding.FragmentProfileBinding;
import com.example.mrcook.session.SessionManagerUtil;
import com.example.mrcook.ui.login.LoginActivity;
import com.example.mrcook.ui.login.LoginViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    SessionManagerUtil sessionManagerUtil;
    private SharedPreferences mSharedPreferences;
    private LoginViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagerUtil = new SessionManagerUtil(getContext().getApplicationContext());
            binding.tvFullname.setText(sessionManagerUtil.getFullName());
            binding.tvEmail.setText(sessionManagerUtil.getEmail());
            binding.tvUsername.setText(sessionManagerUtil.getUsername());

        //if button back onclick
        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome(v);
            }
        });

        //if sign out button onclick
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagerUtil.setLogin(false);
                sessionManagerUtil.setUsername(null);
                sessionManagerUtil.setFullName(null);
                sessionManagerUtil.setEmail(null);
                sessionManagerUtil.setToken(null);

                //Navigate to Login Activity
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }

    private void navigateToHome(View view){
        NavDirections action = ProfileFragmentDirections.actionProfileFragmentToHomeFragment();
        Navigation.findNavController(view).navigate(action);
    }
}