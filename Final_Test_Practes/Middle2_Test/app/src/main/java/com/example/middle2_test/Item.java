package com.example.middle2_test;

/**
 * 이 클래스는 리사이클러뷰 1개에 들어갈 데이터 가짐
 */
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
