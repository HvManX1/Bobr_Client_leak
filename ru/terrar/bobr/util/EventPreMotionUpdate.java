//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package ru.terrar.bobr.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import ru.terrar.bobr.util.EventCancellable;

public class EventPreMotionUpdate
extends EventCancellable {
    private float yaw;
    private float pitch;
    private boolean ground;
    public double x;
    public double y;
    public double z;

    public EventPreMotionUpdate(float yaw, float pitch, boolean ground, double x, double y, double z) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft().player.rotationYawHead = yaw;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft().player.renderYawOffset = yaw;
    }

    public void setYaw1(float yaw) {
        this.yaw = yaw;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft().player.rotationYaw = yaw;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean isGround() {
        return this.ground;
    }

    public void setGround(boolean isGround) {
        this.ground = isGround;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        Minecraft.getMinecraft();
    }

    public boolean onGround() {
        return this.ground;
    }

    public static class EventSpawnPlayer
    extends EventCancellable {
        private EntityPlayer player;

        public EventSpawnPlayer(EntityPlayer player) {
            this.player = player;
        }

        public EntityPlayer getPlayer() {
            return this.player;
        }

        public void setPlayer(EntityPlayer player) {
            this.player = player;
        }
    }
}

