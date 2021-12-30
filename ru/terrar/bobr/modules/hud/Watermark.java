//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 */
package ru.terrar.bobr.modules.hud;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import ru.terrar.bobr.bobr;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.util.ColorUtil;

public class Watermark
extends Module {
    public static final Watermark INSTANCE = new Watermark();
    public Minecraft mc = Minecraft.getMinecraft();
    private Entity target;
    private String hef;
    private Entity focusTarget;
    private boolean checks = false;
    private boolean old = false;
    private String enter;
    private final FontRenderer fr;
    private final ResourceLocation logo;

    public Watermark() {
        super("Watermark", "watermark", Module.ModuleCategory.HUD);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.logo = new ResourceLocation("bobr", "textures/bobr.png");
    }

    public void drawWatermark() throws IOException {
        int[] rainbow = ColorUtil.getRainbow(5, 0.0f);
        int rainbowHex = ColorUtil.RGBtoHex(rainbow[0], rainbow[1], rainbow[2]);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)5.0f, (float)5.0f, (float)0.0f);
        GlStateManager.scale((float)1.5f, (float)1.5f, (float)1.0f);
        int nameEnd = this.fr.drawStringWithShadow("Bobr Client", 0.0f, 0.0f, rainbowHex);
        GlStateManager.scale((float)0.6666667f, (float)0.6666667f, (float)1.0f);
        GlStateManager.translate((double)(1.5 * (double)nameEnd), (double)4.0, (double)0.0);
        if (bobr.getGate().old) {
            this.fr.drawStringWithShadow("0.0.9 - old", 0.0f, 0.0f, 0x909090);
        } else {
            this.fr.drawStringWithShadow("0.0.9", 0.0f, 0.0f, 0x909090);
        }
        GlStateManager.popMatrix();
    }
}

