//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraft.util.text.TextFormatting
 */
package ru.terrar.bobr.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import ru.terrar.bobr.commands.Command;

public class ChatUtil {
    public static void clientMessage(String message) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        message = (Object)TextFormatting.BLUE + "[" + "Bobr Client" + "] " + (Object)TextFormatting.RESET + message;
        Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentString(message));
    }

    public static boolean isCommand(String name, Command command) {
        if (name.equalsIgnoreCase(command.getName())) {
            return true;
        }
        for (String alias : command.getAliases()) {
            if (!name.equalsIgnoreCase(alias)) continue;
            return true;
        }
        return false;
    }
}

