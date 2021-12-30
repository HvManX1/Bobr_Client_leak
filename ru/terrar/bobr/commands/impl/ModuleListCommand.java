/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.TextFormatting
 *  org.lwjgl.input.Keyboard
 */
package ru.terrar.bobr.commands.impl;

import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import ru.terrar.bobr.bobr;
import ru.terrar.bobr.commands.Command;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.util.ChatUtil;

public class ModuleListCommand
extends Command {
    public ModuleListCommand() {
        super("modulelist", "Shows the name, id and keybind of every Module in the Client", "modulelist");
        this.addAliases("list", "modules");
    }

    @Override
    public void onCommand(String[] args) {
        ChatUtil.clientMessage((Object)TextFormatting.BOLD + "Module List:");
        for (Module module : bobr.getGate().moduleManager.MODULE_LIST) {
            ChatUtil.clientMessage((Object)TextFormatting.GOLD + module.getName() + ": " + (Object)TextFormatting.RESET + module.getId() + " [" + Keyboard.getKeyName((int)module.getKeyBind()) + "]");
        }
    }
}

