package com.example.paperdb;

import androidx.annotation.Nullable;

public class Book {
    private String id;
    private String Name;
    private String Price;
    private String Img;

    public Book (@Nullable String id, String name, String price, String img){
        this.id = id;
        this.Price = price;
        this.Name = name;
        this.Img = img;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
