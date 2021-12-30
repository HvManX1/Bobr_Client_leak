//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 */
package ru.terrar.bobr.modules.hud;

import java.io.IOException;
import java.util.Calendar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;

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
    public final BooleanSetting Snow;
    private ResourceLocation Logo;
    private ResourceLocation SnowLogo;

    public Watermark() {
        super("Watermark", "watermark", Module.ModuleCategory.HUD);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.Snow = new BooleanSetting("Snow", "Snow", true);
        this.Logo = new ResourceLocation("ref", "textures/logo.png");
        this.SnowLogo = new ResourceLocation("ref", "textures/snowlogo.png");
        this.addSettings(this.Snow);
    }

    public void drawWatermark() throws IOException {
        Calendar calendar = Calendar.getInstance();
        int Month = calendar.get(2);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)5.0f, (float)5.0f, (float)0.0f);
        if (this.Snow.getValue()) {
            this.mc.renderEngine.bindTexture(this.SnowLogo);
        } else {
            this.mc.renderEngine.bindTexture(this.Logo);
        }
        Gui.drawScaledCustomSizeModalRect((int)0, (int)0, (float)0.0f, (float)0.0f, (int)150, (int)75, (int)150, (int)75, (float)150.0f, (float)75.0f);
        GlStateManager.popMatrix();
    }
}

