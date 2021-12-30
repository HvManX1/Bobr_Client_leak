//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiIngameMenu
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraftforge.client.event.InputUpdateEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 */
package me.thef1xer.gateclient.modules.movement;

import me.thef1xer.gateclient.gui.clickgui.ClickGui;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class GuiMove
extends Module {
    public static final GuiMove INSTANCE = new GuiMove();
    public BooleanSetting sneak = new BooleanSetting("Sneak", "sneak", false);

    public GuiMove() {
        super("GUI Move", "guimove", Module.ModuleCategory.MOVEMENT);
        this.addSettings(this.sneak);
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
    public void onKeyUpdate(InputUpdateEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.world != null && mc.player != null && (mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiIngameMenu || mc.currentScreen instanceof GuiOptions || mc.currentScreen instanceof ClickGui)) {
            if (Keyboard.isKeyDown((int)mc.gameSettings.keyBindForward.getKeyCode())) {
                mc.player.movementInput.moveForward += 1.0f;
                mc.player.movementInput.forwardKeyDown = true;
            }
            if (Keyboard.isKeyDown((int)mc.gameSettings.keyBindBack.getKeyCode())) {
                mc.player.movementInput.moveForward -= 1.0f;
                mc.player.movementInput.backKeyDown = true;
            }
            if (Keyboard.isKeyDown((int)mc.gameSettings.keyBindRight.getKeyCode())) {
                mc.player.movementInput.moveStrafe -= 1.0f;
                mc.player.movementInput.rightKeyDown = true;
            }
            if (Keyboard.isKeyDown((int)mc.gameSettings.keyBindLeft.getKeyCode())) {
                mc.player.movementInput.moveStrafe += 1.0f;
                mc.player.movementInput.rightKeyDown = true;
            }
            mc.player.movementInput.jump = Keyboard.isKeyDown((int)mc.gameSettings.keyBindJump.getKeyCode());
            boolean bl = mc.player.movementInput.sneak = this.sneak.getValue() && Keyboard.isKeyDown((int)mc.gameSettings.keyBindSneak.getKeyCode());
            if (mc.player.movementInput.sneak) {
                mc.player.movementInput.moveStrafe = (float)((double)mc.player.movementInput.moveStrafe * 0.3);
                mc.player.movementInput.moveForward = (float)((double)mc.player.movementInput.moveForward * 0.3);
            }
        }
    }
}

