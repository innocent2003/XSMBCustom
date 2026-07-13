package com.example.xsmbcustom.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import com.example.xsmbcustom.MainActivity;
import com.example.xsmbcustom.R;
import com.example.xsmbcustom.adapter.LotteryAdapter;
import com.example.xsmbcustom.adapter.XSMBPagerAdapter;
import com.example.xsmbcustom.model.LotteryPage;
import com.example.xsmbcustom.model.LotteryResult;
import com.example.xsmbcustom.services.AppDatabase;
import com.example.xsmbcustom.services.JsonStorage;
import com.example.xsmbcustom.services.OnCrawlResultListener;
import com.example.xsmbcustom.services.OnMultiCrawlResultListener;
import com.example.xsmbcustom.services.XSMBCrawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        txtDate.setOnClickListener(v -> showDatePicker(txtDate));
        ArrayList<String> urls = new ArrayList<>();

//        urls.add("https://az24.vn/xsmb-sxmb-xo-so-mien-bac.html");
        urls.add("https://az24.vn/xsmb-thu-2.html");
        urls.add("https://az24.vn/xsmb-thu-3.html");
        urls.add("https://az24.vn/xsmb-thu-4.html");
        urls.add("https://az24.vn/xsmb-thu-5.html");
        urls.add("https://az24.vn/xsmb-thu-6.html");
        urls.add("https://az24.vn/xsmb-thu-7.html");
        urls.add("https://az24.vn/xsmb-chu-nhat.html");
        boolean has2012 = false;

//        for (LotteryPage page : pages) {
//            if (page.date.endsWith("2012")) {
//                has2012 = true;
//                break;
//            }
//        }
//        if(!has2012)Ơ
//        Calendar calendar = Calendar.getInstance();
//        Calendar end = (Calendar) calendar.clone();
//
//        calendar.add(Calendar.YEAR, -15);
//
//        while (!end.before(calendar)) {
//
//            String url = String.format(
//                    Locale.getDefault(),
//                    "https://az24.vn/xsmb-%d-%d-%d.html",
//                    end.get(Calendar.DAY_OF_MONTH) -1,
//                    end.get(Calendar.MONTH) + 1,
//                    end.get(Calendar.YEAR)
//            );
//
//            urls.add(url);
//
//            end.add(Calendar.DAY_OF_MONTH, -1);
//
//        }

        AppDatabase db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "xsmb.db"
        ).build();

//        new Thread(() -> {
//
//            boolean has2012 = db.lotteryDao().countByYear("2012") > 0;
//
//            if (!has2012) {
//
//                ArrayList<String> urls = new ArrayList<>();
//
//                Calendar calendar = Calendar.getInstance();
//                Calendar end = (Calendar) calendar.clone();
//
//                calendar.add(Calendar.YEAR, -15);
//
//                while (!end.before(calendar)) {
//
//                    urls.add(String.format(
//                            Locale.getDefault(),
//                            "https://az24.vn/xsmb-%d-%d-%d.html",
//                            end.get(Calendar.DAY_OF_MONTH),
//                            end.get(Calendar.MONTH) + 1,
//                            end.get(Calendar.YEAR)
//                    ));
//
//                    end.add(Calendar.DAY_OF_MONTH, -1);
//                }
//
//                XSMBCrawler.crawlMultiple(XSMBActivity.this, urls, listener);
//
//            } else {
//
//                runOnUiThread(() ->
//                        Toast.makeText(
//                                XSMBActivity.this,
//                                "Đã có dữ liệu năm 2012",
//                                Toast.LENGTH_SHORT
//                        ).show());
//
//                // Chỉ load dữ liệu từ Room/Json
//            }
//
//        }).start();

        XSMBCrawler.crawlMultiple(this, urls, new OnMultiCrawlResultListener() {

            @Override
            public void onSuccess(ArrayList<LotteryPage> result) {

//                pages = result;
                pages = JsonStorage.loadAll(XSMBActivity.this);

                SimpleDateFormat sdf =
                        new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                Collections.sort(pages, (a, b) -> {
                    try {
                        return sdf.parse(a.date).compareTo(sdf.parse(b.date));
                    } catch (Exception e) {
                        return 0;
                    }
                });

                runOnUiThread(() -> {

                    XSMBPagerAdapter adapter =
                            new XSMBPagerAdapter(
                                    XSMBActivity.this,
                                    pages);

                    viewPager.setAdapter(adapter);

                    int last = adapter.getItemCount() - 1;

                    viewPager.setCurrentItem(last, false);

                    txtDate.setText("Kết quả XSMB ngày "+
                             pages.get(last).date);

                });

            }

            @Override
            public void onError(Exception e) {

                Log.e(TAG, "====== Crawl Failed ======");
                Log.e(TAG, "Message: " + e.getMessage(), e);

            }

        });


        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {

                    @Override
                    public void onPageSelected(int position) {

                        if (position < pages.size()) {

                            txtDate.setText(
//
                                    "Kết quả XSMB ngày "+ pages.get(position).date);

                        }

                    }

                });

    }


    private void showDatePicker(TextView txtDate) {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                (view, year, month, dayOfMonth) -> {

                    String selectedDate = String.format(
                            Locale.getDefault(),
                            "%02d-%02d-%04d",
                            dayOfMonth,
                            month + 1,
                            year
                    );

                    int index = -1;

                    for (int i = 0; i < pages.size(); i++) {

                        if (selectedDate.equals(pages.get(i).date)) {
                            index = i;
                            break;
                        }
                    }

                    if (index != -1) {

                        viewPager.setCurrentItem(index, true);

                        txtDate.setText("Kết quả XSMB ngày " + selectedDate);

                    } else {

                        Toast.makeText(
                                XSMBActivity.this,
                                "Không có dữ liệu ngày " + selectedDate,
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.getDatePicker().setCalendarViewShown(false);
        dialog.getDatePicker().setSpinnersShown(true);

        dialog.setTitle("Chọn ngày");

        dialog.show();
    }
    private ArrayList<String> generateLast3YearsUrls() {

        ArrayList<String> urls = new ArrayList<>();

        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.add(Calendar.YEAR, -3);

        Calendar current = (Calendar) end.clone();

        while (!current.before(start)) {

            int day = current.get(Calendar.DAY_OF_MONTH);
            int month = current.get(Calendar.MONTH) + 1;
            int year = current.get(Calendar.YEAR);

            urls.add(String.format(
                    Locale.getDefault(),
                    "https://az24.vn/xsmb-%d-%d-%d.html",
                    day,
                    month,
                    year
            ));

            current.add(Calendar.DAY_OF_MONTH, -1);
        }

        return urls;
    }



}