/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.settings.impl;

import me.thef1xer.gateclient.settings.Setting;

public class EnumSetting
extends Setting {
    private final Enum<?>[] values;
    private Enum<?> currentValue;

    public EnumSetting(String name, String id, Enum<?>[] values, Enum<?> currentValue) {
        super(name, id);
        this.values = values;
        this.currentValue = currentValue;
    }

    public Enum<?>[] getValues() {
        return this.values;
    }

    public Enum<?> getCurrentValue() {
        return this.currentValue;
    }

    public void setCurrentValue(Enum<?> currentValue) {
        this.currentValue = currentValue;
    }

    public boolean setValueFromName(String name) {
        for (Enum<?> value : this.values) {
            if (!name.equalsIgnoreCase(value.toString())) continue;
            this.setCurrentValue(value);
            return true;
        }
        return false;
    }

    public String getCurrentValueName() {
        return this.currentValue.toString();
    }
}

