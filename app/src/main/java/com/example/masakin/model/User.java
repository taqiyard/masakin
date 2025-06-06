package com.example.masakin.model;

public class User {
    private String username;
    private String password;
    private String profilePic;

    public User(String username, String password, String profilePic) {
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

}
