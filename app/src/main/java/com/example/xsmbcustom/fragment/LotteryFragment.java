package com.example.xsmbcustom.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.xsmbcustom.R;
import com.example.xsmbcustom.model.LotteryResult;

import java.util.ArrayList;

public class LotteryFragment extends Fragment {

    private LinearLayout layoutResult;
    private ArrayList<LotteryResult> data;
    private TableLayout tableHeadTail;
    private static final int MODE_FULL = 0;
    private static final int MODE_2 = 2;
    private static final int MODE_3 = 3;

    private int currentMode = MODE_FULL;

    private RadioGroup rgView;

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
        tableHeadTail = view.findViewById(R.id.tableHeadTail);

        rgView = view.findViewById(R.id.rgView);

        createBoard();
        createHeader();
        rgView.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.rbFull) {

                currentMode = MODE_FULL;

            } else if (checkedId == R.id.rb2) {

                currentMode = MODE_2;

            } else if (checkedId == R.id.rb3) {

                currentMode = MODE_3;

            }

            refreshBoard();

        });

        addHeadTail("0","1,4,7,6","3,6","0");
        addHeadTail("1","9,1,4","0,1,4","1");
        addHeadTail("2","7","5,4","2");
        addHeadTail("3","0,6","4,8,4,9,7","3");
        addHeadTail("4","9,3,2,3,9,1","0,5,8,1","4");
        addHeadTail("5","2,4,5","8,5","5");
        addHeadTail("6","0","9,0,3","6");
        addHeadTail("7","3","2,0","7");
        addHeadTail("8","3,4,5","9","8");
        addHeadTail("9","8,6,3","4,1,4","9");

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



        FrameLayout prizeContainer = new FrameLayout(context);

        LinearLayout.LayoutParams containerLp =
                new LinearLayout.LayoutParams(
                        dp(58),
                        LinearLayout.LayoutParams.MATCH_PARENT);

        prizeContainer.setLayoutParams(containerLp);

        TextView prize = new TextView(context);

        FrameLayout.LayoutParams prizeLp =
                new FrameLayout.LayoutParams(
                        dp(42),
                        dp(28));

        prizeLp.gravity = Gravity.CENTER;

        prize.setLayoutParams(prizeLp);

        prize.setText(row.prize);
        prize.setGravity(Gravity.CENTER);
        prize.setTextColor(Color.WHITE);
        prize.setBackgroundResource(R.drawable.bg_prize);

        prizeContainer.addView(prize);

        line.addView(prizeContainer);


        line.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout numberLayout = new LinearLayout(context);
        numberLayout.setOrientation(LinearLayout.VERTICAL);
        numberLayout.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1));

        int perRow = row.column;

        for (int i = 0; i < row.numbers.size(); i += perRow) {

            LinearLayout lineNumber = new LinearLayout(context);
            lineNumber.setOrientation(LinearLayout.HORIZONTAL);
            lineNumber.setGravity(Gravity.CENTER);

            lineNumber.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            for (int j = i; j < Math.min(i + perRow, row.numbers.size()); j++) {

                TextView tv = new TextView(context);

                tv.setText(formatNumber(row.numbers.get(j)));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(16);
                tv.setBackgroundResource(R.drawable.bg_cell);

                LinearLayout.LayoutParams lp =
                        new LinearLayout.LayoutParams(
                                0,
                                dp(46),
                                1);

                lp.setMargins(1,1,1,1);

                tv.setLayoutParams(lp);

                lineNumber.addView(tv);
            }

            numberLayout.addView(lineNumber);
        }

        line.addView(numberLayout);
        layoutResult.addView(line);

    }

    private void createHeader(){

        TableRow row = new TableRow(getContext());

        row.addView(createHeaderText("Đầu"));
        row.addView(createHeaderText("Đuôi"));
        row.addView(createHeaderText("Đầu"));
        row.addView(createHeaderText("Đuôi"));

        tableHeadTail.addView(row);

    }
    private void addHeadTail(String leftHead,
                             String leftTail,
                             String rightHead,
                             String rightTail){

        TableRow row = new TableRow(getContext());

        row.addView(createCell(leftHead,true));
        row.addView(createCell(leftTail,false));
        row.addView(createCell(rightHead,false));
        row.addView(createCell(rightTail,true));

        tableHeadTail.addView(row);

    }
    private TextView createCell(String text,boolean red){

        TextView tv = new TextView(getContext());

        tv.setText(text);

        tv.setGravity(Gravity.CENTER);

        tv.setPadding(4,4,4,4);

        if(red){

            tv.setBackgroundResource(R.drawable.bg_prize);

            tv.setTextColor(Color.WHITE);
            tv.setPadding(0,0,0,0);
            tv.setIncludeFontPadding(false);
            tv.setMinHeight(0);
            tv.setMinimumHeight(0);
//            tv.setTextSize(5);

        }else{

            tv.setBackgroundResource(R.drawable.bg_cell);

        }

        return tv;

    }
    private TextView createHeaderText(String text){

        TextView tv=new TextView(getContext());

        tv.setText(text);

        tv.setGravity(Gravity.CENTER);

        tv.setTextSize(10);

        tv.setTypeface(null, Typeface.BOLD);

        tv.setPadding(4,4,4,4);

        return tv;

    }
    private String formatNumber(String number) {

        if (currentMode == MODE_FULL) {
            return number;
        }

        if (currentMode == MODE_2) {

            if (number.length() >= 2) {
                return number.substring(number.length() - 2);
            }

            return number;
        }

        if (currentMode == MODE_3) {

            if (number.length() >= 3) {
                return number.substring(number.length() - 3);
            }

            return number;
        }

        return number;
    }
    private void refreshBoard() {

        layoutResult.removeAllViews();

        createBoard();
    }
}