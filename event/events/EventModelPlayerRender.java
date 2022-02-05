/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.entity.Entity
 */
package event.events;

import event.Event;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class EventModelPlayerRender
extends Event {
    public ModelBase modelBase;
    public Entity entity;
    public float limbSwing;
    public float limbSwingAmount;
    public float ageInTicks;
    public float netHeadYaw;
    public float headPitch;
    public float scaleFactor;

    public EventModelPlayerRender(ModelBase modelBaseIn, Entity entityIn, float limbSwingIn, float limbSwingAmountIn, float ageInTicksIn, float netHeadYawIn, float headPitchIn, float scaleFactorIn) {
        this.modelBase = modelBaseIn;
        this.entity = entityIn;
        this.limbSwing = limbSwingIn;
        this.limbSwingAmount = limbSwingAmountIn;
        this.ageInTicks = ageInTicksIn;
        this.netHeadYaw = netHeadYawIn;
        this.headPitch = headPitchIn;
        this.scaleFactor = scaleFactorIn;
    }
}

