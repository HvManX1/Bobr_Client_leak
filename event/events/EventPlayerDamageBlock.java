/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 */
package event.events;

import event.Event;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EventPlayerDamageBlock
extends Event {
    BlockPos pos;
    EnumFacing facing;

    public EventPlayerDamageBlock(BlockPos pos, EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public EnumFacing getFacing() {
        return this.facing;
    }
}

