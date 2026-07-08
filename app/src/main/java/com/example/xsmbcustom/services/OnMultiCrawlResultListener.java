package com.example.xsmbcustom.services;

import com.example.xsmbcustom.model.LotteryPage;
import com.example.xsmbcustom.model.LotteryResult;

import java.util.ArrayList;

public interface OnMultiCrawlResultListener {
    void onSuccess(ArrayList<LotteryPage> pages);

    void onError(Exception e);
}
