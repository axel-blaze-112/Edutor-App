package com.example.miniproj2.Models;

public class lessonModel {

    String name;
    String description;
    String Image;

    public lessonModel() {
    }

    public lessonModel(String name, String description, String image) {
        this.name = name;
        this.description = description;
        Image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }


}
