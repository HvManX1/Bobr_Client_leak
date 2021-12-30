//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.text.TextFormatting
 */
package me.thef1xer.gateclient.modules.hud;

import me.thef1xer.gateclient.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

public class Coords
extends Module {
    public static final Coords INSTANCE = new Coords();
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr;

    public Coords() {
        super("Coords", "coords", Module.ModuleCategory.HUD);
        this.fr = this.mc.fontRenderer;
    }

    public void drawCoords(ScaledResolution sr) {
        Vec3d pos = new Vec3d((double)Math.round(this.mc.player.posX * 100.0) / 100.0, (double)Math.round(this.mc.player.posY * 100.0) / 100.0, (double)Math.round(this.mc.player.posZ * 100.0) / 100.0);
        String coords = (Object)TextFormatting.GRAY + "XYZ: " + (Object)TextFormatting.RESET + pos.x + ", " + pos.y + ", " + pos.z;
        this.fr.drawStringWithShadow(coords, 4.0f, (float)(sr.getScaledHeight() - this.fr.FONT_HEIGHT - 4), 0xFFFFFF);
        if (this.mc.player.dimension == 0) {
            Vec3d netherPos = new Vec3d((double)Math.round(this.mc.player.posX * 12.5) / 100.0, (double)Math.round(this.mc.player.posY * 100.0) / 100.0, (double)Math.round(this.mc.player.posZ * 12.5) / 100.0);
            String nether = (Object)TextFormatting.GRAY + "Nether: " + (Object)TextFormatting.RESET + netherPos.x + ", " + netherPos.y + ", " + netherPos.z;
            this.fr.drawStringWithShadow(nether, 4.0f, (float)(sr.getScaledHeight() - 2 * this.fr.FONT_HEIGHT - 4), 0xFFFFFF);
        } else if (this.mc.player.dimension == -1) {
            Vec3d OWPos = new Vec3d((double)Math.round(this.mc.player.posX * 800.0) / 100.0, (double)Math.round(this.mc.player.posY * 100.0) / 100.0, (double)Math.round(this.mc.player.posZ * 800.0) / 100.0);
            String overworld = (Object)TextFormatting.GRAY + "Overworld: " + (Object)TextFormatting.RESET + OWPos.x + ", " + OWPos.y + ", " + OWPos.z;
            this.fr.drawStringWithShadow(overworld, 4.0f, (float)(sr.getScaledHeight() - 2 * this.fr.FONT_HEIGHT - 4), 0xFFFFFF);
        }
    }
}

