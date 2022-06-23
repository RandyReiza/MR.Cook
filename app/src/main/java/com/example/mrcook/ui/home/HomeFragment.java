package com.example.mrcook.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.query = binding.querySearch.getQuery().toString();
                refreshCurrentState();
            }
        });

        adapter = new FoodAdapter();
        adapter.notifyDataSetChanged();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel = obtainViewModel(requireActivity());

        refreshCurrentState();

        binding.querySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String query = binding.querySearch.getQuery().toString();

                getAllFoodDataAndSetToAdapter(query);
                binding.querySearch.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        adapter.setOnItemClickCallback(new FoodAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Food food) {
                navigateToDetail(requireView(), food);
            }
        });
    }

    private void refreshCurrentState(){
        String query = viewModel.query;
        if (query.isEmpty()) {
            getAllFoodDataAndSetToAdapter(null);
        } else {
            getAllFoodDataAndSetToAdapter(query);
        }
    }

    private void getAllFoodDataAndSetToAdapter(@Nullable String query) {
        if (query == null || query.isEmpty()) {
            viewModel.fetchAllFoodData();
        } else {
            viewModel.searchFood(query);
        }
        observeAll();
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    private void observeAll() {
        viewModel.listFoodLiveData.observe(getViewLifecycleOwner(), it -> {
            adapter.setData(it);
        });
        viewModel.isLoading.observe(getViewLifecycleOwner(), it -> {
            Helper.showView(binding.progressBar, it);
        });
        viewModel.isShowInfo.observe(getViewLifecycleOwner(), it -> {
            showInfo(it, "Recipe is not Found");
        });
    }

    private void showInfo(Boolean state, String text) {
        binding.infoText.setText(text);
        if (state) {
            binding.infoText.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.infoText.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
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