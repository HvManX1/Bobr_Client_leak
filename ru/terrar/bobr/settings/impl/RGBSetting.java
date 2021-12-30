/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.settings.impl;

import ru.terrar.bobr.settings.Setting;

public class RGBSetting
extends Setting {
    private int red;
    private int green;
    private int blue;

    public RGBSetting(String name, String id, int red, int green, int blue) {
        super(name, id);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return this.red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return this.green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return this.blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}

