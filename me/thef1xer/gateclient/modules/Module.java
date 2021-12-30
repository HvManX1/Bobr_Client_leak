/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.TextFormatting
 */
package me.thef1xer.gateclient.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.thef1xer.gateclient.settings.Setting;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import me.thef1xer.gateclient.util.ChatUtil;
import net.minecraft.util.text.TextFormatting;

public class Module {
    private final String name;
    private final String id;
    private boolean enabled = false;
    private int keyBind;
    private final ModuleCategory moduleCategory;
    private final List<Setting> settings = new ArrayList<Setting>();
    public final BooleanSetting drawOnHud = new BooleanSetting("Draw on Hud", "drawonhud", true);

    public Module(String name, String id, ModuleCategory category) {
        this(name, id, 0, category);
    }

    public Module(String name, String id, int keyBind, ModuleCategory category) {
        this.name = name;
        this.id = id;
        this.keyBind = keyBind;
        this.moduleCategory = category;
        this.addSettings(this.drawOnHud);
    }

    public void onEnable() {
        ChatUtil.clientMessage(this.name + (Object)TextFormatting.GREEN + " Enable");
    }

    public void onDisable() {
        ChatUtil.clientMessage(this.name + (Object)TextFormatting.RED + " Disable");
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean set) {
        if (set) {
            this.enabled = true;
            this.onEnable();
        } else {
            this.enabled = false;
            this.onDisable();
        }
    }

    public void toggle() {
        this.setEnabled(!this.enabled);
    }

    public void addSettings(Setting ... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public int getKeyBind() {
        return this.keyBind;
    }

    public void setKeyBind(int key) {
        this.keyBind = key;
    }

    public ModuleCategory getModuleCategory() {
        return this.moduleCategory;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public static enum ModuleCategory {
        COMBAT("Combat", 15024963),
        HUD("HUD", 14431204),
        MOVEMENT("Movement", 6527487),
        PLAYER("Player", 16744497),
        RENDER("Render", 16768809);

        private final String name;
        private final int color;

        private ModuleCategory(String name, int color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return this.name;
        }

        public int getColor() {
            return this.color;
        }
    }
}

