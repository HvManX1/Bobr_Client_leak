/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.util;

import ru.terrar.bobr.util.Cancellable;
import ru.terrar.bobr.util.Event;

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

