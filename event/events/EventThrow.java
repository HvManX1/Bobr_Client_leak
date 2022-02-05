/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.projectile.EntityThrowable
 */
package event.events;

import event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;

public class EventThrow
extends Event {
    private Entity thrower;
    private EntityThrowable entity;
    private float rotation;

    public EventThrow(Entity entityLivingBase, EntityThrowable entityThrowable, float rotation) {
        this.thrower = entityLivingBase;
        this.entity = entityThrowable;
        this.rotation = rotation;
    }

    public Entity getThrower() {
        return this.thrower;
    }

    public EntityThrowable getEntity() {
        return this.entity;
    }

    public float getRotation() {
        return this.rotation;
    }
}

