package com.example.xsmbcustom.services;

import com.example.xsmbcustom.model.LotteryResult;

import java.util.ArrayList;

public interface OnLotteryResultListener {

    void onSuccess(ArrayList<LotteryResult> list);

    void onError(Exception e);

}
