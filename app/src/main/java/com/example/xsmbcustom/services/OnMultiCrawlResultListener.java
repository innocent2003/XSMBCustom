package com.example.xsmbcustom.services;

import com.example.xsmbcustom.model.LotteryResult;

import java.util.ArrayList;

public interface OnMultiCrawlResultListener {
    void onSuccess(ArrayList<ArrayList<LotteryResult>> pages);
    void onError(Exception e);
}
