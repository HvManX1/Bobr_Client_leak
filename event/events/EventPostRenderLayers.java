/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.entity.RenderLivingBase
 *  net.minecraft.entity.EntityLivingBase
 */
package event.events;

import event.Event;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;

public class EventPostRenderLayers
extends Event {
    public RenderLivingBase renderer;
    public ModelBase modelBase;
    public EntityLivingBase entity;
    public float limbSwing;
    public float limbSwingAmount;
    public float partialTicks;
    public float ageInTicks;
    public float netHeadYaw;
    public float headPitch;
    public float scaleIn;

    public RenderLivingBase getRenderer() {
        return this.renderer;
    }

    public EventPostRenderLayers(RenderLivingBase renderer, ModelBase modelBase, EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) {
        this.renderer = renderer;
        this.modelBase = modelBase;
        this.entity = entitylivingbaseIn;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.partialTicks = partialTicks;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
        this.scaleIn = scaleIn;
    }

    public ModelBase getModelBase() {
        return this.modelBase;
    }

    public EntityLivingBase getEntitylivingbaseIn() {
        return this.entity;
    }

    public float getLimbSwing() {
        return this.limbSwing;
    }

    public float getLimbSwingAmount() {
        return this.limbSwingAmount;
    }

    @Override
    public float getPartialTicks() {
        return this.partialTicks;
    }

    public float getAgeInTicks() {
        return this.ageInTicks;
    }

    public float getNetHeadYaw() {
        return this.netHeadYaw;
    }

    public float getHeadPitch() {
        return this.headPitch;
    }

    public float getScaleIn() {
        return this.scaleIn;
    }
}

