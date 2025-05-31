package com.example.masakin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        ImageView ivImage = findViewById(R.id.ivImage);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvDesc = findViewById(R.id.tvDesc);
        TextView tvIngredients = findViewById(R.id.tvIngredients);
        TextView tvInstructions = findViewById(R.id.tvInstructions);
        ImageButton ibBack = findViewById(R.id.ibBack);
        TextView tvTime = findViewById(R.id.tvTime);

        ibBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String ingredients = intent.getStringExtra("ingredients");
        String instructions = intent.getStringExtra("instructions");
        String imageName = intent.getStringExtra("image");
        int time = intent.getIntExtra("time",30);

        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvIngredients.setText(ingredients);
        tvInstructions.setText(instructions);
        tvTime.setText(String.valueOf(time));

        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResId != 0) {
            ivImage.setImageResource(imageResId);
        } else {
            ivImage.setImageResource(R.drawable.default_image);
        }
    }
}
