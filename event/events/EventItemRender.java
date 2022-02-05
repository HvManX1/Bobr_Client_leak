/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.culling.ICamera
 *  net.minecraft.entity.Entity
 */
package event.events;

import event.Event;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

public class EventItemRender
extends Event {
    public Entity entity;
    public ICamera camera;
    public float n;

    public EventItemRender(Entity entity, ICamera camera, float n) {
        this.entity = entity;
        this.camera = camera;
        this.n = n;
    }
}

