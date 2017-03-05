package com.pes.androidmaterialcolorpickerdialog;

import junit.framework.TestCase;

import static com.pes.androidmaterialcolorpickerdialog.ColorFormatHelper.assertColorValueInRange;
import static com.pes.androidmaterialcolorpickerdialog.ColorFormatHelper.formatColorValues;

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

    public void testFormatColorValues_RgbValues() throws Exception {
        assertEquals("9ACD32", formatColorValues(154, 205, 50));
        assertEquals("000000", formatColorValues(0, 0, 0));
        assertEquals("FFFFFF", formatColorValues(255, 255, 255));

        assertEquals("000000", formatColorValues(256, -123, Integer.MAX_VALUE));
        assertEquals("00FF00", formatColorValues(256, 255, 256));
    }

    public void testFormatColorValues_ArgbValues() throws Exception {
        assertEquals("429ACD32", formatColorValues(66, 154, 205, 50));
        assertEquals("00000000", formatColorValues(0, 0, 0, 0));
        assertEquals("FFFFFFFF", formatColorValues(255, 255, 255, 255));

        assertEquals("00000000", formatColorValues(Integer.MIN_VALUE, 256, -42, Integer.MAX_VALUE));
        assertEquals("FF00FF00", formatColorValues(255, 256, 255, 256));
    }
}