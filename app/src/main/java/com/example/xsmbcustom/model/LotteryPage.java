package com.example.xsmbcustom.model;

import java.util.ArrayList;

public class LotteryPage {

    public String date;

    public ArrayList<LotteryResult> results;

    public LotteryPage(String date,
                       ArrayList<LotteryResult> results) {
        this.date = date;
        this.results = results;
    }
}
