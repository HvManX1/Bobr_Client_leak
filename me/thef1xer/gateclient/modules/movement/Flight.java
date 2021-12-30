//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.thef1xer.gateclient.modules.movement;

import me.thef1xer.gateclient.events.PlayerMoveEvent;
import me.thef1xer.gateclient.events.UpdateWalkingPlayerEvent;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.EnumSetting;
import me.thef1xer.gateclient.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Flight
extends Module {
    public static final Flight INSTANCE = new Flight();
    public final EnumSetting mode = new EnumSetting("Mode", "mode", Mode.values(), Mode.VANILLA);
    private final Minecraft mc = Minecraft.getMinecraft();

    public Flight() {
        super("Flight", "flight", Module.ModuleCategory.MOVEMENT);
        this.addSettings(this.mode);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        if (this.mc.player != null) {
            this.mc.player.capabilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (this.mc.player != null) {
            this.mc.player.capabilities.isFlying = true;
        }
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (this.mode.getCurrentValue() == Mode.PACKET) {
            event.x = 0.0;
            event.y = 0.0;
            event.z = 0.0;
        }
    }

    @SubscribeEvent
    public void onUpdateWalking(UpdateWalkingPlayerEvent event) {
        if (this.mode.getCurrentValue() == Mode.PACKET) {
            double[] moveVec = PlayerUtil.getPlayerMoveVec();
            double speedX = moveVec[0] * 0.2;
            double speedZ = moveVec[1] * 0.2;
            double speedY = 0.0;
            if (this.mc.player.movementInput.jump) {
                speedY = 0.2;
            } else if (this.mc.player.movementInput.sneak) {
                speedY = -0.2;
            }
            this.mc.player.setPositionAndRotation(this.mc.player.posX + speedX, this.mc.player.posY + speedY, this.mc.player.posZ + speedZ, this.mc.player.rotationYaw, this.mc.player.rotationPitch);
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, this.mc.player.rotationYaw, this.mc.player.rotationPitch, this.mc.player.onGround));
            this.mc.player.setVelocity(0.0, 0.0, 0.0);
            event.setCanceled(true);
        }
    }

    public static enum Mode {
        VANILLA("Vanilla"),
        PACKET("Packet");

        private final String name;

        private Mode(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

