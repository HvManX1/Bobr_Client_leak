//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package ru.internali.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.internali.module.Category;
import ru.internali.module.Module;

public class NoHurtCum
extends Module {
    public NoHurtCum() {
        super("NoHurtCum", "disables hurt effect", Category.RENDER);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent playerTickEvent) {
        NoHurtCum.mc.player.hurtTime = 0;
        NoHurtCum.mc.player.hurtResistantTime = 0;
        NoHurtCum.mc.player.maxHurtResistantTime = 0;
        NoHurtCum.mc.player.maxHurtTime = 0;
    }
}

