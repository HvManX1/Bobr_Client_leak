//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.thef1xer.gateclient.util;

import java.io.File;
import net.minecraft.client.Minecraft;

public class DirectoryUtil {
    public static final File GATE_FOLDER = new File(Minecraft.getMinecraft().gameDir, "Config");
    public static final File PRESET_FOLDER = new File(GATE_FOLDER, "Presets");

    public static boolean isJson(File file) {
        String name = file.getName();
        int index = name.lastIndexOf(".");
        return name.substring(index).equals(".json");
    }

    public static String removeExtension(String stringIn) {
        StringBuilder newString = new StringBuilder();
        for (char n : stringIn.toCharArray()) {
            if (n == '.') break;
            newString.append(n);
        }
        return newString.toString();
    }
}

