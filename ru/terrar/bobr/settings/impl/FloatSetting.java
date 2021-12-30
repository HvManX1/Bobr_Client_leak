/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.settings.impl;

import ru.terrar.bobr.settings.Setting;

public class FloatSetting
extends Setting {
    private float value;
    private final float min;
    private final float max;
    private final float step;

    public FloatSetting(String name, String id, float value, float min, float max, float step) {
        super(name, id);
        this.value = value;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public FloatSetting(String name, String id, float value, float min, float max) {
        this(name, id, value, min, max, 0.1f);
    }

    public FloatSetting(String name, String id, float value) {
        this(name, id, value, 0.0f, 10.0f, 0.1f);
    }

    public float getValue() {
        return this.value;
    }

    public boolean setValue(float value) {
        if (value >= this.min && value <= this.max) {
            this.value = value;
            return true;
        }
        return false;
    }

    public boolean setValueWithStep(float value) {
        return this.setValue(this.step * (float)Math.round(value / this.step));
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }

    public float getStep() {
        return this.step;
    }
}

