//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.thef1xer.gateclient.modules.player;

import me.thef1xer.gateclient.events.PlayerIsUserEvent;
import me.thef1xer.gateclient.events.SendPacketEvent;
import me.thef1xer.gateclient.events.SetOpaqueCubeEvent;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Freecam
extends Module {
    public static final Freecam INSTANCE = new Freecam();
    public final FloatSetting verticalSpeed = new FloatSetting("Vertical Speed", "verticalspeed", 3.0f);
    public final FloatSetting horizontalSpeed = new FloatSetting("Horizontal Speed", "horizontalspeed", 3.0f);
    private int lastThirdPerson;
    public EntityOtherPlayerMP camera;
    private boolean activeThisSession = false;

    public Freecam() {
        super("Freecam", "freecam", Module.ModuleCategory.PLAYER);
        this.verticalSpeed.setParent("Speed");
        this.horizontalSpeed.setParent("Speed");
        this.addSettings(this.verticalSpeed, this.horizontalSpeed);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.activeThisSession = true;
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().world.isRemote) {
            Minecraft.getMinecraft().setRenderViewEntity((Entity)Minecraft.getMinecraft().player);
            if (this.activeThisSession) {
                Minecraft.getMinecraft().gameSettings.thirdPersonView = this.lastThirdPerson;
                Minecraft.getMinecraft().world.removeEntity((Entity)this.camera);
            }
        }
        this.camera = null;
        this.activeThisSession = false;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            this.camera = null;
            return;
        }
        if (this.camera == null) {
            this.lastThirdPerson = Minecraft.getMinecraft().gameSettings.thirdPersonView;
            this.camera = new EntityOtherPlayerMP((World)Minecraft.getMinecraft().world, Minecraft.getMinecraft().getSession().getProfile());
            Minecraft.getMinecraft().world.addEntityToWorld(333393333, (Entity)this.camera);
            this.camera.copyLocationAndAnglesFrom((Entity)Minecraft.getMinecraft().player);
            Minecraft.getMinecraft().setRenderViewEntity((Entity)this.camera);
            this.camera.noClip = true;
        }
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
        this.camera.inventory = Minecraft.getMinecraft().player.inventory;
        this.camera.setHealth(Minecraft.getMinecraft().player.getHealth());
        float forward = (Minecraft.getMinecraft().player.movementInput.forwardKeyDown ? this.horizontalSpeed.getValue() : 0.0f) - (Minecraft.getMinecraft().player.movementInput.backKeyDown ? this.horizontalSpeed.getValue() : 0.0f);
        float strafe = (Minecraft.getMinecraft().player.movementInput.leftKeyDown ? this.horizontalSpeed.getValue() : 0.0f) - (Minecraft.getMinecraft().player.movementInput.rightKeyDown ? this.horizontalSpeed.getValue() : 0.0f);
        float vertical = (Minecraft.getMinecraft().player.movementInput.jump ? this.verticalSpeed.getValue() : 0.0f) - (Minecraft.getMinecraft().player.movementInput.sneak ? this.verticalSpeed.getValue() : 0.0f);
        Vec3d vector = new Vec3d((double)strafe, (double)vertical, (double)forward).rotateYaw((float)(-Math.toRadians(this.camera.rotationYaw)));
        this.camera.setPositionAndRotationDirect(this.camera.posX + vector.x, this.camera.posY + vector.y, this.camera.posZ + vector.z, this.camera.rotationYaw, this.camera.rotationPitch, 3, false);
    }

    @SubscribeEvent
    public void onIsUser(PlayerIsUserEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onOpaqueCube(SetOpaqueCubeEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onSendPacket(SendPacketEvent event) {
        CPacketUseEntity useEntity;
        if (event.getPacket() instanceof CPacketUseEntity && (useEntity = (CPacketUseEntity)event.getPacket()).getEntityFromWorld((World)Minecraft.getMinecraft().world) == Minecraft.getMinecraft().player) {
            event.setCanceled(true);
        }
    }
}

