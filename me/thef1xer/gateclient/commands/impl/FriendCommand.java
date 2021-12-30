/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.commands.impl;

import me.thef1xer.gateclient.commands.Command;
import me.thef1xer.gateclient.managers.FriendManager;

public class FriendCommand
extends Command {
    public FriendCommand() {
        super("friend", "add or remove friend", "friend <nick>");
        this.addAliases("f");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 2) {
            FriendManager.toggleFriend(args[1].toUpperCase());
        } else {
            this.syntaxError();
        }
    }
}

