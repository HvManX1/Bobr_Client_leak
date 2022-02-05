/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.chunk.Chunk
 */
package event.events;

import event.Event;
import net.minecraft.world.chunk.Chunk;

public class UnloadChunkEvent
extends Event {
    private Chunk chunk;

    public UnloadChunkEvent(Chunk chunk) {
        this.chunk = chunk;
    }

    public Chunk getChunk() {
        return this.chunk;
    }
}

