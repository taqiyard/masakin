package com.example.masakin.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.masakin.database.DBHelper;
import com.example.masakin.R;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin,btnToDaftar;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToDaftar = findViewById(R.id.daftar_screen);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi username dan password", Toast.LENGTH_SHORT).show();
            } else {
                boolean valid = dbHelper.checkUserPass(username, password);
                if(valid) {

                    int id = dbHelper.getUserIdByUsername(username);  // Ambil id user

                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();
                    // Contoh pindah ke MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putInt("id", id);
                    editor.apply();
                    finish();
                } else {
                    Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnToDaftar.setOnClickListener(v ->{
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}
