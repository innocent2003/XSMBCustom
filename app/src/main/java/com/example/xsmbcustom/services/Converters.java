package com.example.xsmbcustom.services;

import androidx.room.TypeConverter;

import com.example.xsmbcustom.model.LotteryResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static String fromLotteryResult(ArrayList<LotteryResult> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static ArrayList<LotteryResult> toLotteryResult(String json) {

        Type type =
                new TypeToken<ArrayList<LotteryResult>>(){}.getType();

        return new Gson().fromJson(json, type);
    }

}