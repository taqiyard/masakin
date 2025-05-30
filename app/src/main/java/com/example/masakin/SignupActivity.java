package com.example.masakin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnSignup,btnToLogin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new DBHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnToLogin = findViewById(R.id.login_screen);

        btnSignup.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show();
            } else if(dbHelper.checkUsername(username)) {
                Toast.makeText(this, "Username sudah dipakai", Toast.LENGTH_SHORT).show();
            } else {
                User user = new User(username, password,"default_profile");
                boolean inserted = dbHelper.insertUser(user);
                if(inserted) {
                    Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                    finish(); // kembali ke login
                } else {
                    Toast.makeText(this, "Gagal registrasi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnToLogin.setOnClickListener(v ->{
            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
            startActivity(intent);
        });
    }
}
