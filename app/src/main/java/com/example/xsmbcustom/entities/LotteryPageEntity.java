package com.example.xsmbcustom.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.example.xsmbcustom.model.LotteryResult;

import java.util.ArrayList;

@Entity(tableName = "lottery_page")
public class LotteryPageEntity {

    @PrimaryKey
    @NonNull
    public String date;

    public ArrayList<LotteryResult> results;
}