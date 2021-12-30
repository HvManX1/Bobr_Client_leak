//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package me.thef1xer.gateclient.modules.combat;

import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AntiAim
extends Module {
    private transient long time;
    public static final AntiAim INSTANCE = new AntiAim();
    public final FloatSetting Speed = new FloatSetting("Speed", "speed", 20.0f, 0.0f, 100.0f);
    private float rot = 0.0f;

    public AntiAim() {
        super("AntiAim", "antaim", Module.ModuleCategory.COMBAT);
        this.addSettings(this.Speed);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.time = System.currentTimeMillis();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        int pitch = (int)Minecraft.getMinecraft().player.rotationPitch;
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            this.rot += this.Speed.getValue() / 100.0f;
            if (this.rot >= 90.0f) {
                this.rot = 0.0f;
            }
            Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.rot, (float)pitch, Minecraft.getMinecraft().player.onGround));
        }
    }
}

