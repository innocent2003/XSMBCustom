package com.example.xsmbcustom.model;

import java.util.List;

public class LotteryResult {

    public String prize;

    public List<String> numbers;

    public int column;

    public LotteryResult(String prize,
                         List<String> numbers,
                         int column) {

        this.prize = prize;
        this.numbers = numbers;
        this.column = column;
    }

}