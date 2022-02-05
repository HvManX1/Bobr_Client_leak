//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScaledResolution
 */
package de.vinii.management.ui.clickgui;

import de.vinii.management.ui.clickgui.Panel;
import de.vinii.management.ui.clickgui.components.Frame;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ClickGui
extends GuiScreen {
    public static int compID = 0;
    private ArrayList<Frame> frames = new ArrayList();
    private final FontRenderer fr;

    public ClickGui() {
        this.fr = Minecraft.getMinecraft().fontRenderer;
        compID = 0;
    }

    protected void addFrame(Frame frame) {
        if (!this.frames.contains(frame)) {
            this.frames.add(frame);
        }
    }

    protected ArrayList<Frame> getFrames() {
        return this.frames;
    }

    public void initGui() {
        for (Frame frame : this.frames) {
            frame.initialize();
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame frame : this.frames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (Frame frame : this.frames) {
            frame.keyTyped(keyCode, typedChar);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sR = new ScaledResolution(this.mc);
        this.fr.drawString("coded by zTerrarxd_#4729", 2, sR.getScaledHeight() - this.fr.FONT_HEIGHT, Panel.fontColor);
        for (Frame frame : this.frames) {
            frame.render(mouseX, mouseY);
        }
    }
}

