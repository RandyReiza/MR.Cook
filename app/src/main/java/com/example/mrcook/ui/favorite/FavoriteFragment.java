package com.example.mrcook.ui.favorite;

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
import com.example.mrcook.databinding.FragmentFavoriteBinding;
import com.example.mrcook.entity.Food;
import com.example.mrcook.helper.Helper;
import com.example.mrcook.helper.ViewModelFactory;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavoriteViewModel viewModel;
    private FoodAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCurrentState();
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        adapter = new FoodAdapter();
        adapter.notifyDataSetChanged();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel = obtainViewModel(requireActivity());

        getAllFavFoodAndSetToAdapter("");

        binding.querySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String title = binding.querySearch.getQuery().toString();

                viewModel.findAllFavFoodByTitleLike(title);

                observeAll();
                return true;
            }
        });

        adapter.setOnItemClickCallback(new FoodAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Food food) {
                navigateToDetail(requireView(), food);
            }
        });
    }

    private void getAllFavFoodAndSetToAdapter(@Nullable String title) {
        viewModel.findAllFavFoodByTitleLike(title);
        observeAll();
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    private void observeAll() {
        viewModel.listFoodLiveData.observe(getViewLifecycleOwner(), it -> {
            if (it != null && !it.isEmpty()) {
                adapter.setData(it);
            }
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

    private void refreshCurrentState() {
        String title = binding.querySearch.getQuery().toString();
        if (title.isEmpty()) {
            getAllFavFoodAndSetToAdapter("");
        } else {
            getAllFavFoodAndSetToAdapter(title);
        }
    }

    private FavoriteViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(FavoriteViewModel.class);
    }

    private void navigateToDetail(View view, Food food) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToDetailFragment action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(food);
        Navigation.findNavController(view).navigate(action);
    }
}