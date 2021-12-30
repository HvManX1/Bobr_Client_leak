/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.util;

import me.thef1xer.gateclient.util.Cancellable;
import me.thef1xer.gateclient.util.Event;

public abstract class EventCancellable
implements Event,
Cancellable {
    private boolean cancelled;

    protected EventCancellable() {
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancelled = state;
    }
}

