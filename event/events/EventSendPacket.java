/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package event.events;

import event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket
extends Event {
    private Packet packet;

    public EventSendPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

