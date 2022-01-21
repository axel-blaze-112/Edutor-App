package com.example.miniproj2.Models;

public class userModel
{
    String uid;
    String usernameame;
    String useremail;
    String userphoneno;
    String imageUr;
    int coins;
    int spins;

    public userModel() {
    }

    public userModel(String uid, String usernameame, String useremail, String userphoneno, String imageUr, int coins, int spins) {
        this.uid = uid;
        this.usernameame = usernameame;
        this.useremail = useremail;
        this.userphoneno = userphoneno;
        this.imageUr = imageUr;
        this.coins = coins;
        this.spins=spins;
    }

    public int getSpins() {
        return spins;
    }

    public void setSpins(int spins) {
        this.spins = spins;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsernameame() {
        return usernameame;
    }

    public void setUsernameame(String usernameame) {
        this.usernameame = usernameame;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserphoneno() {
        return userphoneno;
    }

    public void setUserphoneno(String userphoneno) {
        this.userphoneno = userphoneno;
    }

    public String getImageUr() {
        return imageUr;
    }

    public void setImageUr(String imageUr) {
        this.imageUr = imageUr;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
