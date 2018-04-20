package com.pes.androidmaterialcolorpickerdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.pes.androidmaterialcolorpickerdialog.ColorFormatHelper.assertColorValueInRange;
import static com.pes.androidmaterialcolorpickerdialog.ColorFormatHelper.formatColorValues;

/**
 * This is the only class of the project. It consists in a custom dialog that shows the GUI
 * used for choosing a color using three sliders or an input field.
 *
 * @author Simone Pessotto
 */
public class ColorPicker extends Dialog implements SeekBar.OnSeekBarChangeListener {

    private final Activity activity;

    private View colorView;
    private SeekBar alphaSeekBar, redSeekBar, greenSeekBar, blueSeekBar;
    private EditText hexCode;
    private int alpha, red, green, blue;
    private ColorPickerCallback callback;

    private boolean withAlpha;
    private boolean autoclose;

    /**
     * Creator of the class. It will initialize the class with black color as default
     *
     * @param activity The reference to the activity where the color picker is called
     */
    public ColorPicker(Activity activity) {
        super(activity);

        this.activity = activity;

        if (activity instanceof ColorPickerCallback) {
            callback = (ColorPickerCallback) activity;
        }

        this.alpha = 255;
        this.red = 0;
        this.green = 0;
        this.blue = 0;

        this.withAlpha = false;
        this.autoclose = false;
    }

    /**
     * Creator of the class. It will initialize the class with the rgb color passed as default
     *
     * @param activity The reference to the activity where the color picker is called
     * @param red      Red color for RGB values (0 - 255)
     * @param green    Green color for RGB values (0 - 255)
     * @param blue     Blue color for RGB values (0 - 255)
     *
     *                 If the value of the colors it's not in the right range (0 - 255) it will
     *                 be place at 0.
     */
    public ColorPicker(Activity activity,
                       @IntRange(from = 0, to = 255) int red,
                       @IntRange(from = 0, to = 255) int green,
                       @IntRange(from = 0, to = 255) int blue) {
        this(activity);

        this.red = assertColorValueInRange(red);
        this.green = assertColorValueInRange(green);
        this.blue = assertColorValueInRange(blue);
    }

    /**
     * Creator of the class. It will initialize the class with the argb color passed as default
     *
     * @param activity The reference to the activity where the color picker is called
     * @param alpha    Alpha value (0 - 255)
     * @param red      Red color for RGB values (0 - 255)
     * @param green    Green color for RGB values (0 - 255)
     * @param blue     Blue color for RGB values (0 - 255)
     *
     *                 If the value of the colors it's not in the right range (0 - 255) it will
     *                 be place at 0.
     * @since v1.1.0
     */
    public ColorPicker(Activity activity,
                       @IntRange(from = 0, to = 255) int alpha,
                       @IntRange(from = 0, to = 255) int red,
                       @IntRange(from = 0, to = 255) int green,
                       @IntRange(from = 0, to = 255) int blue) {
        this(activity);

        this.alpha = assertColorValueInRange(alpha);
        this.red = assertColorValueInRange(red);
        this.green = assertColorValueInRange(green);
        this.blue = assertColorValueInRange(blue);

        this.withAlpha = true;
    }

    /**
     * Enable auto-dismiss for the dialog
     */
    public void enableAutoClose(){
        this.autoclose = true;
    }

    /**
     * Disable auto-dismiss for the dialog
     */
    public void disableAutoClose(){
        this.autoclose = false;
    }

    public void setCallback(ColorPickerCallback listener) {
        callback = listener;
    }

