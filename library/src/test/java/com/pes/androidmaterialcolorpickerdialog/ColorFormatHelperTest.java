package com.pes.androidmaterialcolorpickerdialog;

import junit.framework.TestCase;

import static com.pes.androidmaterialcolorpickerdialog.ColorFormatHelper.assertColorValueInRange;
import static com.pes.androidmaterialcolorpickerdialog.ColorFormatHelper.leftPadColorValue;

/**
 * Test cases for the {@link ColorFormatHelper} class
 *
 * @author PattaFeuFeu <github@pattafeufeu.de>
 */
public class ColorFormatHelperTest extends TestCase {

    public void testAssertColorValueInRange_allowedValues() throws Exception {
        for (int value = 0; value <= 255; value++) {
            assertEquals(value, assertColorValueInRange(value));
        }
    }

    public void testAssertColorValueInRange_disallowedValues() throws Exception {
        assertEquals(0, assertColorValueInRange(-1));
        assertEquals(0, assertColorValueInRange(-42));
        assertEquals(0, assertColorValueInRange(-255));
        assertEquals(0, assertColorValueInRange(-256));

        assertEquals(0, assertColorValueInRange(Integer.MAX_VALUE));
        assertEquals(0, assertColorValueInRange(Integer.MIN_VALUE));
        assertEquals(0, assertColorValueInRange(256));
        assertEquals(0, assertColorValueInRange(1024));
    }

    public void testLeftPadColorValue() throws Exception {
        assertEquals("  0", leftPadColorValue(0));
        assertEquals("  1", leftPadColorValue(1));
        assertEquals(" 10", leftPadColorValue(10));
        assertEquals("100", leftPadColorValue(100));
    }
}