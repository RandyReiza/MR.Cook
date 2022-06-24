package com.example.mrcook.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mrcook.R;
import com.example.mrcook.databinding.FragmentProfileBinding;
import com.example.mrcook.helper.ViewModelFactory;
import com.example.mrcook.session.SessionManagerUtil;
import com.example.mrcook.ui.MainActivity;
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

//        viewModel = obtainViewModel(requireActivity());
//        viewModel.user.observe(getViewLifecycleOwner(), it -> {
//            binding.tvFullname.setText(it.getFullName().toString());
//            binding.tvEmail.setText(it.getEmail().toString());
//            binding.tvUsername.setText(it.getUsername().toString());
//
//        });
        //if button back onclick
        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Back to Home", Toast.LENGTH_LONG).show();
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
            }
        });
    }
    private LoginViewModel obtainViewModel(FragmentActivity activity){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(LoginViewModel.class);
    }

    private void navigateToHome(View view){
        NavDirections action = ProfileFragmentDirections.actionProfileFragmentToHomeFragment();
        Navigation.findNavController(view).navigate(action);
    }
}