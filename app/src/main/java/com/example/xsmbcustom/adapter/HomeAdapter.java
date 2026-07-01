package com.example.xsmbcustom.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.xsmbcustom.R;
import com.example.xsmbcustom.model.HomeItem;

import java.util.List;

public class HomeAdapter
        extends RecyclerView.Adapter<HomeAdapter.Holder> {

    private List<HomeItem> list;

    public HomeAdapter(List<HomeItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull Holder holder,
            int position) {

        HomeItem item = list.get(position);

        holder.txtTitle.setText(item.getTitle());
        holder.txtResult.setText(item.getResult());
        holder.layoutRoot.setBackgroundColor(
                item.getColor());
//        holder.layoutRoot.setBackgroundColor(
//                item.getColor());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        LinearLayout layoutRoot;
        TextView txtTitle, txtResult;

        public Holder(@NonNull View itemView) {
            super(itemView);

            layoutRoot =
                    itemView.findViewById(R.id.layoutRoot);

            txtTitle =
                    itemView.findViewById(R.id.txtTitle);

            txtResult =
                    itemView.findViewById(R.id.txtResult);
        }
    }
}