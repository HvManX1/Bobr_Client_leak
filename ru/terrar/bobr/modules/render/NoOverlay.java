/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.GuiIngameForge
 */
package ru.terrar.bobr.modules.render;

import net.minecraftforge.client.GuiIngameForge;
import ru.terrar.bobr.modules.Module;

public class NoOverlay
extends Module {
    public static final NoOverlay INSTANCE = new NoOverlay();

    public NoOverlay() {
        super("No Overlay", "nooverlay", Module.ModuleCategory.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        GuiIngameForge.renderObjective = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        GuiIngameForge.renderObjective = true;
    }
}

