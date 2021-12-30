//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 */
package me.thef1xer.gateclient.modules.movement;

import me.thef1xer.gateclient.events.PlayerMoveEvent;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.Setting;
import me.thef1xer.gateclient.util.BlockUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class SafeWalk
extends Module {
    public static final SafeWalk INSTANCE = new SafeWalk();

    public SafeWalk() {
        super("Safe Walk", "safewalk", Module.ModuleCategory.MOVEMENT);
        this.addSettings(new Setting[0]);
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

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent event) {
        if (Minecraft.getMinecraft().player.onGround && !Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed() && !BlockUtil.isCollidable(Minecraft.getMinecraft().world.getBlockState(new BlockPos(Minecraft.getMinecraft().player.getPositionVector().add(new Vec3d(0.0, -0.5, 0.0)))).getBlock())) {
            KeyBinding.setKeyBindState((int)Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), (boolean)true);
        } else if (!Keyboard.isKeyDown((int)Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
            KeyBinding.setKeyBindState((int)Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
        }
    }
}

