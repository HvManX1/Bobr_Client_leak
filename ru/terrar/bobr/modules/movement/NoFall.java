//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.terrar.bobr.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.terrar.bobr.events.SendPacketEvent;
import ru.terrar.bobr.modules.Module;

public class NoFall
extends Module {
    public static final NoFall INSTANCE = new NoFall();

    public NoFall() {
        super("NoFall", "nofall", Module.ModuleCategory.MOVEMENT);
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
    }

    @SubscribeEvent
    public void onSendPacket(SendPacketEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (event.getPacket() instanceof CPacketPlayer && !((CPacketPlayer)event.getPacket()).isOnGround() && mc.player.fallDistance > 3.0f) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            event.setPacket((Packet<?>)new CPacketPlayer.PositionRotation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch, true));
        }
    }
}

