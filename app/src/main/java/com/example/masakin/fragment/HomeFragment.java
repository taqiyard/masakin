package com.example.masakin.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.masakin.database.DBHelper;
import com.example.masakin.R;
import com.example.masakin.model.Recipe;
import com.example.masakin.adapter.RecipeAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private DBHelper dbHelper;
    private SearchView searchView;
    private List<Recipe> recipeList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());
        List<Recipe> recipeList = dbHelper.getAllRecipes();

        adapter = new RecipeAdapter(recipeList);
        recyclerView.setAdapter(adapter);

        searchView = view.findViewById(R.id.search_view);
        searchView.clearFocus();
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.sniglet);
        searchEditText.setTypeface(typeface);

// Jika ingin mengubah warna hint dan teks juga
        searchEditText.setHintTextColor(Color.GRAY); // atau pakai ContextCompat.getColor()
        searchEditText.setTextColor(Color.BLACK);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "Users");
        String profilePic = preferences.getString("profilePic","default_image");

        TextView tvUsername = view.findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        ImageView tvProfile = view.findViewById(R.id.profile_image);

        if (profilePic != null && !profilePic.isEmpty()) {
            if (profilePic.startsWith("/")) {
                // Path file lokal
                Glide.with(this)
                        .load(new File(profilePic))
                        .placeholder(R.drawable.default_image)
                        .into(tvProfile);
            } else {
                // Nama file dari drawable
                int resId = getResources().getIdentifier(
                        profilePic,
                        "drawable",
                        getContext().getPackageName()
                );
                if (resId != 0) {
                    tvProfile.setImageResource(resId);
                } else {
                    tvProfile.setImageResource(R.drawable.default_image);
                }
            }
        } else {
            tvProfile.setImageResource(R.drawable.default_image);
        }


        return view;


    }

    private void filterList(String text) {
        dbHelper = new DBHelper(getContext());
        List<Recipe> recipeList = dbHelper.getAllRecipes();
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : recipeList){
            if (recipe.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(recipe);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getContext(),"No data found", Toast.LENGTH_SHORT).show();
        }
        else {
            adapter.setFilteredList(filteredList);
        }
    }
}

