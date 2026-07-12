package com.example.xsmbcustom.services;


import androidx.room.Database;
import androidx.room.TypeConverters;
import androidx.room.RoomDatabase;


import com.example.xsmbcustom.dao.LotteryDao;
import com.example.xsmbcustom.entities.LotteryPageEntity;

@Database(
        entities = {LotteryPageEntity.class},
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LotteryDao lotteryDao();

}
