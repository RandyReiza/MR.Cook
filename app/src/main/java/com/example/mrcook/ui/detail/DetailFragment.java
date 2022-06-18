package com.example.mrcook.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mrcook.databinding.FragmentDetailBinding;
import com.example.mrcook.entity.Food;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Food food = DetailFragmentArgs.fromBundle(getArguments()).getFood();

        binding.tv.setText(food.getTitle() + "\n" + food.getThumb() + "\n" + food.getDificulty() + "\n" + food.getPortion() + "\n" + food.getKey() + "\n");
    }
}