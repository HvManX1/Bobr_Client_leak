//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package ru.terrar.bobr.modules.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import ru.terrar.bobr.bobr;
import ru.terrar.bobr.modules.Module;

public class ClickGuiModule
extends Module {
    public static final ClickGuiModule INSTANCE = new ClickGuiModule();

    public ClickGuiModule() {
        super("Click GUI", "clickgui", 54, Module.ModuleCategory.HUD);
    }

    @Override
    public void onEnable() {
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)bobr.getGate().guiManager.CLICK_GUI);
        super.onEnable();
    }
}

