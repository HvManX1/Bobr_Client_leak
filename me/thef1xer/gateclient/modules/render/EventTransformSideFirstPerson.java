/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumHandSide
 */
package me.thef1xer.gateclient.modules.render;

import me.thef1xer.gateclient.modules.render.Event;
import net.minecraft.util.EnumHandSide;

public class EventTransformSideFirstPerson
implements Event {
    private final EnumHandSide enumHandSide;

    public EventTransformSideFirstPerson(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }
}

