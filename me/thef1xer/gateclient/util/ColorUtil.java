/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.util;

public class ColorUtil {
    public static int RGBtoHex(int red, int green, int blue) {
        return red << 16 | green << 8 | blue;
    }

    public static int[] getRainbow(int cycle, float offset) {
        int r = 0;
        int g = 0;
        int b = 0;
        long timeInCycle = (System.currentTimeMillis() - (long)Math.round(offset * 1000.0f)) % (long)(cycle * 1000);
        float portionOfCycle = 6.0f * (float)timeInCycle / (float)(cycle * 1000);
        float timeInPortion = portionOfCycle - (float)Math.floor(portionOfCycle);
        int timeInPortionRGB = Math.round(255.0f * timeInPortion);
        if (portionOfCycle < 1.0f) {
            r = 255;
            g = timeInPortionRGB;
        } else if (portionOfCycle < 2.0f) {
            r = 255 - timeInPortionRGB;
            g = 255;
        } else if (portionOfCycle < 3.0f) {
            g = 255;
            b = timeInPortionRGB;
        } else if (portionOfCycle < 4.0f) {
            g = 255 - timeInPortionRGB;
            b = 255;
        } else if (portionOfCycle < 5.0f) {
            r = timeInPortionRGB;
            b = 255;
        } else if (portionOfCycle < 6.0f) {
            r = 255;
            b = 255 - timeInPortionRGB;
        }
        return new int[]{r, g, b};
    }
}

