package com.example.mrcook.ui.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mrcook.R;
import com.example.mrcook.database.FoodData;
import com.example.mrcook.databinding.FragmentDetailBinding;
import com.example.mrcook.entity.Food;
import com.example.mrcook.helper.Helper;
import com.example.mrcook.helper.ViewModelFactory;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private DetailViewModel viewModel;
    private Food food;

    private FoodData foodData = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        food = DetailFragmentArgs.fromBundle(getArguments()).getFood();

        viewModel = obtainViewModel(requireActivity());

        observeAllValues();

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        binding.buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFavorite(food);
            }
        });
        
        Glide.with(this)
                .load(food.getThumb())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(binding.imgCover);
        Glide.with(this)
                .load(food.getThumb())
                .apply(new RequestOptions().override(340, 170))
                .into(binding.imgView);

        // title
        binding.title.setText(food.getTitle().replace("Resep ", ""));

        // dificulty
        String dificulty;
        if (food.getDificulty().equals("Mudah")) {
            dificulty = "Easy";
        } else if (food.getDificulty().equals("Cukup Rumit")) {
            dificulty = "Medium";
        } else {
            dificulty = "Hard";
        }
        binding.dificulty.setText(dificulty);

        // portion
        binding.portion.setText(food.getPortion().split(" ")[0] + " Portion");

        // times
        binding.times.setText(food.getTimes());

        binding.description.setText(food.getDesc());

        String ingredient = TextUtils.join(" \n", food.getIngredient());
        binding.ingredient.setText(ingredient);

        String step = TextUtils.join(" \n", food.getStep());
        binding.step.setText(step);
    }

    private DetailViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(DetailViewModel.class);
    }

    private void observeAllValues() {
        viewModel.findFoodByKey(food.getKey()).observe(getViewLifecycleOwner(), it -> {
            foodData = it;
            if (foodData != null) binding.buttonFavorite.setImageResource(R.drawable.ic_baseline_favorite_24); else binding.buttonFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        });
        viewModel.isLoading.observe(getViewLifecycleOwner(), it -> {
            Helper.showView(binding.progressBar, it);
        });
    }

    private void changeFavorite(Food food) {
        if (foodData != null) {
            viewModel.deleteFood(foodData);
            setFavoriteState(false, food.getTitle());
        } else {
            viewModel.insertFood(food);
            setFavoriteState(true, food.getTitle());
        }
    }

    private void setFavoriteState(Boolean state, String title) {
        if (state) {
            binding.buttonFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
            Helper.showToast(requireContext(), getResources().getString(R.string.add_to_favorite, title.replace("Resep ", "")));
        } else {
            binding.buttonFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            Helper.showToast(requireContext(), getResources().getString(R.string.remove_from_favorite, title.replace("Resep ", "")));
        }
    }

}