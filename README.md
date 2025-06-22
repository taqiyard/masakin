# 🍲 Masakin - Android Cooking Recipe App

**Masakin** is an Android application that allows users to:
- View default (built-in) recipes.
- Add their own custom recipes.
- Mark recipes as favorites.
- View a personal list of favorite recipes.
- Upload and display images for each recipe.

---

## 🧩 Key Features

### ✅ Default Recipes
- Displayed automatically on the Home screen.
- Cannot be deleted.
- Provided by the app (not user-generated).

### 📝 Add Custom Recipes
Users can create their own recipes with:
- Title
- Description
- Ingredients
- Instructions
- Image from local storage
- Estimated cooking time

### ❤️ Favorite Recipes
- Users can mark or unmark any recipe as a favorite.
- Favorites are stored per user using a local `user_id`.

### 🗑️ Delete Recipes
- Only user-created recipes can be deleted.
- Deletion is handled globally by setting a flag (`isDel = 1`) in the database, ensuring it won’t appear in any fragment or activity.

---

## 🗂️ Database Structure

Tables used:
- `recipes` — stores all recipe data (default and user-created)
- `user_favorites` — stores relationships between users and their favorite recipes
- `user_recipes` *(optional, can be removed if no longer needed)*

Example fields in the `recipes` table:
- `id`
- `title`
- `desc`
- `ingredients`
- `instructions`
- `image`
- `time`
- `isDel` *(1 = deleted)*

---

## ⚙️ Technologies Used

- **Java** with Android SDK
- **SQLite** for local database
- **Glide** for image loading
- **RecyclerView** for displaying recipe lists
- **SharedPreferences** for storing logged-in user data

---

## 🚀 Getting Started

1. Clone this repository
2. Open it in **Android Studio**
3. Run the app on an emulator or physical Android device

---

## 📌 Developer Notes

- Default recipes implicitly have `user_id = 0`.
- Recipes are marked as deleted (`isDel = 1`) instead of being physically removed from the database.
- All user and recipe data is stored locally; **no online authentication** is implemented yet.

---

## 📸 Screenshots
![image](https://github.com/user-attachments/assets/c70461ac-6345-4862-9919-ca101e249276)








---

## 👨‍💻 Developed By
Developed as a college project by [Taqi Yardan](https://github.com/).

---

## 📜 License
This project is licensed under the MIT License – feel free to use, modify, and distribute it.
