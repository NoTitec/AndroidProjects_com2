package com.example.mymusicplayer;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public class ContactsItems {

    private String imgUri;
    private String name;
    private String colname;

    ContactsItems(String image,  String name, String colname){
        this.imgUri = image;
        this.name = name;
        this.colname = colname;
    }

    public void setImage(String img) {
        imgUri = img;
    }
    public void setName(String name) {
        this.name = name ;
    }
    public void setColname(String colname) {
        this.colname = colname ;
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