package com.example.masakin.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masakin.database.DBHelper;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.masakin.R;
import com.example.masakin.model.Recipe;

import java.io.File;

public class DetailRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        DBHelper dbHelper = new DBHelper(this);

        ImageView ivImage = findViewById(R.id.ivImage);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvDesc = findViewById(R.id.tvDesc);
        TextView tvIngredients = findViewById(R.id.tvIngredients);
        TextView tvInstructions = findViewById(R.id.tvInstructions);
        ImageButton ibBack = findViewById(R.id.ibBack);
        TextView tvTime = findViewById(R.id.tvTime);
        Button btnFav = findViewById(R.id.btnFav);

        ibBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String ingredients = intent.getStringExtra("ingredients");
        String instructions = intent.getStringExtra("instructions");
        String imageName = intent.getStringExtra("image");
        int time = intent.getIntExtra("time", 30);

        int userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("id", -1);
        int recipeId = intent.getIntExtra("recipe_id", -1);


        if (dbHelper.isRecipeFavoritedByUser(userId,recipeId)) {
            btnFav.setText("Favorit ✔");
        } else {
            btnFav.setText("+ Favorit");
        }



        btnFav.setOnClickListener(v -> {
            if (dbHelper.isRecipeFavoritedByUser(userId, recipeId)) {
                dbHelper.removeFavorite(userId, recipeId);
                btnFav.setText("+ Favorit");
                Toast.makeText(this, "Resep dihapus dari favorit", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.addFavorite(userId, recipeId);
                btnFav.setText("Favorit ✔");
                Toast.makeText(this, "Resep ditambahkan ke favorit", Toast.LENGTH_SHORT).show();
            }
        });

        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvIngredients.setText(ingredients);
        tvInstructions.setText(instructions);
        tvTime.setText(String.valueOf(time));

        imageName = intent.getStringExtra("image");

        if (imageName != null && !imageName.isEmpty()) {
            if (imageName.startsWith("/")) {
                // Ini path file lokal, muat dari file
                Glide.with(this)
                        .load(new File(imageName))
                        .placeholder(R.drawable.default_image)
                        .into(ivImage);
            } else {
                // Ini nama drawable
                int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                if (imageResId != 0) {
                    ivImage.setImageResource(imageResId);
                } else {
                    ivImage.setImageResource(R.drawable.default_image);
                }
            }
        } else {
            ivImage.setImageResource(R.drawable.default_image);
        }

    }
}
