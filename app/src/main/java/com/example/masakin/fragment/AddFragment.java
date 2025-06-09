package com.example.masakin.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;
import com.example.masakin.database.DBHelper;
import com.example.masakin.R;
import com.example.masakin.model.Recipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton ibUploadImage;
    private Uri selectedImageUri = null;
    private String selectedImagePath = null;



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

        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("id",0);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String ingredients = etIngredients.getText().toString().trim();
            String instructions = etInstructions.getText().toString().trim();
            String timeStr = etTime.getText().toString().trim();

            if (title.isEmpty() || desc.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || timeStr.isEmpty()) {
                Toast.makeText(getContext(), "Harap isi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedImagePath == null) {
                Toast.makeText(getContext(), "Harap pilih gambar", Toast.LENGTH_SHORT).show();
                return;
            }

            int time = Integer.parseInt(timeStr);
            String imageUriString = selectedImageUri.toString();

            Recipe recipe = new Recipe(title, desc, ingredients, instructions,selectedImagePath,time,0);
            DBHelper dbHelper = new DBHelper(getContext());
            long inserted = dbHelper.addUserRecipe(userId,recipe);
            if (inserted == recipe.getId()) {
                Toast.makeText(getContext(), "Resep berhasil disimpan", Toast.LENGTH_SHORT).show();
                etTitle.setText("");
                etDesc.setText("");
                etIngredients.setText("");
                etInstructions.setText("");
                etTime.setText("");
                selectedImagePath = null;
                ibUploadImage.setImageResource(R.drawable.upload_foto); // atau default

            } else {
                Toast.makeText(getContext(), "Gagal menyimpan resep", Toast.LENGTH_SHORT).show();
            }
        });


        ibUploadImage = view.findViewById(R.id.ibUploadImage);
        ibUploadImage.setOnClickListener(v -> openImagePicker());



        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            selectedImageUri = imageUri; // <<-- ini tambahan

            // Salin ke internal storage
            String imagePath = copyImageToInternalStorage(imageUri, "img_" + System.currentTimeMillis() + ".jpg");

            if (imagePath != null) {
                selectedImagePath = imagePath;
                Glide.with(this).load(imagePath).into(ibUploadImage);
            }
        }
    }


    public String copyImageToInternalStorage(Uri uri, String filename) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            File file = new File(requireContext().getFilesDir(), filename);
            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return file.getAbsolutePath(); // ini yang bisa disimpan di SQLite
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
