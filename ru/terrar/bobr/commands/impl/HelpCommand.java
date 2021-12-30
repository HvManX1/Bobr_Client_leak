/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.TextFormatting
 */
package ru.terrar.bobr.commands.impl;

import net.minecraft.util.text.TextFormatting;
import ru.terrar.bobr.bobr;
import ru.terrar.bobr.commands.Command;
import ru.terrar.bobr.util.ChatUtil;

public class HelpCommand
extends Command {
    public HelpCommand() {
        super("help", "Displays this list", "help");
        this.addAliases("?");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 1) {
            ChatUtil.clientMessage((Object)TextFormatting.BOLD + "List of Commands in this Client:");
            for (Command command : bobr.getGate().commandManager.COMMAND_LIST) {
                ChatUtil.clientMessage((Object)TextFormatting.GOLD + command.getName() + ": " + (Object)TextFormatting.RESET + command.getDesc());
            }
        } else {
            this.syntaxError();
        }
    }
}

