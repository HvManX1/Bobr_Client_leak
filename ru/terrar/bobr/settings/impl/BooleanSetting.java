/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.settings.impl;

import ru.terrar.bobr.settings.Setting;

public class BooleanSetting
extends Setting {
    private boolean value;

    public BooleanSetting(String name, String id, boolean value) {
        super(name, id);
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void toggle() {
        this.setValue(!this.value);
    }
}

