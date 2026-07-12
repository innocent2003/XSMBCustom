package com.example.xsmbcustom.services;

import android.content.Context;

import com.example.xsmbcustom.model.LotteryPage;
import com.example.xsmbcustom.model.LotteryResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class JsonStorage {

    public static void save(Context context,
                            String fileName,
                            LotteryPage page) {

        try {

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            String json = gson.toJson(page);

            File file = new File(context.getFilesDir(), fileName);

            FileWriter writer = new FileWriter(file);

            writer.write(json);

            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<LotteryPage> loadAll(Context context) {

        ArrayList<LotteryPage> pages = new ArrayList<>();

        Gson gson = new Gson();

        File dir = context.getFilesDir();

        File[] files = dir.listFiles();

        if (files == null) return pages;

        for (File file : files) {

            if (!file.getName().startsWith("xsmb_")
                    || !file.getName().endsWith(".json"))
                continue;

            try {

                FileReader reader = new FileReader(file);

                LotteryPage page =
                        gson.fromJson(reader, LotteryPage.class);

                reader.close();

                if (page != null)
                    pages.add(page);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return pages;
    }


}
