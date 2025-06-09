package com.example.masakin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.masakin.model.Recipe;
import com.example.masakin.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Masakin.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT, profilePic TEXT)");
        db.execSQL("CREATE TABLE recipes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "desc TEXT, " +
                "ingredients TEXT, " +
                "instructions TEXT, " +
                "image TEXT, " +
                "time INTEGER, " +
                "isDel INTEGER)");

        db.execSQL("CREATE TABLE user_recipes (" +
                "user_id INTEGER, " +
                "recipe_id INTEGER, " +
                "PRIMARY KEY (user_id, recipe_id))");

        db.execSQL("CREATE TABLE user_favorites (" +
                "user_id INTEGER, " +
                "recipe_id INTEGER, " +
                "PRIMARY KEY (user_id, recipe_id))");


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
                int time = cursor.getInt(6);
                int isDel = cursor.getInt(7);

                recipes.add(new Recipe(id, title, desc, ingredients, instructions, image,time,isDel)); // <--- Kirim desc
            } while (cursor.moveToNext());
        }


        cursor.close();
        return recipes;
    }


    public boolean updateProfilePic(String username, String profilePicPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("profilePic", profilePicPath);

        int rows = db.update("users", cv, "username = ?", new String[]{username});
        return rows > 0; // true jika update berhasil
    }

    // Ambil profilePic user
    public String getProfilePic(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String profilePic = null;

        Cursor cursor = db.rawQuery("SELECT profilePic FROM users WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            profilePic = cursor.getString(0);
        }
        cursor.close();
        return profilePic;
    }

    public void addFavorite(int userId, int recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("recipe_id", recipeId);
        db.insert("user_favorites", null, values);
    }

    private Recipe cursorToRecipe(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String desc = cursor.getString(cursor.getColumnIndexOrThrow("desc"));
        String ingredients = cursor.getString(cursor.getColumnIndexOrThrow("ingredients"));
        String instructions = cursor.getString(cursor.getColumnIndexOrThrow("instructions"));
        String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
        int time = cursor.getInt(cursor.getColumnIndexOrThrow("time"));
        int isDel = cursor.getInt(cursor.getColumnIndexOrThrow("isDel"));

        return new Recipe(id, title, desc, ingredients, instructions, image, time, isDel);
    }

    public List<Recipe> getFavoriteRecipes(int userId) {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT r.* FROM recipes r " +
                        "JOIN user_favorites uf ON r.id = uf.recipe_id " +
                        "WHERE uf.user_id = ? AND r.isDel = 0",
                new String[]{String.valueOf(userId)}
        );

        while (cursor.moveToNext()) {
            recipes.add(cursorToRecipe(cursor)); // buat method ini sesuai struktur Recipe kamu
        }
        cursor.close();
        return recipes;
    }

    public long addUserRecipe(int userId, Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        long recipeId = -1;

        db.beginTransaction();
        try {
            // 1. Insert resep baru ke tabel recipes
            ContentValues values = new ContentValues();
            values.put("title", recipe.getTitle());
            values.put("desc", recipe.getDesc());
            values.put("ingredients", recipe.getIngredients());
            values.put("instructions", recipe.getInstructions());
            values.put("image", recipe.getImage());
            values.put("isDel", 0);
            values.put("time", recipe.getTime());

            recipeId = db.insert("recipes", null, values);

            if (recipeId == -1) {
                // Gagal insert resep
                return -1;
            }

            // 2. Masukkan relasi user dan resep baru ke user_recipes
            ContentValues relValues = new ContentValues();
            relValues.put("user_id", userId);
            relValues.put("recipe_id", recipeId);

            long relId = db.insert("user_recipes", null, relValues);
            if (relId == -1) {
                // Gagal insert relasi, rollback
                return -1;
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return recipeId; // kembalikan id resep baru
    }

    public int getUserIdByUsername(String username) {
        int userId = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }
        cursor.close();
        return userId;
    }

    public boolean isRecipeFavoritedByUser(int recipeId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM user_favorites WHERE recipe_id = ? AND user_id = ?",
                new String[]{String.valueOf(recipeId), String.valueOf(userId)}
        );
        boolean exists = cursor.moveToFirst();  // True jika ada datanya
        cursor.close();
        return exists;
    }

    public boolean isRecipeAddedByUser(int recipeId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM user_added_recipes WHERE recipe_id = ? AND user_id = ?",
                new String[]{String.valueOf(recipeId), String.valueOf(userId)}
        );
        boolean exists = cursor.moveToFirst();  // True jika ada datanya
        cursor.close();
        return exists;
    }

    public List<Recipe> getDefaultRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM recipes",
                null
        );

        while (cursor.moveToNext()) {
            recipes.add(cursorToRecipe(cursor)); // pastikan kamu punya method ini
        }
        cursor.close();
        return recipes;
    }

    public void removeFavorite(int userId, int recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("user_favorites", "user_id = ? AND recipe_id = ?", new String[]{String.valueOf(userId), String.valueOf(recipeId)});
    }

    public void hideUserRecipe(int userId, int recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE user_recipes SET is_hidden = 1 WHERE user_id = ? AND recipe_id = ?", new Object[]{userId, recipeId});
    }

}
