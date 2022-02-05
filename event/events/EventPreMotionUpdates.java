//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package event.events;

import event.Event;
import net.minecraft.client.Minecraft;

public class EventPreMotionUpdates
extends Event {
    private boolean cancel;
    public float yaw;
    public float pitch;
    public double y;

    public EventPreMotionUpdates(float yaw, float pitch, double y) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
    }

    public void setMotion(double motionX, double motionY, double motionZ) {
        Minecraft.getMinecraft().player.motionX = motionX;
        Minecraft.getMinecraft().player.motionY = motionY;
        Minecraft.getMinecraft().player.motionZ = motionZ;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public double getY() {
        return this.y;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancel = state;
    }
}

