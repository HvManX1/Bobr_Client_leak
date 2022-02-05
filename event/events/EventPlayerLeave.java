/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package event.events;

import com.mojang.authlib.GameProfile;
import event.Event;

public class EventPlayerLeave
extends Event {
    GameProfile gameProfile;

    public EventPlayerLeave(GameProfile gameProfile) {
        this.gameProfile = gameProfile;
    }

    public GameProfile getGameProfile() {
        return this.gameProfile;
    }
}

