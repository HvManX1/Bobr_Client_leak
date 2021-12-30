/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.TextFormatting
 */
package me.thef1xer.gateclient.commands.impl;

import java.io.File;
import me.thef1xer.gateclient.GateClient;
import me.thef1xer.gateclient.commands.Command;
import me.thef1xer.gateclient.managers.PresetManager;
import me.thef1xer.gateclient.util.ChatUtil;
import me.thef1xer.gateclient.util.DirectoryUtil;
import net.minecraft.util.text.TextFormatting;

public class PresetCommand
extends Command {
    private final PresetManager presetManager;

    public PresetCommand() {
        super("preset", "Allows you to have multiple configurations", "preset", "preset list", "preset clear", "preset load <name>", "preset create <name>", "preset save", "preset remove <name>", "preset autosave <true/false>");
        this.presetManager = GateClient.getGate().presetManager;
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 1) {
            String preset = DirectoryUtil.removeExtension(this.presetManager.getActivePreset().getName());
            ChatUtil.clientMessage("Current Preset: " + (Object)TextFormatting.GOLD + preset + (Object)TextFormatting.WHITE + " Auto Save: " + (Object)TextFormatting.GOLD + this.presetManager.isAutoSave());
            return;
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("list")) {
                this.presetManager.updatePresetList();
                ChatUtil.clientMessage((Object)TextFormatting.BOLD + "Preset List:");
                for (File file : this.presetManager.PRESET_LIST) {
                    if (this.presetManager.getActivePreset().equals(file)) {
                        ChatUtil.clientMessage((Object)TextFormatting.GOLD + DirectoryUtil.removeExtension(file.getName()));
                        continue;
                    }
                    ChatUtil.clientMessage(DirectoryUtil.removeExtension(file.getName()));
                }
                return;
            }
            if (args[1].equalsIgnoreCase("clear")) {
                this.presetManager.updatePresetList();
                for (File file : this.presetManager.PRESET_LIST) {
                    file.delete();
                }
                this.presetManager.createNewPreset("default.json");
                ChatUtil.clientMessage("Preset list cleared");
                return;
            }
            if (args[1].equalsIgnoreCase("save")) {
                this.presetManager.saveActivePreset();
                ChatUtil.clientMessage("Preset saved");
                return;
            }
        }
        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("load")) {
                this.presetManager.updatePresetList();
                for (File file : this.presetManager.PRESET_LIST) {
                    if (!file.getName().equalsIgnoreCase(args[2] + ".json")) continue;
                    this.presetManager.setActivePreset(file);
                    GateClient.getGate().configManager.save();
                    ChatUtil.clientMessage("Preset " + (Object)TextFormatting.GOLD + args[2].toLowerCase() + (Object)TextFormatting.RESET + " loaded");
                    return;
                }
                ChatUtil.clientMessage("Preset not found");
                return;
            }
            if (args[1].equalsIgnoreCase("create")) {
                if (this.presetManager.createNewPreset(args[2] + ".json")) {
                    ChatUtil.clientMessage("Preset " + (Object)TextFormatting.GOLD + args[2] + (Object)TextFormatting.RESET + " created");
                } else {
                    ChatUtil.clientMessage("That preset already exists, try another name");
                }
                return;
            }
            if (args[1].equalsIgnoreCase("remove")) {
                if (this.presetManager.getActivePreset().getName().equalsIgnoreCase(args[2] + ".json")) {
                    this.presetManager.removeActivePreset();
                    ChatUtil.clientMessage("Preset " + (Object)TextFormatting.GOLD + args[2] + (Object)TextFormatting.RESET + " removed");
                    return;
                }
                if (this.presetManager.removePreset(args[2] + ".json")) {
                    ChatUtil.clientMessage("Preset " + (Object)TextFormatting.GOLD + args[2] + (Object)TextFormatting.RESET + " removed");
                } else {
                    ChatUtil.clientMessage("Preset not found");
                }
                return;
            }
            if (args[1].equalsIgnoreCase("autosave")) {
                if (args[2].equalsIgnoreCase("true")) {
                    this.presetManager.setAutoSave(true);
                    ChatUtil.clientMessage("The current Preset will now be saved automatically");
                } else if (args[2].equalsIgnoreCase("false")) {
                    this.presetManager.setAutoSave(false);
                    ChatUtil.clientMessage("The current Preset will no longer be saved automatically");
                } else {
                    ChatUtil.clientMessage("The second argument must be either true or false");
                }
                return;
            }
        }
        this.syntaxError();
    }
}

