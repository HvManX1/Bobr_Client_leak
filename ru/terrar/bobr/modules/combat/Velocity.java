//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.play.server.SPacketEntityVelocity
 *  net.minecraft.network.play.server.SPacketExplosion
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.terrar.bobr.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.terrar.bobr.events.ReceivePacketEvent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.FloatSetting;

public class Velocity
extends Module {
    public static final Velocity INSTANCE = new Velocity();
    public final FloatSetting horizontal = new FloatSetting("Horizontal", "horizontal", 0.0f, 0.0f, 1.0f);
    public final FloatSetting vertical = new FloatSetting("Vertical", "vertical", 0.0f, 0.0f, 1.0f);

    public Velocity() {
        super("Velocity", "velocity", Module.ModuleCategory.COMBAT);
        this.addSettings(this.horizontal, this.vertical);
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
    public void onReceivePacket(ReceivePacketEvent event) {
        SPacketEntityVelocity velPacket;
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
        if (event.getPacket() instanceof SPacketEntityVelocity && (velPacket = (SPacketEntityVelocity)event.getPacket()).getEntityID() == Minecraft.getMinecraft().player.getEntityId()) {
            if (this.horizontal.getValue() != 0.0f) {
                Minecraft.getMinecraft().player.motionX = (double)velPacket.getMotionX() * (double)this.horizontal.getValue() / 8000.0;
                Minecraft.getMinecraft().player.motionZ = (double)velPacket.getMotionZ() * (double)this.horizontal.getValue() / 8000.0;
            }
            if (this.vertical.getValue() != 0.0f) {
                Minecraft.getMinecraft().player.motionY = (double)velPacket.getMotionY() * (double)this.vertical.getValue() / 8000.0;
            }
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketExplosion) {
            SPacketExplosion expPacket = (SPacketExplosion)event.getPacket();
            Explosion explosion = new Explosion((World)Minecraft.getMinecraft().world, null, expPacket.getX(), expPacket.getY(), expPacket.getZ(), expPacket.getStrength(), expPacket.getAffectedBlockPositions());
            explosion.doExplosionB(true);
            if (this.horizontal.getValue() != 0.0f) {
                Minecraft.getMinecraft().player.motionX += (double)(expPacket.getMotionX() * this.horizontal.getValue());
                Minecraft.getMinecraft().player.motionZ += (double)(expPacket.getMotionZ() * this.horizontal.getValue());
            }
            if (this.vertical.getValue() != 0.0f) {
                Minecraft.getMinecraft().player.motionY += (double)(expPacket.getMotionY() * this.vertical.getValue());
            }
            event.setCanceled(true);
        }
    }
}

