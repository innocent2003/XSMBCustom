package com.example.xsmbcustom.model;

public class HomeItem {

    private String title;
    private String result;
    private int color;

    public HomeItem(String title,
                    String result,
                    int color) {
        this.title = title;
        this.result = result;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public String getResult() {
        return result;
    }

    public int getColor() {
        return color;
    }
}