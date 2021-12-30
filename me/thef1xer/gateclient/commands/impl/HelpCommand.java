/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.TextFormatting
 */
package me.thef1xer.gateclient.commands.impl;

import me.thef1xer.gateclient.GateClient;
import me.thef1xer.gateclient.commands.Command;
import me.thef1xer.gateclient.util.ChatUtil;
import net.minecraft.util.text.TextFormatting;

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
            for (Command command : GateClient.getGate().commandManager.COMMAND_LIST) {
                ChatUtil.clientMessage((Object)TextFormatting.GOLD + command.getName() + ": " + (Object)TextFormatting.RESET + command.getDesc());
            }
        } else {
            this.syntaxError();
        }
    }
}

