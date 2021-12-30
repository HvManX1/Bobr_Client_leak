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

public class PrefixCommand
extends Command {
    public PrefixCommand() {
        super("prefix", "Changes the Command Prefix", "prefix <new prefix>");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 2) {
            bobr.getGate().commandManager.setPrefix(args[1]);
            bobr.getGate().configManager.save();
            ChatUtil.clientMessage("Prefix changed to: " + (Object)TextFormatting.GOLD + args[1]);
            return;
        }
        this.syntaxError();
    }
}

