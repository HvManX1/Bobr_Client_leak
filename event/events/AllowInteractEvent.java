/*
 * Decompiled with CFR 0.150.
 */
package event.events;

import event.Event;

public class AllowInteractEvent
extends Event {
    public boolean usingItem;

    public AllowInteractEvent(boolean usingItem) {
        this.usingItem = usingItem;
    }
}

