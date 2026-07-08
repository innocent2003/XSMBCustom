package com.example.xsmbcustom.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.xsmbcustom.fragment.LotteryFragment;
import com.example.xsmbcustom.model.LotteryPage;
import com.example.xsmbcustom.model.LotteryResult;

import java.util.ArrayList;
import java.util.List;
public class XSMBPagerAdapter extends FragmentStateAdapter {

    private final ArrayList<LotteryPage> pages;

    public XSMBPagerAdapter(
            @NonNull FragmentActivity activity,
            ArrayList<LotteryPage> pages) {

        super(activity);
        this.pages = pages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return new LotteryFragment(
                pages.get(position).results
        );

    }

    @Override
    public int getItemCount() {

        return pages.size();

    }
}