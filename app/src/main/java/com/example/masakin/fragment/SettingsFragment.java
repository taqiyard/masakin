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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.masakin.R;
import com.example.masakin.activity.AboutActivity;
import com.example.masakin.activity.LoginActivity;
import com.example.masakin.database.DBHelper;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SettingsFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ShapeableImageView ibChangepp;
    private Uri selectedImageUri = null;
    private String selectedImagePath = null;
    private ImageView tvProfile;
    private DBHelper dbHelper;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        dbHelper = new DBHelper(requireContext());

        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        username = prefs.getString("username", "defaultUser");

        ShapeableImageView ibLogout = view.findViewById(R.id.ibLogout);
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        tvProfile = view.findViewById(R.id.profile_image);
        ibChangepp = view.findViewById(R.id.ibChangepp);
        ShapeableImageView ibAbout = view.findViewById(R.id.ibAbout);

        tvUsername.setText(username);

        // Ambil foto profil dari database
        String profilePic = dbHelper.getProfilePic(username);

        if (profilePic != null && !profilePic.isEmpty()) {
            if (profilePic.startsWith("/")) {
                Glide.with(this)
                        .load(new File(profilePic))
                        .placeholder(R.drawable.default_profile)
                        .into(tvProfile);
            } else {
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

        ibLogout.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        ibChangepp.setOnClickListener(v -> openImagePicker());

        ibAbout.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), AboutActivity.class);
            startActivity(intent);
        });

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
            selectedImageUri = imageUri;

            String imagePath = copyImageToInternalStorage(imageUri, "img_" + System.currentTimeMillis() + ".jpg");

            if (imagePath != null) {
                selectedImagePath = imagePath;

                Glide.with(this)
                        .load(imagePath)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.default_profile)
                        .into(tvProfile);

                // Simpan path foto ke database, bukan SharedPreferences
                boolean updated = dbHelper.updateProfilePic(username, imagePath);
                if (!updated) {
                    // Handle jika update gagal (opsional)
                }
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

            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
