package com.example.masakin;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList;

    public RecipeAdapter(List<Recipe> recipeList){
        this.recipeList = recipeList;
    }


    public void setFilteredList(List<Recipe> filteredList){
        this.recipeList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position){
        Recipe recipe = recipeList.get(position);
        holder.tvTitle.setText(recipe.getTitle());
        holder.tvDesc.setText(recipe.getDesc());
        holder.tvTime.setText(String.valueOf(recipe.getTime()));

        String imageName = recipe.getImage();
        if (imageName != null && !imageName.isEmpty()) {
            int imageResId = holder.itemView.getContext().getResources()
                    .getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
            if (imageResId != 0) {
                holder.ivImage.setImageResource(imageResId);
            } else {
                holder.ivImage.setImageResource(R.drawable.default_image); // Gambar default kalau tidak ditemukan
                Log.d("IMAGE_LOAD", "Image name: " + imageName + ", ResId: " + imageResId);
            }
        } else {
            holder.ivImage.setImageResource(R.drawable.default_image); // Gambar default kalau null atau kosong
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailRecipeActivity.class);
            intent.putExtra("title", recipe.getTitle());
            intent.putExtra("desc", recipe.getDesc());
            intent.putExtra("ingredients", recipe.getIngredients());
            intent.putExtra("instructions", recipe.getInstructions());
            intent.putExtra("image", recipe.getImage());
            intent.putExtra("time", recipe.getTime());
            holder.itemView.getContext().startActivity(intent);
        });


    }


    public int getItemCount(){
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvDesc,tvTime;
        ImageView ivImage;

        public RecipeViewHolder(@NonNull View itemview){
            super(itemview);
            tvTitle = itemview.findViewById(R.id.tvTitle);
            tvDesc = itemview.findViewById(R.id.tvDesc);
            tvTime = itemview.findViewById(R.id.tvTime);
            ivImage = itemview.findViewById(R.id.ivImage);

        }
    }


}
