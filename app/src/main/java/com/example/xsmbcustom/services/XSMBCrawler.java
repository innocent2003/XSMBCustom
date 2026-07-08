package com.example.xsmbcustom.services;

import android.content.Context;
import android.util.Log;

import com.example.xsmbcustom.model.LotteryPage;
import com.example.xsmbcustom.model.LotteryResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class XSMBCrawler {

    public static void crawl(String url,
                             OnCrawlResultListener listener) {

        new Thread(() -> {

            try {

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .timeout(10000)
                        .get();

                Element table = doc.selectFirst(
                        "table.kqmb.colgiai.extendable");
                Element title = doc.selectFirst(".page-title");
                String date = "";

                if (title != null) {
                    date = title.text();
                }

                Log.d("DATE", date);

                ArrayList<LotteryResult> list =
                        new ArrayList<>();

                if (table != null) {

                    Elements rows = table.select("tr");

                    for (Element row : rows) {

                        Element txtGiai =
                                row.selectFirst("td.txt-giai");

                        Element vGiai =
                                row.selectFirst("td.v-giai");

                        if (txtGiai == null || vGiai == null)
                            continue;

                        String prize = txtGiai.text().trim();
                        if ("Mã ĐB".equalsIgnoreCase(prize)
                                || "MãDB".equalsIgnoreCase(prize)
                                || prize.startsWith("Mã")) {
                            continue;
                        }

                        List<String> numbers =
                                new ArrayList<>();

                        for (Element span :
                                vGiai.select("span")) {

                            String value = span.text().trim();

                            if (!value.isEmpty()) {
                                numbers.add(value);
                            }
                        }

                        list.add(new LotteryResult(
                                prize,
                                numbers,
                                getColumn(prize)
                        ));
                    }
                }

                listener.onSuccess(list);

            } catch (Exception e) {

                listener.onError(e);

            }

        }).start();
    }

    /**
     * Số cột hiển thị của từng giải
     */
    private static int getColumn(String prize) {

        switch (prize) {

            case "ĐB":
                return 1;

            case "G1":
                return 1;

            case "G2":
                return 2;

            case "G3":
                return 3;

            case "G4":
                return 4;

            case "G5":
                return 3;

            case "G6":
                return 3;

            case "G7":
                return 4;

            default:
                return 1;
        }
    }
    public static void crawlMultiple(
            Context context,
            List<String> urls,
            OnMultiCrawlResultListener listener) {

        new Thread(() -> {

            try {

//                ArrayList<ArrayList<LotteryResult>> pages = new ArrayList<>();
                ArrayList<LotteryPage> pages = new ArrayList<>();

                int index = 0;

                for (String url : urls) {

                    Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0")
                            .timeout(10000)
                            .get();

                    Element table = doc.selectFirst(
                            "table.kqmb.colgiai.extendable");
                    Element title = doc.selectFirst("h1");
                    String date = "";

                    if (title != null) {
                        date = title.text();
                    }

                    Log.d("DATE", date);

                    ArrayList<LotteryResult> list = new ArrayList<>();

                    if (table != null) {

                        Elements rows = table.select("tr");

                        for (Element row : rows) {

                            Element txtGiai = row.selectFirst("td.txt-giai");
                            Element vGiai = row.selectFirst("td.v-giai");

                            if (txtGiai == null || vGiai == null)
                                continue;

                            String prize = txtGiai.text().trim();
                            if ("Mã ĐB".equalsIgnoreCase(prize)
                                    || "MãDB".equalsIgnoreCase(prize)
                                    || prize.startsWith("Mã")) {
                                continue;
                            }

                            ArrayList<String> numbers = new ArrayList<>();

                            for (Element span : vGiai.select("span")) {

                                String value = span.text().trim();

                                if (!value.isEmpty()) {
                                    numbers.add(value);
                                }
                            }

                            list.add(new LotteryResult(
                                    prize,
                                    numbers,
                                    getColumn(prize)
                            ));
                        }
                    }

                    // Lưu JSON
                    JsonStorage.save(
                            context,
                            "xsmb_" + index + ".json",
                            list
                    );

//                    pages.add(list);
                    LotteryPage page = new LotteryPage(date, list);

// thêm vào ViewPager
                    pages.add(page);
                    index++;
                }

                listener.onSuccess(pages);

            } catch (Exception e) {

                listener.onError(e);

            }

        }).start();
    }

}