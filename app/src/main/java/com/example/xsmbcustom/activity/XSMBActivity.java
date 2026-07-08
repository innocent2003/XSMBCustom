package com.example.xsmbcustom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.xsmbcustom.MainActivity;
import com.example.xsmbcustom.R;
import com.example.xsmbcustom.adapter.LotteryAdapter;
import com.example.xsmbcustom.adapter.XSMBPagerAdapter;
import com.example.xsmbcustom.model.LotteryPage;
import com.example.xsmbcustom.model.LotteryResult;
import com.example.xsmbcustom.services.OnCrawlResultListener;
import com.example.xsmbcustom.services.OnMultiCrawlResultListener;
import com.example.xsmbcustom.services.XSMBCrawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XSMBActivity  extends AppCompatActivity {


    private ViewPager2 viewPager;
    private ArrayList<LotteryPage> pages = new ArrayList<>();
    private static final String TAG = "XSMBActivity";
//    ImageView btnBack = findViewById(R.id.btnBack);

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
        ImageView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        viewPager = findViewById(R.id.viewPager);
//        viewPager.setRotationY(180f);
        TextView txtDate = findViewById(R.id.txtDate);
        ArrayList<String> urls = new ArrayList<>();

//        urls.add("https://az24.vn/xsmb-sxmb-xo-so-mien-bac.html");
        urls.add("https://az24.vn/xsmb-thu-2.html");
        urls.add("https://az24.vn/xsmb-thu-3.html");
        urls.add("https://az24.vn/xsmb-thu-4.html");
        urls.add("https://az24.vn/xsmb-thu-5.html");
        urls.add("https://az24.vn/xsmb-thu-6.html");
        urls.add("https://az24.vn/xsmb-thu-7.html");
        urls.add("https://az24.vn/xsmb-chu-nhat.html");


        XSMBCrawler.crawlMultiple(this, urls, new OnMultiCrawlResultListener() {

            @Override
            public void onSuccess(ArrayList<LotteryPage> result) {

                pages = result;

                runOnUiThread(() -> {

                    XSMBPagerAdapter adapter =
                            new XSMBPagerAdapter(
                                    XSMBActivity.this,
                                    pages);

                    viewPager.setAdapter(adapter);

                    int last = adapter.getItemCount() - 1;

                    viewPager.setCurrentItem(last, false);

                    txtDate.setText(
                             pages.get(last).date);

                });

            }

            @Override
            public void onError(Exception e) {

                Log.e(TAG, "====== Crawl Failed ======");
                Log.e(TAG, "Message: " + e.getMessage(), e);

            }
        });
//        txtDate.setText("📅 Kết quả XSMB ngày " + pages.get(position).date);

//        String[] dates = {
//                "2026-06-28",
//                "2026-06-27",
//                "2026-06-26",
//                "2026-06-28",
//                "2026-06-27",
//                "2026-06-26",
//                "2026-06-26"
//
//        };

        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {

                    @Override
                    public void onPageSelected(int position) {

                        if (position < pages.size()) {

                            txtDate.setText(
//
                                             pages.get(position).date);

                        }

                    }

                });

    }





}