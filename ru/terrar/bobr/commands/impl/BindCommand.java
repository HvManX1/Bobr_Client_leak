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

public class BindCommand
extends Command {
    public BindCommand() {
        super("bind", "Binds a module to a key", "bind <module> <key>", "bind <module>", "bind clear", "bind list");
        this.addAliases("b");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("clear")) {
                for (Module module : bobr.getGate().moduleManager.MODULE_LIST) {
                    module.setKeyBind(0);
                }
                ChatUtil.clientMessage("Key Binds cleared");
                if (bobr.getGate().presetManager.isAutoSave()) {
                    bobr.getGate().presetManager.saveActivePreset();
                }
                return;
            }
            if (args[1].equalsIgnoreCase("list")) {
                ChatUtil.clientMessage((Object)TextFormatting.BOLD + "Key Bind List:");
                for (Module module : bobr.getGate().moduleManager.MODULE_LIST) {
                    if (module.getKeyBind() == 0) continue;
                    ChatUtil.clientMessage(module.getName() + " is bound to " + Keyboard.getKeyName((int)module.getKeyBind()));
                }
                return;
            }
            for (Module module : bobr.getGate().moduleManager.MODULE_LIST) {
                if (!module.getId().equalsIgnoreCase(args[1].toUpperCase())) continue;
                ChatUtil.clientMessage(module.getName() + " is bound to " + Keyboard.getKeyName((int)module.getKeyBind()));
                return;
            }
        }
        if (args.length != 3 || args[2].length() != 1) {
            this.syntaxError();
            return;
        }
        for (Module module : bobr.getGate().moduleManager.MODULE_LIST) {
            if (!module.getId().equalsIgnoreCase(args[1])) continue;
            module.setKeyBind(Keyboard.getKeyIndex((String)args[2].toUpperCase()));
            ChatUtil.clientMessage(module.getName() + " bound to " + args[2].toUpperCase());
            if (bobr.getGate().presetManager.isAutoSave()) {
                bobr.getGate().presetManager.saveActivePreset();
            }
            return;
        }
        this.syntaxError();
    }
}

