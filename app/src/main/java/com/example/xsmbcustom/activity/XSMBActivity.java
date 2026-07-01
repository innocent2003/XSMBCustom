package com.example.xsmbcustom.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.xsmbcustom.R;
import com.example.xsmbcustom.adapter.LotteryAdapter;
import com.example.xsmbcustom.adapter.XSMBPagerAdapter;
import com.example.xsmbcustom.model.LotteryResult;
import com.example.xsmbcustom.services.OnCrawlResultListener;
import com.example.xsmbcustom.services.XSMBCrawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XSMBActivity  extends AppCompatActivity {


    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xsmbactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


//        viewPager = findViewById(R.id.viewPager);
//
//        ArrayList<LotteryResult> day1 = loadDataNgay1();
//        ArrayList<LotteryResult> day2 = loadDataNgay1();
//        ArrayList<LotteryResult> day3 = loadDataNgay1();
//
//        List<ArrayList<LotteryResult>> pages = new ArrayList<>();
//
//        pages.add(day1);
//        pages.add(day2);
//        pages.add(day3);
//
//        XSMBPagerAdapter pagerAdapter =
//                new XSMBPagerAdapter(this, pages);
//
//        viewPager.setAdapter(pagerAdapter);
//


        viewPager = findViewById(R.id.viewPager);
        TextView txtDate = findViewById(R.id.txtDate);

        String url = "https://az24.vn/xsmb-sxmb-xo-so-mien-bac.html";

        XSMBCrawler.crawl(url, new OnCrawlResultListener() {

            @Override
            public void onSuccess(ArrayList<LotteryResult> list) {
                Log.d("XSMB", "Size = " + list.size());

                for (LotteryResult item : list) {
                    Log.d("XSMB", item.prize + " -> " + item.numbers);
                }

                runOnUiThread(() -> {

                    List<ArrayList<LotteryResult>> pages = new ArrayList<>();
                    pages.add(list);

                    XSMBPagerAdapter pagerAdapter =
                            new XSMBPagerAdapter(XSMBActivity.this, pages);

                    viewPager.setAdapter(pagerAdapter);

                    txtDate.setText("📅 Kết quả XSMB hôm nay");
                });

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });


        //////////
//        TextView txtDate = findViewById(R.id.txtDate);

        String[] dates = {
                "2026-06-28",
                "2026-06-27",
                "2026-06-26"
        };

        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {

                    @Override
                    public void onPageSelected(int position) {

                        txtDate.setText("📅 Kết quả XSMB ngày " + dates[position]);

                    }

                });

    }




    private ArrayList<LotteryResult> loadDataNgay1() {

        ArrayList<LotteryResult> list = new ArrayList<>();

        list.add(new LotteryResult("ĐB",
                Arrays.asList("12452"), 1));

        list.add(new LotteryResult("G1",
                Arrays.asList("17149"), 1));

        list.add(new LotteryResult("G2",
                Arrays.asList("78543", "09227"), 2));

        list.add(new LotteryResult("G3",
                Arrays.asList(
                        "04630", "04283", "69042",
                        "24619", "93901", "21143"), 3));

        list.add(new LotteryResult("G4",
                Arrays.asList(
                        "6660", "5298", "1396", "4449"), 4));

        list.add(new LotteryResult("G5",
                Arrays.asList(
                        "3504", "0054", "1193",
                        "2284", "8711", "5407"), 3));

        list.add(new LotteryResult("G6",
                Arrays.asList(
                        "006", "473", "114"), 3));

        list.add(new LotteryResult("G7",
                Arrays.asList(
                        "85", "41", "55", "36"), 4));

        return list;
    }
}