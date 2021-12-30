/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.commands.impl;

import ru.terrar.bobr.commands.Command;
import ru.terrar.bobr.managers.FriendManager;

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

