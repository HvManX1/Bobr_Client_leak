//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package ru.internali.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.internali.module.Category;
import ru.internali.module.Module;

public class Spider
extends Module {
    public static int timer;

    public Spider() {
        super("Spider", "spide on walls", Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (Spider.mc.player.collidedHorizontally) {
            if (++timer == 0) {
                Spider.mc.player.jump();
                Spider.mc.player.motionY = 0.42f;
                Spider.mc.player.onGround = true;
            }
            if (timer >= 150) {
                timer = -1;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}