    /**
     * Simple onCreate function. Here there is the init of the GUI.
     *
     * @param savedInstanceState As usual ...
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        setContentView(R.layout.materialcolorpicker__layout_color_picker);

        colorView = findViewById(R.id.colorView);

        hexCode = (EditText) findViewById(R.id.hexCode);

        alphaSeekBar = (SeekBar) findViewById(R.id.alphaSeekBar);
        redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);

        alphaSeekBar.setOnSeekBarChangeListener(this);
        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        hexCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(withAlpha ? 8 : 6)});

        hexCode.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            updateColorView(v.getText().toString());
                            InputMethodManager imm = (InputMethodManager) activity
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(hexCode.getWindowToken(), 0);

                            return true;
                        }
                        return false;
                    }
                });

        final Button okColor = (Button) findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendColor();
            }
        });
    }

    private void initUi() {
        colorView.setBackgroundColor(getColor());

        alphaSeekBar.setProgress(alpha);
        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);

        if (!withAlpha) {
            alphaSeekBar.setVisibility(View.GONE);
        }

        hexCode.setText(withAlpha
                ? formatColorValues(alpha, red, green, blue)
                : formatColorValues(red, green, blue)
        );
    }

    private void sendColor() {
        if (callback != null)
            callback.onColorChosen(getColor());
        if(autoclose){
            dismiss();
        }
    }

    public void setColor(@ColorInt int color) {
        alpha = Color.alpha(color);
        red = Color.red(color);
        green = Color.green(color);
        blue = Color.blue(color);
    }

    /**
     * Method that synchronizes the color between the bars, the view, and the HEX code text.
     *
     * @param input HEX Code of the color.
     */
    private void updateColorView(String input) {
        try {
            final int color = Color.parseColor('#' + input);
            alpha = Color.alpha(color);
            red = Color.red(color);
            green = Color.green(color);
            blue = Color.blue(color);

            colorView.setBackgroundColor(getColor());

            alphaSeekBar.setProgress(alpha);
            redSeekBar.setProgress(red);
            greenSeekBar.setProgress(green);
            blueSeekBar.setProgress(blue);
        } catch (IllegalArgumentException ignored) {
            hexCode.setError(activity.getResources().getText(R.string.materialcolorpicker__errHex));
        }
    }

    /**
     * Method called when the user change the value of the bars. This sync the colors.
     *
     * @param seekBar  SeekBar that has changed
     * @param progress The new progress value
     * @param fromUser Whether the user is the reason for the method call
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar.getId() == R.id.alphaSeekBar) {

            alpha = progress;

        } else if (seekBar.getId() == R.id.redSeekBar) {

            red = progress;

        } else if (seekBar.getId() == R.id.greenSeekBar) {

            green = progress;

        } else if (seekBar.getId() == R.id.blueSeekBar) {

            blue = progress;

        }

        colorView.setBackgroundColor(getColor());

        //Setting the inputText hex color
        hexCode.setText(withAlpha
                ? formatColorValues(alpha, red, green, blue)
                : formatColorValues(red, green, blue)
        );

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    /**
     * Getter for the ALPHA value of the ARGB selected color
     *
     * @return ALPHA Value Integer (0 - 255)
     * @since v1.1.0
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     * Getter for the RED value of the RGB selected color
     *
     * @return RED Value Integer (0 - 255)
     */
    public int getRed() {
        return red;
    }

    /**
     * Getter for the GREEN value of the RGB selected color
     *
     * @return GREEN Value Integer (0 - 255)
     */
    public int getGreen() {
        return green;
    }


    /**
     * Getter for the BLUE value of the RGB selected color
     *
     * @return BLUE Value Integer (0 - 255)
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Getter for the color as Android Color class value.
     *
     * From Android Reference: The Color class defines methods for creating and converting color
     * ints.
     * Colors are represented as packed ints, made up of 4 bytes: alpha, red, green, blue.
     * The values are unpremultiplied, meaning any transparency is stored solely in the alpha
     * component, and not in the color components.
     *
     * @return Selected color as Android Color class value.
     */
    public int getColor() {
        return withAlpha ? Color.argb(alpha, red, green, blue) : Color.rgb(red, green, blue);
    }

    @Override
    public void show() {
        super.show();
        initUi();
    }
}