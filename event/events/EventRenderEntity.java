/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.entity.RenderLivingBase
 *  net.minecraft.entity.EntityLivingBase
 */
package event.events;

import event.Event;
import javax.annotation.Nullable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderEntity
extends Event {
    RenderLivingBase renderer;
    EntityLivingBase entity;
    ModelBase model;
    double x;
    double y;
    double z;
    float entityYaw;
    float partialTicks;

    public EventRenderEntity(@Nullable RenderLivingBase renderer, EntityLivingBase entity, @Nullable ModelBase model, double x, double y, double z, float entityYaw, float partialTicks) {
        this.renderer = renderer;
        this.entity = entity;
        this.model = model;
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityYaw = entityYaw;
        this.partialTicks = partialTicks;
    }

    public RenderLivingBase getRenderer() {
        return this.renderer;
    }

    public double getZ() {
        return this.z;
    }

    public EntityLivingBase getEntity() {
        return this.entity;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public ModelBase getModel() {
        return this.model;
    }

    public float getEntityYaw() {
        return this.entityYaw;
    }

    @Override
    public float getPartialTicks() {
        return this.partialTicks;
    }
}

