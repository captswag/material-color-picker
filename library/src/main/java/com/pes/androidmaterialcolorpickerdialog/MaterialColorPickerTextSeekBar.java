package com.pes.androidmaterialcolorpickerdialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by Patrick Geselbracht on 2017-03-04
 *
 * @author Patrick Geselbracht <github@pattafeufeu.de>
 * @since v1.1.0
 */
class MaterialColorPickerTextSeekBar extends AppCompatSeekBar {

    private Paint textPaint;
    private Rect textRect;

    @ColorInt
    private int textColor;

    @Dimension(unit = 2)
    private float textSize;

    private String text;

    public MaterialColorPickerTextSeekBar(Context context) {
        super(context);
        init(null);
    }

    public MaterialColorPickerTextSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MaterialColorPickerTextSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        textPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        textRect = new Rect();

        if (attrs != null) {

            final TypedArray typedArray = getContext().obtainStyledAttributes(
                    attrs, R.styleable.MaterialColorPickerTextSeekBar
            );

            try {

                textColor = typedArray.getColor(
                        R.styleable.MaterialColorPickerTextSeekBar_android_textColor,
                        0xff000000
                );

                textSize = typedArray.getDimension(
                        R.styleable.MaterialColorPickerTextSeekBar_android_textSize,
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                                18, getResources().getDisplayMetrics())
                );

                text = typedArray.getString(R.styleable
                        .MaterialColorPickerTextSeekBar_android_text);

            } finally {

                typedArray.recycle();

            }

        }

        textPaint.setColor(textColor);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        /* Measures 255 instead of the actual text because otherwise the padding would jump up
         * and down each time the text with its ascender and descenders changes.
         *
         * --
         *
         * Since we're only interested in a roundabout height depending on the text's font size
         * anyway, calculating the text bounds of this value is enough in this case.
         */
        textPaint.getTextBounds("255", 0, 3, textRect);

        setPadding(getPaddingLeft(), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) (0.6 * textRect.height()), getResources().getDisplayMetrics()),
                getPaddingRight(), getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(
                (text == null) ? String.valueOf(getProgress()) : text,
                getThumb().getBounds().left + getPaddingLeft(),
                textRect.height() + (getPaddingTop() >> 2),
                textPaint
        );

    }
}
