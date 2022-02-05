/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.ItemStack
 */
package event.events;

import event.Event;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class EventDrinkPotion
extends Event {
    EntityLivingBase entityLivingBase;
    ItemStack stack;

    public EventDrinkPotion(EntityLivingBase entityLivingBase, ItemStack itemStack) {
        this.entityLivingBase = entityLivingBase;
        this.stack = itemStack;
    }

    public EntityLivingBase getEntityLivingBase() {
        return this.entityLivingBase;
    }

    public ItemStack getStack() {
        return this.stack;
    }
}

