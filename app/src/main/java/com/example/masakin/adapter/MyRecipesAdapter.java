package com.example.masakin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.masakin.R;
import com.example.masakin.database.DBHelper;
import com.example.masakin.model.Recipe;
import com.example.masakin.activity.DetailRecipeActivity;

import java.io.File;
import java.util.List;

public class MyRecipesAdapter extends RecyclerView.Adapter<MyRecipesAdapter.MyRecipesViewHolder> {
    private List<Recipe> recipeList;
    private Context context;
    private DBHelper dbHelper;
    private String sourceType;


    public MyRecipesAdapter(Context context, List<Recipe> recipeList,String sourceType){
        this.context = context;
        this.recipeList = recipeList;
        this.dbHelper = new DBHelper(context);
        this.sourceType = sourceType;
    }


    public void setFilteredList(List<Recipe> filteredList){
        this.recipeList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public MyRecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_recipe, parent, false);
        return new MyRecipesViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyRecipesViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.tvTitle.setText(recipe.getTitle());
        holder.tvDesc.setText(recipe.getDesc());
        holder.tvTime.setText(String.valueOf(recipe.getTime()));

        String imagePath = recipe.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            if (imagePath.startsWith("/")) {
                // Path lokal ⇒ muat pakai Glide dari File
                Glide.with(holder.itemView.getContext())
                        .load(new File(imagePath))
                        .placeholder(R.drawable.default_image)
                        .into(holder.ivImage);
            } else {
                // Nama drawable ⇒ muat dari resources
                int resId = holder.itemView.getContext()
                        .getResources()
                        .getIdentifier(
                                imagePath,
                                "drawable",
                                holder.itemView.getContext().getPackageName()
                        );
                if (resId != 0) {
                    holder.ivImage.setImageResource(resId);
                } else {
                    holder.ivImage.setImageResource(R.drawable.default_image);
                }
            }
        } else {
            holder.ivImage.setImageResource(R.drawable.default_image);
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

        SharedPreferences prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("id",0);


        holder.btnDel.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Hapus Resep")
                        .setMessage("Apakah kamu yakin ingin menghapus resep ini?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                          recipeList.get(pos);
                            if ("fav".equals(sourceType)) {
                                dbHelper.addFavorite(userId,recipe.getId());
                            } else if ("added".equals(sourceType)) {
                                dbHelper.addUserRecipe(userId,recipe);
                            }

                            recipeList.remove(pos);
                            notifyItemRemoved(pos);
                        })
                        .setNegativeButton("Batal", null)
                        .show();
            }
        });

    }


    public int getItemCount(){
        return recipeList.size();
    }

    public static class MyRecipesViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvDesc,tvTime;
        ImageView ivImage;
        ImageButton btnDel;

        public MyRecipesViewHolder(@NonNull View itemview){
            super(itemview);

            tvTitle = itemview.findViewById(R.id.tvTitle);
            tvDesc = itemview.findViewById(R.id.tvDesc);
            tvTime = itemview.findViewById(R.id.tvTime);
            ivImage = itemview.findViewById(R.id.ivImage);
            btnDel = itemview.findViewById(R.id.btnDelete);



        }
    }


}
