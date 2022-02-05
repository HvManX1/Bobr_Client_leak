/*
 * Decompiled with CFR 0.150.
 */
package event.events;

import event.Event;

public class Event3D
extends Event {
    private float partialTicks;

    public Event3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    @Override
    public float getPartialTicks() {
        return this.partialTicks;
    }
}

