package com.example.masakin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masakin.database.DBHelper;
import com.example.masakin.adapter.MyRecipesAdapter;
import com.example.masakin.R;
import com.example.masakin.model.Recipe;

import java.util.ArrayList;
import java.util.List;


public class AddedRecipesFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_added_recipes, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());
        List<Recipe> recipeList = dbHelper.getAllRecipes();

        List<Recipe> favRecipes = new ArrayList<>();
        for (Recipe r : recipeList) {
            if (r.getIsMine() == 1 && r.getIsDel() == 0) {
                favRecipes.add(r);
            }
        }

        adapter = new MyRecipesAdapter(getContext(),favRecipes);
        recyclerView.setAdapter(adapter);
        return view;
    }
}