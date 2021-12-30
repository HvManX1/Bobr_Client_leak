//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.thef1xer.gateclient.modules.movement;

import me.thef1xer.gateclient.events.PlayerMoveEvent;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Speed
extends Module {
    public static final Speed INSTANCE = new Speed();
    private final Minecraft mc = Minecraft.getMinecraft();

    public Speed() {
        super("Speed", "speed", Module.ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        super.onDisable();
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        if (this.mc.player.isSneaking() || this.mc.player.isInWater() || this.mc.player.isInLava() || this.mc.player.isOnLadder() || this.mc.player.isElytraFlying() || this.mc.player.capabilities.isFlying) {
            return;
        }
        float playerSpeed = 0.28634363f;
        double[] moveVec = PlayerUtil.getPlayerMoveVec();
        event.x = (double)playerSpeed * moveVec[0];
        event.z = (double)playerSpeed * moveVec[1];
    }
}

