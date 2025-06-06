package com.example.masakin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FavRecipesFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyRecipesAdapter adapter;
    private DBHelper dbHelper;
    private List<Recipe> recipeList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav_recipes, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());
        List<Recipe> recipeList = dbHelper.getAllRecipes();

        List<Recipe> favRecipes = new ArrayList<>();
        for (Recipe r : recipeList) {
            if (r.getIsFav() == 1 && r.getIsDel() == 0) {
                favRecipes.add(r);
            }
        }

        adapter = new MyRecipesAdapter(favRecipes);
        recyclerView.setAdapter(adapter);
        return view;
    }
}