package com.anjithsasindran.materialcolorpicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ColorPickerActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    View colorView;
    SeekBar redSeekBar, greenSeekBar, blueSeekBar;
    TextView redToolTip, greenToolTip, blueToolTip;
    Button buttonSelector;
    ClipboardManager clipBoard;
    ClipData clip;
    Window window;
    Display display;
    int red, green, blue;
    Rect thumbRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_color_picker);

        display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        SharedPreferences settings = getSharedPreferences("COLOR_SETTINGS", 0);
        red = settings.getInt("RED_COLOR", 0);
        green = settings.getInt("GREEN_COLOR", 0);
        blue = settings.getInt("BLUE_COLOR", 0);

        clipBoard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        colorView = findViewById(R.id.colorView);
        window = getWindow();

        redSeekBar = (SeekBar)findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar)findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar)findViewById(R.id.blueSeekBar);

        redToolTip = (TextView)findViewById(R.id.redToolTip);
        greenToolTip = (TextView)findViewById(R.id.greenToolTip);
        blueToolTip = (TextView)findViewById(R.id.blueToolTip);

        buttonSelector = (Button)findViewById(R.id.buttonSelector);

        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);

        //Setting View, Status bar & button color & hex codes

        redToolTip.setText(red+"");
        greenToolTip.setText(green+"");
        blueToolTip.setText(blue+"");

        colorView.setBackgroundColor(Color.rgb(red, green, blue));

        if(display.getRotation()!= Surface.ROTATION_90 && display.getRotation() != Surface.ROTATION_270)
            window.setStatusBarColor(Color.rgb(red, green, blue));

        //Set's color hex on Button
        buttonSelector.setText(String.format("#%02x%02x%02x", red, green, blue));

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        thumbRect = redSeekBar.getThumb().getBounds();
        if (red<10)
            redToolTip.setX(thumbRect.exactCenterX()+redSeekBar.getPaddingLeft());
        else if (red<100)
            redToolTip.setX(thumbRect.exactCenterX()+redSeekBar.getPaddingLeft()-redToolTip.getTextSize()/3);
        else
            redToolTip.setX(thumbRect.exactCenterX()+redSeekBar.getPaddingLeft()-redToolTip.getTextSize()/2);

        thumbRect = greenSeekBar.getThumb().getBounds();
        if (green<10)
            greenToolTip.setX(thumbRect.exactCenterX()+greenSeekBar.getPaddingLeft());
        else if (red<100)
            greenToolTip.setX(thumbRect.exactCenterX()+greenSeekBar.getPaddingLeft()-greenToolTip.getTextSize()/3);
        else
            greenToolTip.setX(thumbRect.exactCenterX()+greenSeekBar.getPaddingLeft()-greenToolTip.getTextSize()/2);

        thumbRect = blueSeekBar.getThumb().getBounds();
        if (blue<10)
            blueToolTip.setX(thumbRect.exactCenterX()+blueSeekBar.getPaddingLeft());
        else if (blue<100)
            blueToolTip.setX(thumbRect.exactCenterX()+blueSeekBar.getPaddingLeft()-blueToolTip.getTextSize()/3);
        else
            blueToolTip.setX(thumbRect.exactCenterX()+blueSeekBar.getPaddingLeft()-blueToolTip.getTextSize()/2);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar.getId() == R.id.redSeekBar) {
            red = progress;
            redToolTip.setText(red+"");
            thumbRect = seekBar.getThumb().getBounds();
            if (progress<10)
                redToolTip.setX(thumbRect.exactCenterX()+seekBar.getPaddingLeft());
            else if (progress<100)
                redToolTip.setX(thumbRect.exactCenterX()+seekBar.getPaddingLeft()-redToolTip.getTextSize()/3);
            else
                redToolTip.setX(thumbRect.exactCenterX()+seekBar.getPaddingLeft()-redToolTip.getTextSize()/2);
        }
        else if (seekBar.getId() == R.id.greenSeekBar) {
            green = progress;
            greenToolTip.setText(green+"");
            thumbRect = seekBar.getThumb().getBounds();
            if (progress<10)
                greenToolTip.setX(thumbRect.exactCenterX()+seekBar.getPaddingLeft());
            else if (progress<100)
                greenToolTip.setX(thumbRect.exactCenterX()+seekBar.getPaddingLeft()-greenToolTip.getTextSize()/3);
            else
                greenToolTip.setX(thumbRect.exactCenterX()+seekBar.getPaddingLeft()-greenToolTip.getTextSize()/2);
        }
        else if (seekBar.getId() == R.id.blueSeekBar) {
            blue = progress;
            blueToolTip.setText(blue+"");
            thumbRect = seekBar.getThumb().getBounds();
            if (progress<10)
                blueToolTip.setX(thumbRect.exactCenterX()+seekBar.getPaddingLeft());
            else if (progress<100)
                blueToolTip.setX(thumbRect.exactCenterX()+seekBar.getPaddingLeft()-blueToolTip.getTextSize()/3);
            else
                blueToolTip.setX(thumbRect.exactCenterX()+seekBar.getPaddingLeft()-blueToolTip.getTextSize()/2);
        }

        colorView.setBackgroundColor(Color.rgb(red, green, blue));

        if(display.getRotation() == Surface.ROTATION_0)
            window.setStatusBarColor(Color.rgb(red, green, blue));

        //Setting the button hex color
        buttonSelector.setText(String.format("#%02x%02x%02x", red, green, blue));

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    public void colorSelect(View view) {

        //Copies color to Clipboard
        clip = ClipData.newPlainText("clip", buttonSelector.getText());
        clipBoard.setPrimaryClip(clip);

        Toast.makeText(this, "Color " + buttonSelector.getText() + " copied to clipboard", Toast.LENGTH_SHORT).show();

    }

    public void showDetails(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Material Color Picker")

                .setView(R.layout.dialog)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

        TextView insideDialog = (TextView)dialog.findViewById(R.id.developer);
        insideDialog.setText(Html.fromHtml("Developed by <b>Anjith Sasindran</b>"));

        TextView designer = (TextView)dialog.findViewById(R.id.designer);
        designer.setText(Html.fromHtml("Design inspired from <b>Lucas Bonomi</b>"));

        dialog.findViewById(R.id.website).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("http://www.anjithsasindran.in")));
            }
        });

        dialog.findViewById(R.id.github).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("https://github.com/4k3R/material-color-picker")));
            }
        });

        dialog.findViewById(R.id.instagram).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Uri uri = Uri.parse("http://instagram.com/_u/anjithsasindran");
                Intent insta = new Intent(Intent.ACTION_VIEW, uri);
                insta.setPackage("com.instagram.android");

                PackageManager packageManager = getBaseContext().getPackageManager();
                List<ResolveInfo> list = packageManager.queryIntentActivities(insta, PackageManager.MATCH_DEFAULT_ONLY);

                if (list.size()>0) {
                    startActivity(insta);
                }
                else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/anjithsasindran")));
                }

            }
        });

        dialog.findViewById(R.id.dribbble).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("https://dribbble.com/shots/1858968-Material-Design-colorpicker?list=users&offset=2")));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Storing values of red, green & blue in SharedPreferences

        SharedPreferences settings = getSharedPreferences("COLOR_SETTINGS", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("RED_COLOR", redSeekBar.getProgress());
        editor.putInt("GREEN_COLOR", greenSeekBar.getProgress());
        editor.putInt("BLUE_COLOR", blueSeekBar.getProgress());
        editor.apply();
    }
}