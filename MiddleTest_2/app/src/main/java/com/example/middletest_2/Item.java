package com.example.middletest_2;

public class Item {
    private String name;
    private int small;
    private int big;

    public String getName() {
        return name;
    }

    public int getSmall() {
        return small;
    }

    public int getBig() {
        return big;
    }

    public Item(String name, int small, int big) {
        this.name = name;
        this.small = small;
        this.big = big;
    }
}
