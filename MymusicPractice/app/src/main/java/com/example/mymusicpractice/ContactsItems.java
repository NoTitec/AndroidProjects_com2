package com.example.mymusicpractice;

public class ContactsItems {
    private String imgUri;
    private String name;
    private String colname;

    ContactsItems(String image,  String name, String colname){
        this.imgUri = image;
        this.name = name;
        this.colname = colname;
    }

    public String getImage() {
        return imgUri;
    }
    public String getName() {
        return name;
    }
    public String getColname() {
        return colname;
    }
}
