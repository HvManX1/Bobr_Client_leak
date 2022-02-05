//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FogColors
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package ru.internali.module.movement;

import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.utils.TimerUtils;

public class Speed
extends Module {
    public TimerUtils timer = new TimerUtils();
    private int boostTick;

    public Speed() {
        super("Bhop", "Bhop", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void fogColor(EntityViewRenderEvent.FogColors event) {
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (Speed.mc.player.onGround) {
            Speed.mc.player.jump();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}

