//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraftforge.client.event.ClientChatEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.input.Keyboard
 */
package me.thef1xer.gateclient.handlers;

import me.thef1xer.gateclient.GateClient;
import me.thef1xer.gateclient.commands.Command;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class EventHandler {
    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onKeyPressed(InputEvent.KeyInputEvent event) {
        if (Keyboard.isCreated() && Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null && Keyboard.getEventKeyState()) {
            for (Module module : GateClient.getGate().moduleManager.MODULE_LIST) {
                if (Keyboard.getEventKey() != module.getKeyBind()) continue;
                module.toggle();
                if (!GateClient.getGate().presetManager.isAutoSave()) continue;
                GateClient.getGate().presetManager.saveActivePreset();
            }
        }
    }

    @SubscribeEvent
    public void onMessageSent(ClientChatEvent event) {
        String message = event.getOriginalMessage();
        if (message.startsWith(GateClient.getGate().commandManager.getPrefix())) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(message);
            String[] args = message.substring(GateClient.getGate().commandManager.getPrefix().length()).split(" ");
            boolean found = false;
            for (Command command : GateClient.getGate().commandManager.COMMAND_LIST) {
                if (!ChatUtil.isCommand(args[0], command)) continue;
                if (args.length == 2 && args[1].equalsIgnoreCase("help")) {
                    ChatUtil.clientMessage("Syntax for " + (Object)TextFormatting.GOLD + command.getName() + (Object)TextFormatting.RESET + " command:");
                    for (String syntax : command.getSyntax()) {
                        ChatUtil.clientMessage((Object)TextFormatting.YELLOW + GateClient.getGate().commandManager.getPrefix() + syntax);
                    }
                } else {
                    command.onCommand(args);
                }
                found = true;
                break;
            }
            if (!found) {
                ChatUtil.clientMessage("Command Not Found");
            }
            event.setCanceled(true);
        }
    }
}

