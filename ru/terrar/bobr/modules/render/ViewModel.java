//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.ItemRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ru.terrar.bobr.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.FloatSetting;

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
    public final FloatSetting armPitch;
    public final FloatSetting armYaw;

    public ViewModel() {
        super("ViewModel", "viewmodel", Module.ModuleCategory.RENDER);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.armPitch = new FloatSetting("armPitch", "armPitch", 2.0f, 0.0f, 10.0f);
        this.armYaw = new FloatSetting("armYaw", "armYaw", 2.0f, 0.0f, 10.0f);
        this.addSettings(this.armPitch, this.armYaw);
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        ItemRenderer itemRenderer = this.mc.entityRenderer.itemRenderer;
        this.mc.player.renderArmPitch = this.armPitch.getValue();
        this.mc.player.renderArmYaw = this.armYaw.getValue();
    }
}

