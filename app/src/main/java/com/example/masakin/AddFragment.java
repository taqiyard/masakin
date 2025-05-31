package com.example.masakin;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton ibUploadImage;
    private Uri selectedImageUri = null;


    public AddFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        EditText etTitle = view.findViewById(R.id.etAddTitle);
        EditText etDesc = view.findViewById(R.id.etAddDesc);
        EditText etIngredients = view.findViewById(R.id.etAddIngredients);
        EditText etInstructions = view.findViewById(R.id.etAddInstructions);
        EditText etTime = view.findViewById(R.id.etAddTime);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String ingredients = etIngredients.getText().toString().trim();
            String instructions = etInstructions.getText().toString().trim();
            String timeStr = etTime.getText().toString().trim();
            int isFav = 0;
            int isMine = 1;
            int isDel = 0;

            if (title.isEmpty() || desc.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || timeStr.isEmpty()) {
                Toast.makeText(getContext(), "Harap isi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedImageUri == null) {
                Toast.makeText(getContext(), "Harap pilih gambar", Toast.LENGTH_SHORT).show();
                return;
            }

            int time = Integer.parseInt(timeStr);
            String imageUriString = selectedImageUri.toString();

            Recipe recipe = new Recipe(title, desc, ingredients, instructions,imageUriString,isFav, isMine, isDel,time);
            DBHelper dbHelper = new DBHelper(getContext());
            boolean inserted = dbHelper.insertRecipe(recipe);

            if (inserted) {
                Toast.makeText(getContext(), "Resep berhasil disimpan", Toast.LENGTH_SHORT).show();
                etTitle.setText("");
                etDesc.setText("");
                etIngredients.setText("");
                etInstructions.setText("");
                etTime.setText("");
                ibUploadImage.setImageResource(R.drawable.upload_foto); // atau default
                selectedImageUri = null;
            } else {
                Toast.makeText(getContext(), "Gagal menyimpan resep", Toast.LENGTH_SHORT).show();
            }
        });


        ibUploadImage = view.findViewById(R.id.ibUploadImage);
        ibUploadImage.setOnClickListener(v -> openImagePicker());



        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ibUploadImage.setImageURI(selectedImageUri);
        }
    }
}
