package com.example.recyclerimage_text;

public class Items {
    private int imgID;
    private String titleStr;
    private String ktitleStr;
    Items(int imgID, String titleStr,String ktitleStr) {
        this.imgID = imgID;
        this.titleStr = titleStr;
        this.ktitleStr= ktitleStr;
    }

    public String getKtitleStr() {
        return ktitleStr;
    }

    public void setKtitleStr(String ktitleStr) {
        this.ktitleStr = ktitleStr;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }
}
