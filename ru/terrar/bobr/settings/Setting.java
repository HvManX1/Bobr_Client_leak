/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.settings;

public abstract class Setting {
    private final String name;
    private final String id;
    private String parent;

    public Setting(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return this.parent;
    }
}

