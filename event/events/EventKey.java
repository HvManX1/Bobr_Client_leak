/*
 * Decompiled with CFR 0.150.
 */
package event.events;

import event.Event;

public class EventKey
extends Event {
    private int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}

