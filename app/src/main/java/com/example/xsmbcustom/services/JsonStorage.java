package com.example.xsmbcustom.services;

import android.content.Context;

import com.example.xsmbcustom.model.LotteryResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class JsonStorage {

    public static void save(Context context,
                            String fileName,
                            List<LotteryResult> data) {

        try {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            String json = gson.toJson(data);

            File file = new File(context.getFilesDir(), fileName);

            FileWriter writer = new FileWriter(file);

            writer.write(json);

            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
