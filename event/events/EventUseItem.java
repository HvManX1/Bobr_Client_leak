/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package event.events;

import event.Event;
import net.minecraft.entity.player.EntityPlayer;

public class EventUseItem
extends Event {
    EntityPlayer player;

    public EventUseItem(EntityPlayer entityPlayer) {
        this.player = entityPlayer;
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }
}

