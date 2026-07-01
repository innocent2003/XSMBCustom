package com.example.xsmbcustom.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.xsmbcustom.R;
import com.example.xsmbcustom.model.LotteryResult;

import java.util.ArrayList;

public class LotteryFragment extends Fragment {

    private LinearLayout layoutResult;
    private ArrayList<LotteryResult> data;

    public LotteryFragment(ArrayList<LotteryResult> data) {
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_lottery,
                container,
                false);

        layoutResult = view.findViewById(R.id.layoutResult);

        createBoard();

        return view;
    }

    private void createBoard() {

        for (LotteryResult row : data) {

            addRow(row);

        }

    }
    private int dp(int value){

        return (int)(value *
                getResources()
                        .getDisplayMetrics()
                        .density);

    }
    private void addRow(LotteryResult row){

        Context context = getContext();

        LinearLayout line = new LinearLayout(context);

        line.setOrientation(LinearLayout.HORIZONTAL);

        TextView prize = new TextView(context);

        prize.setText(row.prize);

        prize.setWidth(dp(60));

        prize.setGravity(Gravity.CENTER);

        prize.setBackgroundResource(R.drawable.bg_prize);

        line.addView(prize);

        GridLayout grid = new GridLayout(context);

        grid.setColumnCount(row.column);

        grid.setLayoutParams(
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1));

        for(String number : row.numbers){

            TextView tv = new TextView(context);

            tv.setText(number);

            tv.setGravity(Gravity.CENTER);

            tv.setPadding(16,16,16,16);

            tv.setBackgroundResource(R.drawable.bg_cell);

            GridLayout.LayoutParams lp =
                    new GridLayout.LayoutParams();

            lp.width = 0;

            lp.columnSpec =
                    GridLayout.spec(GridLayout.UNDEFINED,1f);

            tv.setLayoutParams(lp);

            grid.addView(tv);

        }

        line.addView(grid);

        layoutResult.addView(line);

    }
}