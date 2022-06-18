package com.example.mrcook.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mrcook.adapter.FoodAdapter;
import com.example.mrcook.databinding.FragmentHomeBinding;
import com.example.mrcook.entity.Food;
import com.example.mrcook.helper.Helper;
import com.example.mrcook.helper.ViewModelFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private FoodAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FoodAdapter();
        adapter.notifyDataSetChanged();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel = obtainViewModel(requireActivity());

        observeAndShowCookRecipe();

        adapter.setOnItemClickCallback(new FoodAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Food food) {
                navigateToDetail(requireView(), food);
            }
        });
    }

    private void observeAndShowCookRecipe() {
        Helper.showView(binding.progressBar, true);
        viewModel.fetchAllFoodData();

        viewModel.listFoodLiveData.observe(getViewLifecycleOwner(), food -> {
            adapter.setData(food);
            Helper.showView(binding.progressBar, false);
        });
    }

    private HomeViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(HomeViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void navigateToDetail(View view, Food food) {
        HomeFragmentDirections.ActionHomeFragmentToDetailFragment action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(food);
        Navigation.findNavController(view).navigate(action);
    }
}