package com.example.xsmbcustom.dao;


import com.example.xsmbcustom.entities.LotteryPageEntity;

import java.util.List;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.Entity;
import androidx.room.Dao;
import androidx.room.Database;

@Dao
public interface LotteryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LotteryPageEntity page);

    @Query("SELECT * FROM lottery_page")
    List<LotteryPageEntity> getAll();

    @Query("SELECT * FROM lottery_page WHERE date=:date")
    LotteryPageEntity getByDate(String date);

}