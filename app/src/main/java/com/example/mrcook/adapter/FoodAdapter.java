package com.example.mrcook.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mrcook.databinding.FoodItemsBinding;
import com.example.mrcook.entity.Food;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private final ArrayList<Food> listFood = new ArrayList<>();
    private  OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<Food> foods) {
        listFood.clear();
        listFood.addAll(foods);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FoodItemsBinding binding = FoodItemsBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new FoodViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int i) {
        holder.bind(listFood.get(i));
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        final FoodItemsBinding binding;

        FoodViewHolder(@NonNull FoodItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Food food) {
            // foto/thumb
            Glide.with(binding.getRoot().getContext())
                    .load(food.getThumb())
                    .apply(new RequestOptions().override(115, 132))
                    .into(binding.foodThumb);

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
            binding.portion.setText(food.getPortion().split(" ")[0]);

            // times
            String times;
            if (food.getTimes().split(" ").length > 1) {
                times = food.getTimes().split(" ")[0] + "...";
            } else {
                times = food.getTimes();
            }
            binding.times.setText(times);

            binding.getRoot().setOnClickListener(v ->
                onItemClickCallback.onItemClicked(food)
            );
        }
    }


    public interface OnItemClickCallback {
        void onItemClicked(Food food);
    }
}
