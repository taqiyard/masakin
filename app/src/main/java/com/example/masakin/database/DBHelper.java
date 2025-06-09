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
                "isDel INTEGER DEFAULT 0)");

        db.execSQL("CREATE TABLE user_recipes (" +
                "user_id INTEGER, " +
                "recipe_id INTEGER, " +
                "PRIMARY KEY (user_id, recipe_id))");

        db.execSQL("CREATE TABLE user_favorites (" +
                "user_id INTEGER, " +
                "recipe_id INTEGER, " +
                "PRIMARY KEY (user_id, recipe_id))");


        // Resep 1
        ContentValues v1 = new ContentValues();
        v1.put("title", "Nasi Goreng Spesial");
        v1.put("desc", "Nasi goreng dengan topping telur dan sosis");
        v1.put("ingredients", "Nasi putih, Telur, Sosis, Bawang putih, Bawang merah, Kecap manis, Minyak");
        v1.put("instructions", "1. Tumis bawang putih dan merah.\n2. Masukkan telur, orak-arik.\n3. Tambahkan sosis dan nasi.\n4. Tuang kecap, aduk rata, sajikan.");
        v1.put("image", "nasi_goreng");
        v1.put("time", 20);
        db.insert("recipes", null, v1);

        // Resep 2
        ContentValues v2 = new ContentValues();
        v2.put("title", "Mie Goreng Jawa");
        v2.put("desc", "Mie goreng khas Jawa dengan rasa gurih manis");
        v2.put("ingredients", "Mie telur, Kol, Wortel, Ayam suwir, Bawang putih, Kecap manis, Garam, Merica");
        v2.put("instructions", "1. Rebus mie.\n2. Tumis bawang putih.\n3. Masukkan sayur dan ayam.\n4. Tambahkan mie, kecap, dan bumbu. Aduk rata.");
        v2.put("image", "mie_goreng");
        v2.put("time", 25);
        db.insert("recipes", null, v2);

        // Resep 3
        ContentValues v3 = new ContentValues();
        v3.put("title", "Ayam Bakar Kecap");
        v3.put("desc", "Ayam bakar dengan bumbu kecap manis pedas");
        v3.put("ingredients", "Ayam, Kecap manis, Bawang putih, Bawang merah, Cabai, Garam, Ketumbar");
        v3.put("instructions", "1. Haluskan bumbu.\n2. Lumuri ayam, marinasi 1 jam.\n3. Bakar sambil oles bumbu hingga matang.");
        v3.put("image", "ayam_bakar");
        v3.put("time", 40);
        db.insert("recipes", null, v3);

        // Resep 4
        ContentValues v4 = new ContentValues();
        v4.put("title", "Soto Ayam Kampung");
        v4.put("desc", "Soto ayam berkuah kuning dengan ayam kampung");
        v4.put("ingredients", "Ayam kampung, Serai, Kunyit, Jahe, Daun bawang, Bawang putih, Garam, Air");
        v4.put("instructions", "1. Rebus ayam hingga empuk.\n2. Tumis bumbu halus.\n3. Campurkan ke dalam rebusan ayam.\n4. Tambahkan daun bawang, sajikan.");
        v4.put("image", "soto_ayam");
        v4.put("time", 45);
        db.insert("recipes", null, v4);

        // Resep 5
        ContentValues v5 = new ContentValues();
        v5.put("title", "Tempe Orek Pedas");
        v5.put("desc", "Tempe tumis dengan bumbu manis pedas");
        v5.put("ingredients", "Tempe, Bawang merah, Cabai rawit, Kecap, Gula, Garam");
        v5.put("instructions", "1. Goreng tempe hingga kering.\n2. Tumis bawang dan cabai.\n3. Masukkan tempe dan kecap. Aduk rata.");
        v5.put("image", "tempe_orek");
        v5.put("time", 15);
        db.insert("recipes", null, v5);

        // Resep 6
        ContentValues v6 = new ContentValues();
        v6.put("title", "Capcay Kuah");
        v6.put("desc", "Sayur capcay kuah sehat dan lezat");
        v6.put("ingredients", "Wortel, Kol, Sawi, Bunga kol, Jagung muda, Bawang putih, Saus tiram, Garam, Merica");
        v6.put("instructions", "1. Tumis bawang putih.\n2. Tambahkan semua sayur.\n3. Tambahkan air, saus tiram, dan bumbu. Masak hingga matang.");
        v6.put("image", "capcay");
        v6.put("time", 20);
        db.insert("recipes", null, v6);

        // Resep 7
        ContentValues v7 = new ContentValues();
        v7.put("title", "Rendang Daging");
        v7.put("desc", "Rendang daging sapi khas Minang yang gurih dan pedas");
        v7.put("ingredients", "Daging sapi, Santan, Lengkuas, Serai, Cabai merah, Bawang merah, Bawang putih, Daun jeruk");
        v7.put("instructions", "1. Tumis bumbu halus.\n2. Masukkan daging dan santan.\n3. Masak lama dengan api kecil hingga kering dan empuk.");
        v7.put("image", "rendang");
        v7.put("time", 120);
        db.insert("recipes", null, v7);

        // Resep 8
        ContentValues v8 = new ContentValues();
        v8.put("title", "Tumis Kangkung");
        v8.put("desc", "Tumis kangkung sederhana dan nikmat");
        v8.put("ingredients", "Kangkung, Bawang putih, Cabai merah, Terasi, Garam");
        v8.put("instructions", "1. Tumis bawang dan cabai.\n2. Tambahkan kangkung.\n3. Tambahkan terasi dan garam. Tumis cepat, sajikan.");
        v8.put("image", "kangkung");
        v8.put("time", 10);
        db.insert("recipes", null, v8);

        // Resep 9
        ContentValues v9 = new ContentValues();
        v9.put("title", "Bakwan Sayur");
        v9.put("desc", "Gorengan bakwan renyah isi sayuran");
        v9.put("ingredients", "Kol, Wortel, Tepung terigu, Bawang putih, Air, Garam, Merica");
        v9.put("instructions", "1. Campur semua bahan.\n2. Tambahkan air hingga adonan kental.\n3. Goreng hingga kecokelatan.");
        v9.put("image", "bakwan");
        v9.put("time", 20);
        db.insert("recipes", null, v9);

        // Resep 10
        ContentValues v10 = new ContentValues();
        v10.put("title", "Perkedel Kentang");
        v10.put("desc", "Perkedel kentang goreng lembut di dalam");
        v10.put("ingredients", "Kentang, Bawang goreng, Telur, Garam, Merica, Seledri");
        v10.put("instructions", "1. Rebus dan haluskan kentang.\n2. Campur bahan lain.\n3. Bentuk bulat, goreng hingga matang.");
        v10.put("image", "perkedel");
        v10.put("time", 25);
        db.insert("recipes", null, v10);

        // Resep 11
        ContentValues v11 = new ContentValues();
        v11.put("title", "Telur Dadar Padang");
        v11.put("desc", "Telur dadar tebal dengan bumbu khas Padang");
        v11.put("ingredients", "Telur, Kelapa parut, Cabai, Bawang, Daun kunyit, Daun bawang, Garam");
        v11.put("instructions", "1. Campur semua bahan.\n2. Goreng dengan minyak banyak dan api kecil agar matang merata.");
        v11.put("image", "telur_dadar");
        v11.put("time", 15);
        db.insert("recipes", null, v11);

        // Resep 12
        ContentValues v12 = new ContentValues();
        v12.put("title", "Sambal Goreng Kentang Ati");
        v12.put("desc", "Kentang dan ati sapi dimasak sambal merah pedas");
        v12.put("ingredients", "Kentang, Ati sapi, Bawang merah, Cabai, Tomat, Garam, Gula");
        v12.put("instructions", "1. Goreng kentang dan ati.\n2. Tumis sambal.\n3. Masukkan kentang dan ati. Aduk rata.");
        v12.put("image", "kentang_ati");
        v12.put("time", 35);
        db.insert("recipes", null, v12);

        // Resep 13
        ContentValues v13 = new ContentValues();
        v13.put("title", "Sayur Asem");
        v13.put("desc", "Sayur kuah segar dengan rasa asam khas");
        v13.put("ingredients", "Melinjo, Kacang panjang, Jagung, Labu siam, Asam jawa, Bawang, Gula merah");
        v13.put("instructions", "1. Rebus air dan asam.\n2. Masukkan bahan.\n3. Bumbui dan masak hingga empuk.");
        v13.put("image", "sayur_asem");
        v13.put("time", 30);
        db.insert("recipes", null, v13);

        // Resep 14
        ContentValues v14 = new ContentValues();
        v14.put("title", "Ayam Kecap");
        v14.put("desc", "Ayam dimasak dengan saus kecap manis gurih");
        v14.put("ingredients", "Ayam, Kecap manis, Bawang bombay, Bawang putih, Garam, Lada, Air");
        v14.put("instructions", "1. Tumis bawang.\n2. Masukkan ayam, tambahkan kecap dan bumbu.\n3. Masak hingga kuah menyusut.");
        v14.put("image", "ayam_kecap");
        v14.put("time", 35);
        db.insert("recipes", null, v14);

        // Resep 15
        ContentValues v15 = new ContentValues();
        v15.put("title", "Sup Ayam Wortel");
        v15.put("desc", "Sup ringan dengan ayam dan wortel, cocok untuk anak-anak");
        v15.put("ingredients", "Ayam, Wortel, Kentang, Daun bawang, Bawang putih, Merica, Garam");
        v15.put("instructions", "1. Rebus ayam dan buang busanya.\n2. Tambahkan wortel dan kentang.\n3. Bumbui dan masak hingga matang.");
        v15.put("image", "sup_ayam");
        v15.put("time", 30);
        db.insert("recipes", null, v15);

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

    public boolean isRecipeFavoritedByUser(int userId, int recipeId) {
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
                "SELECT * FROM user_recipes WHERE recipe_id = ? AND user_id = ?",
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
                "SELECT * FROM recipes WHERE isDel = 0",
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

        // Mulai transaksi untuk memastikan konsistensi
        db.beginTransaction();
        try {
            // Sembunyikan relasi user-resep
            db.delete("user_recipes", "user_id = ? AND recipe_id = ?", new String[]{String.valueOf(userId), String.valueOf(recipeId)});
            // Tandai resep sebagai dihapus (soft delete)
            db.execSQL("UPDATE recipes SET isDel = 1 WHERE id = ?", new Object[]{recipeId});

            db.setTransactionSuccessful(); // Tandai transaksi berhasil
        } finally {
            db.endTransaction(); // Selesai transaksi (commit jika berhasil, rollback jika gagal)
        }
    }


}
