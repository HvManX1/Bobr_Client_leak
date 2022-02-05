/*
 * Decompiled with CFR 0.150.
 */
package event.events;

import event.Event;
import java.util.UUID;

public class EventPlayerConnect
extends Event {
    UUID uuid;
    String name;

    public EventPlayerConnect(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public static class Leave
    extends EventPlayerConnect {
        public Leave(UUID uuid, String name) {
            super(uuid, name);
        }
    }

    public static class Join
    extends EventPlayerConnect {
        public Join(UUID uuid, String name) {
            super(uuid, name);
        }
    }
}

