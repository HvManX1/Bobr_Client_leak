/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumHandSide
 */
package ru.terrar.bobr.modules.render;

import net.minecraft.util.EnumHandSide;
import ru.terrar.bobr.modules.render.Event;

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

