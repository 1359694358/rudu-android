package com.google.android.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.google.android.app.R;

public class RoundBackgroundColorSpan extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    private Context context;
    public RoundBackgroundColorSpan( Context cxt,int bgColor, int textColor) {
        super();
        this.context=cxt;
        this.bgColor = bgColor;
        this.textColor = textColor;
    }
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return ((int)paint.measureText(text, start, end))+context.getResources().getDimensionPixelOffset(R.dimen.dimen10);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        int color1 = paint.getColor();
        paint.setColor(this.bgColor);
        canvas.drawRoundRect(new RectF(x, top+context.getResources().getDimensionPixelOffset(R.dimen.dimen2), x + ((int) paint.measureText(text, start, end))+context.getResources().getDimensionPixelOffset(R.dimen.dimen10), bottom-1), context.getResources().getDimensionPixelOffset(R.dimen.dimen6), context.getResources().getDimensionPixelOffset(R.dimen.dimen6), paint);
        paint.setColor(this.textColor);
        canvas.drawText(text, start, end, x+context.getResources().getDimensionPixelOffset(R.dimen.dimen5), y-context.getResources().getDimensionPixelOffset(R.dimen.dimen1)-0.5F, paint);
        paint.setColor(color1);
    }
}