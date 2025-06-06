package com.example.masakin;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRecipesFragment extends Fragment {

    private Button btnAddedRecipes, btnMyFavorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_recipes,container, false);

        btnAddedRecipes = view.findViewById(R.id.btnAddedRecipes);
        btnMyFavorite = view.findViewById(R.id.btnMyFavorite);

        btnAddedRecipes.setOnClickListener(v -> {
            loadFragment(new AddedRecipesFragment());
            updateButtonState(false);
            btnAddedRecipes.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            btnMyFavorite.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        });

        btnMyFavorite.setOnClickListener((v -> {
            loadFragment(new FavRecipesFragment());
            updateButtonState(true);
            btnMyFavorite.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            btnAddedRecipes.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }));

        loadFragment(new FavRecipesFragment());
        updateButtonState(true);

        return view;
    }

    private void loadFragment(Fragment fragment){
        getChildFragmentManager().beginTransaction().replace(R.id.subFragmentContainer, fragment).commit();
    }

    private void updateButtonState(boolean myRecipeSelected){
        btnMyFavorite.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), myRecipeSelected ? R.color.active_tab : R.color.inactive_tab));
        btnAddedRecipes.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), myRecipeSelected ? R.color.inactive_tab : R.color.active_tab));
    }

}