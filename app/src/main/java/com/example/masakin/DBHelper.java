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
                "instructions TEXT, " +
                "image TEXT, " +
                "isFav INTEGER DEFAULT 0, " +
                "isMine INTEGER DEFAULT 0, " +
                "isDel INTEGER DEFAULT 0, " +
                "time INTEGER)");

        ContentValues values = new ContentValues();
        values.put("title", "Nasi Goreng");
        values.put("desc", "Nasi goreng simpel dan enak");
        values.put("ingredients", "Nasi, telur, bawang, kecap");
        values.put("instructions", "1. Tumis bawang\n2. Masukkan telur\n3. Masukkan nasi\n4. Tambahkan kecap");
        values.put("image", "nasi_goreng"); // bisa nama resource tanpa ekstensi
        values.put("time",15);

        ContentValues values1 = new ContentValues();
        values1.put("title", "Mie Goreng");
        values1.put("desc", "Mie goreng ga enak");
        values1.put("ingredients", "Nasi, telur, bawang, kecap");
        values1.put("instructions", "1. Tumis bawang\n2. Masukkan telur\n3. Masukkan nasi\n4. Tambahkan kecap");
        values1.put("image", "mie_goreng"); // bisa nama resource tanpa ekstensi
        values1.put("time",15);

        ContentValues values2 = new ContentValues();
        values2.put("title", "Ayam Bakar");
        values2.put("desc", "Ayam bakar bumbu khas");
        values2.put("ingredients", "Ayam, kecap, bawang putih, bawang merah, ketumbar");
        values2.put("instructions", "1. Haluskan bumbu\n2. Lumuri ayam\n3. Bakar ayam hingga matang");
        values2.put("image", "ayam_bakar");
        values2.put("time",30);


        ContentValues values3 = new ContentValues();
        values3.put("title", "Soto Ayam");
        values3.put("desc", "Soto ayam kuning khas Jawa");
        values3.put("ingredients", "Ayam, kunyit, serai, bawang putih, daun bawang");
        values3.put("instructions", "1. Rebus ayam\n2. Tumis bumbu\n3. Campur semua dan sajikan panas");
        values3.put("image", "soto_ayam");
        values3.put("time",30);


        ContentValues values4 = new ContentValues();
        values4.put("title", "Tempe Orek");
        values4.put("desc", "Tempe orek manis pedas");
        values4.put("ingredients", "Tempe, bawang, cabe, kecap");
        values4.put("instructions", "1. Goreng tempe\n2. Tumis bumbu\n3. Masukkan tempe dan kecap");
        values4.put("image", "tempe_orek");
        values4.put("time",20);


        ContentValues values5 = new ContentValues();
        values5.put("title", "Capcay");
        values5.put("desc", "Capcay sayur sehat dan lezat");
        values5.put("ingredients", "Wortel, kol, brokoli, bawang putih, saus tiram");
        values5.put("instructions", "1. Tumis bawang\n2. Masukkan sayur\n3. Tambahkan saus dan air, masak sebentar");
        values5.put("image", "capcay");
        values5.put("time",15);



        db.insert("recipes", null, values);
        db.insert("recipes", null, values1);
        db.insert("recipes", null, values2);
        db.insert("recipes", null, values3);
        db.insert("recipes", null, values4);
        db.insert("recipes", null, values5);
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
        values.put("isFav",0);
        values.put("isMine",1);
        values.put("isDel",0);
        values.put("time", recipe.getTime());

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
                int isFav = cursor.getInt(6);
                int isMine = cursor.getInt(7);
                int isDel = cursor.getInt(8);
                int time = cursor.getInt(9);

                recipes.add(new Recipe(id, title, desc, ingredients, instructions, image, isFav, isMine, isDel, time)); // <--- Kirim desc
            } while (cursor.moveToNext());
        }


        cursor.close();
        return recipes;
    }


    public List<Recipe> getFavoriteRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes WHERE isFav = 1", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String desc = cursor.getString(2);
                String ingredients = cursor.getString(3);
                String instructions = cursor.getString(4);
                String image = cursor.getString(5);
                int isFav = cursor.getInt(6);
                int isMine = cursor.getInt(7);
                int isDel = cursor.getInt(8);
                int time = cursor.getInt(9);

                recipes.add(new Recipe(id, title, desc, ingredients, instructions, image, isFav, isMine, isDel, time));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return recipes;
    }

    public List<Recipe> getMyRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes WHERE isMine = 1", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String desc = cursor.getString(2);
                String ingredients = cursor.getString(3);
                String instructions = cursor.getString(4);
                String image = cursor.getString(5);
                int isFav = cursor.getInt(6);
                int isMine = cursor.getInt(7);
                int isDel = cursor.getInt(8);
                int time = cursor.getInt(9);

                recipes.add(new Recipe(id, title, desc, ingredients, instructions, image, isFav, isMine, isDel, time));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return recipes;
    }

}
