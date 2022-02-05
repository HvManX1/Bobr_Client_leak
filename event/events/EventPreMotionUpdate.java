/*
 * Decompiled with CFR 0.150.
 */
package event.events;

import event.Event;

public class EventPreMotionUpdate
extends Event {
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
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean onGround() {
        return this.ground;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }
}

