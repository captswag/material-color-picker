package com.pes.androidmaterialcolorpickerdialog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private View inputColor;

    private int colorR = 0;
    private int colorG = 0;
    private int colorB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        colorR = sharedPref.getInt("colorR", 0);
        colorG = sharedPref.getInt("colorG", 0);
        colorB = sharedPref.getInt("colorB", 0);

        inputColor = (View)findViewById(R.id.colorInputView);
        inputColor.setBackgroundColor(Color.rgb(colorR, colorG, colorB));

    }


    public void chooseColor(View v){

        final ColorPicker cp = new ColorPicker(MainActivity.this, colorR, colorG, colorB);
        cp.show();

        Button okColor = (Button)cp.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputColor.setBackgroundColor(cp.getColor());
                colorR = cp.getRed();
                colorG = cp.getGreen();
                colorB = cp.getBlue();

                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("colorR", colorR);
                editor.putInt("colorG", colorG);
                editor.putInt("colorB", colorB);
                editor.commit();

                cp.dismiss();
            }
        });
    }
}
