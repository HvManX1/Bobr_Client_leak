//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.terrar.bobr.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;

public class NoSlow
extends Module {
    public static final NoSlow INSTANCE = new NoSlow();
    public final BooleanSetting sneak = new BooleanSetting("Sneaking", "sneak", true);
    public final BooleanSetting item = new BooleanSetting("Item", "item", true);

    public NoSlow() {
        super("NoSlow", "noslow", Module.ModuleCategory.MOVEMENT);
        this.addSettings(this.sneak, this.item);
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
    public void onInput(InputUpdateEvent event) {
        if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null) {
            if (this.item.getValue() && Minecraft.getMinecraft().player.isHandActive() && !Minecraft.getMinecraft().player.isRiding()) {
                Minecraft.getMinecraft().player.movementInput.moveStrafe /= 0.2f;
                Minecraft.getMinecraft().player.movementInput.moveForward /= 0.2f;
            }
            if (this.sneak.getValue() && Minecraft.getMinecraft().player.isSneaking()) {
                Minecraft.getMinecraft().player.movementInput.moveStrafe = (float)((double)Minecraft.getMinecraft().player.movementInput.moveStrafe / 0.3);
                Minecraft.getMinecraft().player.movementInput.moveForward = (float)((double)Minecraft.getMinecraft().player.movementInput.moveForward / 0.3);
            }
        }
    }
}

