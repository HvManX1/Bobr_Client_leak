/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.TextFormatting
 *  org.lwjgl.input.Keyboard
 */
package me.thef1xer.gateclient.commands.impl;

import me.thef1xer.gateclient.GateClient;
import me.thef1xer.gateclient.commands.Command;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.util.ChatUtil;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class ModuleListCommand
extends Command {
    public ModuleListCommand() {
        super("modulelist", "Shows the name, id and keybind of every Module in the Client", "modulelist");
        this.addAliases("list", "modules");
    }

    @Override
    public void onCommand(String[] args) {
        ChatUtil.clientMessage((Object)TextFormatting.BOLD + "Module List:");
        for (Module module : GateClient.getGate().moduleManager.MODULE_LIST) {
            ChatUtil.clientMessage((Object)TextFormatting.GOLD + module.getName() + ": " + (Object)TextFormatting.RESET + module.getId() + " [" + Keyboard.getKeyName((int)module.getKeyBind()) + "]");
        }
    }
}

