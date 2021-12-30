//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package me.thef1xer.gateclient.modules.render;

import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ViewModel
extends Module {
    public static final ViewModel INSTANCE = new ViewModel();
    public Minecraft mc = Minecraft.getMinecraft();
    private Entity target;
    private String hef;
    private Entity focusTarget;
    private boolean checks = false;
    private boolean old = false;
    private String enter;
    private int x;
    private int y;
    private final FontRenderer fr;
    public final FloatSetting rightx;
    public final FloatSetting righty;
    public final FloatSetting rightz;
    public final FloatSetting leftx;
    public final FloatSetting lefty;
    public final FloatSetting leftz;

    public ViewModel() {
        super("ViewModel", "viewmodel", Module.ModuleCategory.RENDER);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.rightx = new FloatSetting("rightx", "rightx", -2.0f, -2.0f, 2.0f);
        this.righty = new FloatSetting("righty", "righty", -2.0f, -2.0f, 2.0f);
        this.rightz = new FloatSetting("rightz", "rightz", -2.0f, -2.0f, 2.0f);
        this.leftx = new FloatSetting("leftx", "leftx", -2.0f, -2.0f, 2.0f);
        this.lefty = new FloatSetting("lefty", "lefty", -2.0f, -2.0f, 2.0f);
        this.leftz = new FloatSetting("leftz", "leftz", -2.0f, -2.0f, 2.0f);
        this.addSettings(this.rightx, this.righty, this.rightz, this.leftx, this.lefty, this.leftz);
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onSidePerson(TickEvent.ClientTickEvent event) {
        GlStateManager.translate((float)this.rightx.getValue(), (float)this.righty.getValue(), (float)this.rightz.getValue());
    }
}

