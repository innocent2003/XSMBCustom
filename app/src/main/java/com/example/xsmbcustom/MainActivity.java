package com.example.xsmbcustom;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import com.example.xsmbcustom.adapter.LotteryPagerAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "JSOUP_TEST";

    ViewPager2 viewPager;
    ImageView btnBack;
    TextView txtTitle;
    String thu = getTodayThu();
//    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        txtTitle = findViewById(R.id.txtTitle);
//        tabLayout = findViewById(R.id.tabLayout);

        btnBack = findViewById(R.id.btnBack);
        LotteryPagerAdapter adapter =
                new LotteryPagerAdapter(this);

        viewPager.setAdapter(adapter);


        txtTitle.setText("Trang chủ");
        btnBack.setOnClickListener(v -> {
            viewPager.setCurrentItem(0, true);
        });

        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        switch (position) {
                            case 0:
                                btnBack.setVisibility(View.INVISIBLE);
                                txtTitle.setText("Trang chủ");
                                viewPager.setUserInputEnabled(false);
                                break;
                            case 1:
                                btnBack.setVisibility(View.VISIBLE);
                                txtTitle.setText("Miền Bắc");
                                viewPager.setUserInputEnabled(true);
                                break;
                            case 2:
                                btnBack.setVisibility(View.VISIBLE);
                                txtTitle.setText("Miền Bắc");
                                viewPager.setUserInputEnabled(true);
                                break;
                            case 3:
                                btnBack.setVisibility(View.VISIBLE);
                                txtTitle.setText("Miền Bắc");
                                viewPager.setUserInputEnabled(true);
                                break;
                        }
                    }
                });
        crawlData(thu);
//        crawlTest("https://az24.vn/xsmn-sxmn-xo-so-mien-nam.html");
        crawlTest("https://az24.vn/xsmn-thu-7.html");
        crawlWithOkHttp();
        SimpleDateFormat sdf =
                new SimpleDateFormat(
                        "EEEE, dd/MM/yyyy",
                        new Locale("vi", "VN"));

        String today = sdf.format(new Date());

        Log.d("DATE_TEST", today);

    }


//    @Override
    public void openMienBac() {
        viewPager.setCurrentItem(1, true);
    }

