/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.commands.impl;

import ru.terrar.bobr.bobr;
import ru.terrar.bobr.commands.Command;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.util.ChatUtil;

public class ToggleCommand
extends Command {
    public ToggleCommand() {
        super("toggle", "Toggles a Module", "toggle <module>");
        this.addAliases("t");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length != 2) {
            this.syntaxError();
            return;
        }
        if (!this.isModule(args[1])) {
            ChatUtil.clientMessage("Module not found");
        }
    }

    public boolean isModule(String name) {
        for (Module module : bobr.getGate().moduleManager.MODULE_LIST) {
            if (!module.getId().equalsIgnoreCase(name)) continue;
            module.toggle();
            ChatUtil.clientMessage(module.getName() + " module toggled");
            if (bobr.getGate().presetManager.isAutoSave()) {
                bobr.getGate().presetManager.saveActivePreset();
            }
            return true;
        }
        return false;
    }
}

