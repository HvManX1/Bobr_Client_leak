//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package ru.terrar.bobr.gui.clickgui;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class snoweffect {
    private static ResourceLocation snow = new ResourceLocation("ref", "textures/snow.png");

    public static void draweffect() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int y = snoweffect.generateRandomIntIntRange(0, sr.getScaledWidth());
        int x = snoweffect.generateRandomIntIntRange(0, sr.getScaledHeight());
        snoweffect.drawpoint(x, y);
    }

    public static void drawpoint(int x, int y) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)x, (double)y, (double)0.0);
        Minecraft.getMinecraft().renderEngine.bindTexture(snow);
        Gui.drawScaledCustomSizeModalRect((int)0, (int)0, (float)0.0f, (float)0.0f, (int)7, (int)7, (int)7, (int)7, (float)7.0f, (float)7.0f);
        GL11.glPopMatrix();
    }

    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}

