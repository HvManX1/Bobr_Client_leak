/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.managers;

import ru.terrar.bobr.gui.clickgui.ClickGui;

public class GuiManager {
    public final ClickGui CLICK_GUI = new ClickGui();

    public void init() {
        this.CLICK_GUI.init();
    }
}

