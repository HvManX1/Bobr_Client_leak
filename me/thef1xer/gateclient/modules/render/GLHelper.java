/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.thef1xer.gateclient.modules.render;

import java.awt.Color;
import java.awt.Rectangle;
import org.lwjgl.opengl.GL11;

public class GLHelper {
    public static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void drawBorderedRect(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        GLHelper.enableGL2D();
        GLHelper.glColor(n);
        GLHelper.drawRect(f + f5, f2 + f5, f3 - f5, f4 - f5);
        GLHelper.glColor(n2);
        GLHelper.drawRect(f + f5, f2, f3 - f5, f2 + f5);
        GLHelper.drawRect(f, f2, f + f5, f4);
        GLHelper.drawRect(f3 - f5, f2, f3, f4);
        GLHelper.drawRect(f + f5, f4 - f5, f3 - f5, f4);
        GLHelper.disableGL2D();
    }

    public static void drawBorderedRect(float f, float f2, float f3, float f4, int n, int n2) {
        GLHelper.enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GLHelper.drawVLine(f *= 2.0f, f2 *= 2.0f, f4 *= 2.0f, n2);
        GLHelper.drawVLine((f3 *= 2.0f) - 1.0f, f2, f4, n2);
        GLHelper.drawHLine(f, f3 - 1.0f, f2, n2);
        GLHelper.drawHLine(f, f3 - 2.0f, f4 - 1.0f, n2);
        GLHelper.drawRect(f + 1.0f, f2 + 1.0f, f3 - 1.0f, f4 - 1.0f, n);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        GLHelper.disableGL2D();
    }

    public static void drawGradientRect(float f, float f2, float f3, float f4, int n, int n2) {
        GLHelper.enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        GLHelper.glColor(n);
        GL11.glVertex2f((float)f, (float)f4);
        GL11.glVertex2f((float)f3, (float)f4);
        GLHelper.glColor(n2);
        GL11.glVertex2f((float)f3, (float)f2);
        GL11.glVertex2f((float)f, (float)f2);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        GLHelper.disableGL2D();
    }

    public static void drawHLine(float f, float f2, float f3, int n) {
        if (f2 < f) {
            float f4 = f;
            f = f2;
            f2 = f4;
        }
        GLHelper.drawRect(f, f3, f2 + 1.0f, f3 + 1.0f, n);
    }

    public static void drawHLine(float f, float f2, float f3, int n, int n2) {
        if (f2 < f) {
            float f4 = f;
            f = f2;
            f2 = f4;
        }
        GLHelper.drawGradientRect(f, f3, f2 + 1.0f, f3 + 1.0f, n, n2);
    }

    public static void drawRect(float f, float f2, float f3, float f4, int n) {
        GLHelper.enableGL2D();
        GLHelper.glColor(n);
        GLHelper.drawRect(f, f2, f3, f4);
        GLHelper.disableGL2D();
    }

    public static void drawRect(Rectangle rectangle, int n) {
        GLHelper.drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, n);
    }

    public static void drawRect(float f, float f2, float f3, float f4) {
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)f, (float)f4);
        GL11.glVertex2f((float)f3, (float)f4);
        GL11.glVertex2f((float)f3, (float)f2);
        GL11.glVertex2f((float)f, (float)f2);
        GL11.glEnd();
    }

    public static void drawRect(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        GLHelper.enableGL2D();
        GL11.glColor4f((float)f5, (float)f6, (float)f7, (float)f8);
        GLHelper.drawRect(f, f2, f3, f4);
        GLHelper.disableGL2D();
    }

    public static void drawVLine(float f, float f2, float f3, int n) {
        if (f3 < f2) {
            float f4 = f2;
            f2 = f3;
            f3 = f4;
        }
        GLHelper.drawRect(f, f2 + 1.0f, f + 1.0f, f3, n);
    }

    public static void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void glColor(float f, int n, int n2, int n3) {
        float f2 = 0.003921569f * (float)n;
        float f3 = 0.003921569f * (float)n2;
        float f4 = 0.003921569f * (float)n3;
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
    }

    public static void glColor(int n) {
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
    }

    public static void glColor(Color color) {
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }
}

