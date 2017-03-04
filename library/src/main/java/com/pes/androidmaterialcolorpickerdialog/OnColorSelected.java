package com.pes.androidmaterialcolorpickerdialog;

/**
 * Created by Simone Pes on 26/01/2017.
 *
 * @deprecated Use {@link ColorPickerCallback} instead
 */
public interface OnColorSelected {

    /**
     * Gets called whenever a user chooses a color from the ColorPicker, i.e., presses the
     * "Choose" button.
     *
     * @param color Color chosen
     * @deprecated Use {@link ColorPickerCallback#onColorChosen(int)} instead.
     */
    void returnColor(int color);
}
