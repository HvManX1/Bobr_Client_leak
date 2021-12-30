//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.terrar.bobr.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.terrar.bobr.modules.Module;

public class Sprint
extends Module {
    public static final Sprint INSTANCE = new Sprint();

    public Sprint() {
        super("Sprint", "sprint", Module.ModuleCategory.MOVEMENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void onLivingEvent(LivingEvent.LivingUpdateEvent event) {
        if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().world.isRemote && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.moveForward > 0.0f && !Minecraft.getMinecraft().player.collidedHorizontally && !Minecraft.getMinecraft().player.isSprinting() && !Minecraft.getMinecraft().player.isSneaking()) {
            Minecraft.getMinecraft().player.setSprinting(true);
        }
    }
}

