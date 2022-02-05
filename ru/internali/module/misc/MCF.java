//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.input.Mouse
 */
package ru.internali.module.misc;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.utils.FriendManager;

public class MCF
extends Module {
    public int timer;

    public MCF() {
        super("MCF", "add friends", Category.OUTHER);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (Mouse.isButtonDown((int)2) && (this.timer & 0x3E8) == 0) {
            this.timer += 50;
            Entity friend = MCF.mc.objectMouseOver.entityHit;
            if (friend != null) {
                FriendManager.toggleFriend(friend.getName());
            }
        }
        ++this.timer;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}

