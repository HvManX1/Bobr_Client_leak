//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.thef1xer.gateclient.commands.impl;

import me.thef1xer.gateclient.commands.Command;
import net.minecraft.client.Minecraft;

public class SayCommand
extends Command {
    public SayCommand() {
        super("say", "Sends a message (that can include the prefix)", "say <message>");
        this.addAliases("s");
    }

    @Override
    public void onCommand(String[] args) {
        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            message.append(args[i]);
            if (i == args.length - 1) continue;
            message.append(" ");
        }
        Minecraft.getMinecraft().player.sendChatMessage(message.toString());
    }
}

