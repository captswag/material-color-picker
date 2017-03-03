package com.pes.androidmaterialcolorpickerdialog;

import android.annotation.SuppressLint;
import android.support.annotation.IntRange;

final class ColorFormatHelper {

    /**
     * Checks whether the specified value is between (including bounds) 0 and 255
     *
     * @param colorValue Color value
     * @return Specified input value if between 0 and 255, otherwise 0
     */
    static int assertColorValueInRange(int colorValue) {
        return ((0 <= colorValue) && (colorValue <= 255)) ? colorValue : 0;
    }

    /**
     * Left-pads the specified color value with 0 to 2 spaces, depending on the color value length
     *
     * @param colorValue Color value
     * @return Left-padded (with Strings) color value as a String
     */
    @SuppressLint("DefaultLocale")
    static String leftPadColorValue(@IntRange(from = 0, to = 255) int colorValue) {
        return String.format("%3d", colorValue);
    }

}
