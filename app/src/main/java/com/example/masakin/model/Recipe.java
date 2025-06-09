package com.example.masakin.model;

public class Recipe {
    private int id;
    private String title;
    private String desc;
    private String ingredients;
    private String instructions;



    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int time;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    private int isDel;

    public Recipe(int id, String title, String desc, String ingredients, String instructions, String image,int time, int isDel) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.image = image;
        this.time = time;
        this.isDel = isDel;
    }

    // Constructor tanpa id, digunakan saat membuat data baru
    public Recipe(String title, String desc, String ingredients, String instructions, String image, int time,int isDel) {
        this.title = title;
        this.desc = desc;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.image = image;
        this.time = time;
        this.isDel = isDel;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}
