package com.example.xsmbcustom.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.xsmbcustom.R;
import com.example.xsmbcustom.activity.XSMBActivity;
import com.example.xsmbcustom.adapter.HomeAdapter;
import com.example.xsmbcustom.model.HomeItem;

import java.util.ArrayList;
import java.util.List;
public class HomeFragment extends Fragment {

    RecyclerView rvHomeGrid;

    LinearLayout mienBac;
    LinearLayout mienTrung;
    LinearLayout mienNam;
    LinearLayout thongKe;
    LinearLayout soiCau;
    LinearLayout doVeSo;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_home,
                container,
                false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        rvHomeGrid =
                view.findViewById(R.id.rvHomeGrid);

        mienBac =
                view.findViewById(R.id.layoutMienBac);

        mienTrung =
                view.findViewById(R.id.layoutMienTrung);

        mienNam =
                view.findViewById(R.id.layoutMienNam);
        thongKe = view.findViewById(R.id.layoutThongKe);
        soiCau = view.findViewById(R.id.layoutSoiCau);
        doVeSo = view.findViewById(R.id.layoutDoVeSo);

        rvHomeGrid.setLayoutManager(
                new GridLayoutManager(getContext(),3));

        List<HomeItem> list = new ArrayList<>();
        list.add(new HomeItem(
                "Miền Bắc DB",
                "47202",
                Color.parseColor("#A52A1A")));

        list.add(new HomeItem(
                "An Giang DB",
                "429901",
                Color.parseColor("#102D9C")));

        list.add(new HomeItem(
                "Bình Thuận",
                "063241",
                Color.parseColor("#9AA08A")));

        list.add(new HomeItem(
                "Tây Ninh DB",
                "004186",
                Color.parseColor("#7CB7C0")));

        list.add(new HomeItem(
                "Bình Định DB",
                "588617",
                Color.parseColor("#E3D06A")));

        list.add(new HomeItem(
                "Quảng Bình",
                "599761",
                Color.parseColor("#7D42C2")));

        list.add(new HomeItem(
                "Quảng Trị DB",
                "980724",
                Color.parseColor("#4A82F7")));

        list.add(new HomeItem(
                "Mega645",
                "03-09-14-17-27-32",
                Color.parseColor("#E7D8F7")));

        list.add(new HomeItem(
                "Power655",
                "08-24-25-32-37-44-03",
                Color.parseColor("#B43AE6")));

        list.add(new HomeItem(
                "Max3D",
                "145-303",
                Color.parseColor("#00FF00")));

        list.add(new HomeItem(
                "Max3D Pro",
                "452-543",
                Color.parseColor("#D8E77A")));

//        list.add(new HomeItem("Miền Bắc DB","47202"));
//        list.add(new HomeItem("An Giang DB","429901"));
//        list.add(new HomeItem("Bình Thuận","063241"));
//
//        list.add(new HomeItem("Tây Ninh","004186"));
//        list.add(new HomeItem("Bình Định","588617"));
//        list.add(new HomeItem("Quảng Bình","599761"));
//
//        list.add(new HomeItem("Quảng Trị","980724"));
//        list.add(new HomeItem("Mega645","03-09-14"));
//        list.add(new HomeItem("Power655","08-24-25"));

        rvHomeGrid.setAdapter(
                new HomeAdapter(list));


        mienBac.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), XSMBActivity.class);
            startActivity(intent);
        });

//        mienBac.setOnClickListener(v ->
//                listener.openMienBac());

//        mienTrung.setOnClickListener(v ->
//                listener.openMienTrung());
//
//        mienNam.setOnClickListener(v ->
//                listener.openMienNam());
//        mienTrung.setOnClickListener(v -> {
//            Intent intent =
//                    new Intent(getActivity(),
//                            MienTrungActivity.class);
//            startActivity(intent);
//        });
//
//        mienNam.setOnClickListener(v -> {
//            Intent intent =
//                    new Intent(getActivity(),
//                            MienNamActivity.class);
//            startActivity(intent);
//        });
//        thongKe.setOnClickListener(v -> {
//            Intent intent =
//                    new Intent(getActivity(),
//                            ThongKeActivity.class);
//            startActivity(intent);
//        });
//        soiCau.setOnClickListener(v -> {
//            Intent intent =
//                    new Intent(getActivity(),
//                            SoiCauActivity.class);
//            startActivity(intent);
//        });
//        doVeSo.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(), XSMBActivity.class);
//            startActivity(intent);
//        });
    }
}