//    @Override
//    public void openMienTrung() {
//        viewPager.setCurrentItem(2, true);
//    }
//
//    @Override
//    public void openMienNam() {
//        viewPager.setCurrentItem(3, true);
//    }

    private String getTodayThu() {

        Calendar calendar = Calendar.getInstance();

        int dayOfWeek =
                calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {

            case Calendar.MONDAY:
                return "2";

            case Calendar.TUESDAY:
                return "3";

            case Calendar.WEDNESDAY:
                return "4";

            case Calendar.THURSDAY:
                return "5";

            case Calendar.FRIDAY:
                return "6";

            case Calendar.SATURDAY:
                return "7";

            default:
                return "chu-nhat";
        }
    }
    private void crawlData(String thu) {

        new Thread(() -> {

            try {

                String url = "https://az24.vn/xsmb-"+thu+".html";

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .timeout(10000)
                        .get();

//                Log.d(TAG, "Title = " + doc.title());

                Element table = doc.selectFirst(
                        "table.kqmb.colgiai.extendable"
                );

                if (table == null) {

                    Log.e(TAG, "Khong tim thay bang");
                    return;
                }

                Elements rows = table.select("tr");

                for (Element row : rows) {

                    Element txtGiai =
                            row.selectFirst("td.txt-giai");

                    Element vGiai =
                            row.selectFirst("td.v-giai");

                    if (txtGiai == null || vGiai == null) {
                        continue;
                    }

                    String tenGiai = txtGiai.text();

                    Elements spans =
                            vGiai.select("span");

                    StringBuilder ketQua =
                            new StringBuilder();

                    for (Element span : spans) {

                        ketQua.append(span.text())
                                .append(" ");
                    }

                    Log.d(
                            TAG,
                            "Giai = "
                                    + tenGiai
                                    + " | KetQua = "
                                    + ketQua.toString().trim()
                    );
                }

            } catch (Exception e) {

                Log.e(TAG,
                        "Loi crawl: "
                                + e.getMessage(),
                        e);
            }

        }).start();
    }

    private void crawlTest(String url) {

        new Thread(() -> {

            try {

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36")
                        .header("Accept",
                                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                        .header("Accept-Language",
                                "vi-VN,vi;q=0.9,en-US;q=0.8")
                        .build();

                try (Response response = client.newCall(request).execute()) {

                    if (!response.isSuccessful()) {

                        Log.e("XSMN", "HTTP Code = " + response.code());
                        return;
                    }

                    String html = response.body().string();

                    // Chỉ khác Jsoup.connect() ở dòng này
                    Document doc = Jsoup.parse(html);

                    // ======================
                    // TỪ ĐÂY GIỮ NGUYÊN
                    // ======================

                    Element table = doc.selectFirst(
                            "table.colfourcity.colgiai.extendable, " +
                                    "table.colthreecity.colgiai.extendable");

                    if (table == null) {

                        Log.e("XSMN", "Table not found");
                        return;
                    }

                    for (Element row : table.select("tr")) {

                        Log.d("ROW_CLASS",
                                row.className());

                        if (row.select("th").size() > 0) {

                            for (Element th : row.select("th")) {

                                Log.d("PROVINCE",
                                        th.text());
                            }

                            continue;
                        }

                        Elements tds = row.select("> td");

                        if (tds.isEmpty()) {
                            continue;
                        }

                        String prize = tds.get(0).text();

                        Log.d("PRIZE", prize);

                        for (int i = 1; i < tds.size(); i++) {

                            Element td = tds.get(i);

                            Elements divs = td.select("div");

                            if (!divs.isEmpty()) {

                                StringBuilder sb = new StringBuilder();

                                for (Element div : divs) {

                                    sb.append(div.text())
                                            .append(" ");
                                }

                                Log.d(
                                        "RESULT",
                                        "Cot " + i + " = "
                                                + sb.toString().trim()
                                );

                            } else {

                                Log.d(
                                        "RESULT",
                                        "Cot " + i + " = "
                                                + td.text()
                                );
                            }
                        }
                    }

                }

            } catch (Exception e) {

                Log.e("XSMN", "Error", e);
            }

        }).start();
    }
    //    private void crawlTest(String url) {
//
//        new Thread(() -> {
//
//            try {
//
//                Document doc = Jsoup.connect(url)
//                        .userAgent("Mozilla/5.0")
//                        .timeout(15000)
//                        .get();
//
//                Element table = doc.selectFirst(
//                        "table.colfourcity.colgiai.extendable, " +
//                                "table.colthreecity.colgiai.extendable");
//
//                if (table == null) {
//
//                    Log.e("XSMN",
//                            "Table not found");
//
//                    return;
//                }
//
//                for (Element row : table.select("tr")) {
//
//                    Log.d("ROW_CLASS",
//                            row.className());
//
//                    // Header chứa tên tỉnh
//                    if (row.select("th").size() > 0) {
//
//                        for (Element th : row.select("th")) {
//
//                            Log.d("PROVINCE",
//                                    th.text());
//                        }
//
//                        continue;
//                    }
//
//                    Elements tds =
//                            row.select("> td");
//
//                    if (tds.isEmpty()) {
//                        continue;
//                    }
//
//                    String prize =
//                            tds.get(0).text();
//
//                    Log.d("PRIZE",
//                            prize);
//
//                    for (int i = 1;
//                         i < tds.size();
//                         i++) {
//
//                        Element td =
//                                tds.get(i);
//
//                        Elements divs =
//                                td.select("div");
//
//                        if (!divs.isEmpty()) {
//
//                            StringBuilder sb =
//                                    new StringBuilder();
//
//                            for (Element div : divs) {
//
//                                sb.append(div.text())
//                                        .append(" ");
//                            }
//
//                            Log.d(
//                                    "RESULT",
//                                    "Cot " + i
//                                            + " = "
//                                            + sb.toString()
//                            );
//
//                        } else {
//
//                            Log.d(
//                                    "RESULT",
//                                    "Cot " + i
//                                            + " = "
//                                            + td.text()
//                            );
//                        }
//                    }
//                }
//
//            } catch (Exception e) {
//
//                Log.e("XSMN",
//                        "Error",
//                        e);
//            }
//
//        }).start();
//    }
    private void crawlWithOkHttp() {

        new Thread(() -> {

            try {

                Log.d("OKHTTP", "1. Start");

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build();

                Request request = new Request.Builder()
                        .url("https://az24.vn/xsmn-thu-7.html")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36")
                        .header("Accept", "text/html")
                        .header("Accept-Language", "vi-VN,vi;q=0.9")
                        .build();

                Log.d("OKHTTP", "2. Before execute");

                Response response = client.newCall(request).execute();

                Log.d("OKHTTP", "3. After execute");

                Log.d("OKHTTP", "HTTP Code = " + response.code());

                if (response.body() == null) {
                    Log.e("OKHTTP", "Body is NULL");
                    return;
                }

                String html = response.body().string();

                Log.d("OKHTTP", "HTML Length = " + html.length());

                Log.d("OKHTTP", html.substring(0, Math.min(500, html.length())));

                Document doc = Jsoup.parse(html);

                Log.d("OKHTTP", "Title = " + doc.title());

            } catch (Exception e) {

                Log.e("OKHTTP", "Exception", e);

            }

        }).start();
    }


}