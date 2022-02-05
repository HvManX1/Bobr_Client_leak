/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.AxisAlignedBB
 */
package event.events;

import event.Event;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;

public class EventCollide
extends Event {
    private Entity entity;
    private double posX;
    private double posY;
    private double posZ;
    private AxisAlignedBB boundingBox;
    private Block block;

    public EventCollide(Entity entity, double posX, double posY, double posZ, AxisAlignedBB boundingBox, Block block) {
        this.entity = entity;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.boundingBox = boundingBox;
        this.block = block;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public Block getBlock() {
        return this.block;
    }
}

