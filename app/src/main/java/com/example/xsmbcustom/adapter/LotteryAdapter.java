package com.example.xsmbcustom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xsmbcustom.R;
import com.example.xsmbcustom.model.LotteryResult;

import java.util.List;

public class LotteryAdapter extends RecyclerView.Adapter<LotteryAdapter.ViewHolder> {

    private Context context;
    private List<LotteryResult> list;

    public LotteryAdapter(Context context, List<LotteryResult> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_result, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       LotteryResult item = list.get(position);

        holder.txtPrize.setText(item.prize);

        holder.gridNumber.removeAllViews();

        holder.gridNumber.setColumnCount(item.column);

        for (String number : item.numbers) {

            TextView tv = new TextView(context);

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 0;
            params.height = 140;

            params.columnSpec =
                    GridLayout.spec(GridLayout.UNDEFINED, 1f);

            params.setMargins(1,1,1,1);

            tv.setLayoutParams(params);

            tv.setText(number);

            tv.setGravity(Gravity.CENTER);

            tv.setTextSize(18);

            tv.setTextColor(Color.BLACK);

            tv.setBackgroundResource(R.drawable.bg_cell);

            holder.gridNumber.addView(tv);

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtPrize;

        GridLayout gridNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPrize = itemView.findViewById(R.id.txtPrize);

            gridNumber = itemView.findViewById(R.id.gridNumber);
        }
    }

}