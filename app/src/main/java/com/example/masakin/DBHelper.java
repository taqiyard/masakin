package com.example.masakin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Masakin.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE users(username TEXT PRIMARY KEY, password TEXT, profilePic TEXT)");
        db.execSQL("CREATE TABLE recipes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "desc TEXT, " +
                "ingredients TEXT, " +
                "instructions TEXT," +
                "image TEXT)");

        ContentValues values = new ContentValues();
        values.put("title", "Nasi Goreng");
        values.put("desc", "Nasi goreng simpel dan enak");
        values.put("ingredients", "Nasi, telur, bawang, kecap");
        values.put("instructions", "1. Tumis bawang\n2. Masukkan telur\n3. Masukkan nasi\n4. Tambahkan kecap");
        values.put("image", "nasi_goreng"); // bisa nama resource tanpa ekstensi

        ContentValues values1 = new ContentValues();
        values1.put("title", "Mie Goreng");
        values1.put("desc", "Mie goreng ga enak");
        values1.put("ingredients", "Nasi, telur, bawang, kecap");
        values1.put("instructions", "1. Tumis bawang\n2. Masukkan telur\n3. Masukkan nasi\n4. Tambahkan kecap");
        values1.put("image", "mie_goreng"); // bisa nama resource tanpa ekstensi

        db.insert("recipes", null, values);
        db.insert("recipes", null, values1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("profilePic", "default_profile");
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkUserPass(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",new String[]{username,password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    public boolean insertRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", recipe.getTitle());
        values.put("desc", recipe.getDesc());
        values.put("ingredients", recipe.getIngredients());
        values.put("instructions", recipe.getInstructions());
        values.put("image", recipe.getImage()); // simpan nama drawable
        long result = db.insert("recipes", null, values);
        return result != -1;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String desc = cursor.getString(2);  // <--- Ambil desc
                String ingredients = cursor.getString(3);
                String instructions = cursor.getString(4);
                String image = cursor.getString(5);

                recipes.add(new Recipe(id, title, desc, ingredients, instructions, image)); // <--- Kirim desc
            } while (cursor.moveToNext());
        }


        cursor.close();
        return recipes;
    }









}
