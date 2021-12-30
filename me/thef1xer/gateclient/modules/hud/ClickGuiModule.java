//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package me.thef1xer.gateclient.modules.hud;

import me.thef1xer.gateclient.GateClient;
import me.thef1xer.gateclient.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ClickGuiModule
extends Module {
    public static final ClickGuiModule INSTANCE = new ClickGuiModule();

    public ClickGuiModule() {
        super("Click GUI", "clickgui", 54, Module.ModuleCategory.HUD);
    }

    @Override
    public void onEnable() {
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)GateClient.getGate().guiManager.CLICK_GUI);
        super.onEnable();
    }
}

