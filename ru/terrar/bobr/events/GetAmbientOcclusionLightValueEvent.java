//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package ru.terrar.bobr.events;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GetAmbientOcclusionLightValueEvent
extends Event {
    private float lightValue;

    public GetAmbientOcclusionLightValueEvent(IBlockState state) {
        this.lightValue = state.getBlock().getAmbientOcclusionLightValue(state);
    }

    public float getLightValue() {
        return this.lightValue;
    }

    public void setLightValue(float lightValue) {
        this.lightValue = lightValue;
    }
}

