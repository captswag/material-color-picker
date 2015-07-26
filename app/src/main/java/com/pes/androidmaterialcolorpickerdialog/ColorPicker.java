package com.pes.androidmaterialcolorpickerdialog;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Simone Pessotto on 16/07/2015.
 */
public class ColorPicker extends Dialog implements SeekBar.OnSeekBarChangeListener {

    public Activity c;
    public Dialog d;

    View colorView;
    SeekBar redSeekBar, greenSeekBar, blueSeekBar;
    TextView redToolTip, greenToolTip, blueToolTip;
    EditText codHex;
    private int red, green, blue;
    int seekBarLeft;
    Rect thumbRect;



    public ColorPicker(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public ColorPicker(Activity a, int r, int g, int b) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.red = r;
        this.green = g;
        this.blue = b;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setContentView(R.layout.layout_color_picker);
        } else {
            setContentView(R.layout.layout_color_picker_old_android);
        }

        colorView = findViewById(R.id.colorView);

        redSeekBar = (SeekBar)findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar)findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar)findViewById(R.id.blueSeekBar);

        seekBarLeft = redSeekBar.getPaddingLeft();

        redToolTip = (TextView)findViewById(R.id.redToolTip);
        greenToolTip = (TextView)findViewById(R.id.greenToolTip);
        blueToolTip = (TextView)findViewById(R.id.blueToolTip);

        codHex = (EditText)findViewById(R.id.codHex);

        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);

        colorView.setBackgroundColor(Color.rgb(red, green, blue));

        codHex.setText(String.format("%02x%02x%02x", red, green, blue));

        codHex.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            updateColorView(v.getText().toString());
                            InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(codHex.getWindowToken(), 0);

                            return true;
                        }
                        return false;
                    }
                });


    }

    private void updateColorView(String s) {
        if(s.matches("-?[0-9a-fA-F]+")){
            int color = (int)Long.parseLong(s, 16);
            red = (color >> 16) & 0xFF;
            green = (color >> 8) & 0xFF;
            blue = (color >> 0) & 0xFF;

            colorView.setBackgroundColor(Color.rgb(red, green, blue));
            redSeekBar.setProgress(red);
            greenSeekBar.setProgress(green);
            blueSeekBar.setProgress(blue);
        }
        else{
            codHex.setError(c.getResources().getText(R.string.errHex));
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus){

        thumbRect = redSeekBar.getThumb().getBounds();

        redToolTip.setX(seekBarLeft + thumbRect.left);
        if (red<10)
            redToolTip.setText("  "+red);
        else if (red<100)
            redToolTip.setText(" "+red);
        else
            redToolTip.setText(red+"");

        thumbRect = greenSeekBar.getThumb().getBounds();

        greenToolTip.setX(seekBarLeft + thumbRect.left);
        if (green<10)
            greenToolTip.setText("  "+green);
        else if (red<100)
            greenToolTip.setText(" "+green);
        else
            greenToolTip.setText(green+"");

        thumbRect = blueSeekBar.getThumb().getBounds();

        blueToolTip.setX(seekBarLeft + thumbRect.left);
        if (blue<10)
            blueToolTip.setText("  "+blue);
        else if (blue<100)
            blueToolTip.setText(" "+blue);
        else
            blueToolTip.setText(blue+"");

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar.getId() == R.id.redSeekBar) {

            red = progress;
            thumbRect = seekBar.getThumb().getBounds();

            redToolTip.setX(seekBarLeft + thumbRect.left);

            if (progress<10)
                redToolTip.setText("  " + red);
            else if (progress<100)
                redToolTip.setText(" "+red);
            else
                redToolTip.setText(red+"");

        }
        else if (seekBar.getId() == R.id.greenSeekBar) {

            green = progress;
            thumbRect = seekBar.getThumb().getBounds();

            greenToolTip.setX(seekBar.getPaddingLeft()+thumbRect.left);
            if (progress<10)
                greenToolTip.setText("  "+green);
            else if (progress<100)
                greenToolTip.setText(" "+green);
            else
                greenToolTip.setText(green+"");

        }
        else if (seekBar.getId() == R.id.blueSeekBar) {

            blue = progress;
            thumbRect = seekBar.getThumb().getBounds();

            blueToolTip.setX(seekBarLeft + thumbRect.left);
            if (progress<10)
                blueToolTip.setText("  "+blue);
            else if (progress<100)
                blueToolTip.setText(" "+blue);
            else
                blueToolTip.setText(blue+"");

        }

        colorView.setBackgroundColor(Color.rgb(red, green, blue));

        //Setting the inputText hex color
        codHex.setText(String.format("%02x%02x%02x", red, green, blue));

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getColor(){
        return Color.rgb(red,green, blue);
    }
}