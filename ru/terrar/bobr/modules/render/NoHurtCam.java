//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ru.terrar.bobr.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.Setting;

public class NoHurtCam
extends Module {
    public static final NoHurtCam INSTANCE = new NoHurtCam();
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

    public NoHurtCam() {
        super("NoHurtCam", "nohurtcam", Module.ModuleCategory.RENDER);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.addSettings(new Setting[0]);
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        this.mc.player.hurtTime = 0;
        this.mc.player.hurtResistantTime = 0;
        this.mc.player.maxHurtResistantTime = 0;
        this.mc.player.maxHurtTime = 0;
    }
}

