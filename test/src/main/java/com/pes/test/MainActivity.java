package com.pes.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.OnColorSelected;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ColorPicker cp = new ColorPicker(MainActivity.this, 255, 127, 123, 67);

        setContentView(R.layout.activity_main);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                cp.show();
                /*final Button okColor = (Button)cp.findViewById(R.id.okColorButton);

                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Log.d("Red", Integer.toString(cp.getRed()));
                        Log.d("Green", Integer.toString(cp.getGreen()));
                        Log.d("Blue", Integer.toString(cp.getBlue()));

                        cp.dismiss();
                    }
                });*/

            }
        });

        cp.setOnColorSelected(new OnColorSelected() {
            @Override
            public void returnColor(int col) {
                Log.d("Red", Integer.toString(Color.red(col)));
                Log.d("Green", Integer.toString(Color.green(col)));
                Log.d("Blue", Integer.toString(Color.blue(col)));
            }
        });


    }
}
