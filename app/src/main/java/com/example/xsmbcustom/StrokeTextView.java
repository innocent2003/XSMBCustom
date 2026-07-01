package com.example.xsmbcustom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class StrokeTextView extends AppCompatTextView {

    public StrokeTextView(Context context) {
        super(context);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = getPaint();

        // Viền đỏ
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        setTextColor(Color.parseColor("#D50000"));
        super.onDraw(canvas);

        // Chữ trắng
        paint.setStyle(Paint.Style.FILL);
        setTextColor(Color.WHITE);
        super.onDraw(canvas);
    }
}
