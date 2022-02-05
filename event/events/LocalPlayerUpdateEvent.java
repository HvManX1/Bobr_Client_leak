/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 */
package event.events;

import event.Event;
import net.minecraft.entity.EntityLivingBase;

public class LocalPlayerUpdateEvent
extends Event {
    EntityLivingBase entityLivingBase;

    public LocalPlayerUpdateEvent(EntityLivingBase entity) {
        this.entityLivingBase = entity;
    }

    public EntityLivingBase getEntityLivingBase() {
        return this.entityLivingBase;
    }
}

