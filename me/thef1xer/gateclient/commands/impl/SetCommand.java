/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.TextFormatting
 *  org.apache.logging.log4j.core.util.Integers
 */
package me.thef1xer.gateclient.commands.impl;

import me.thef1xer.gateclient.GateClient;
import me.thef1xer.gateclient.commands.Command;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.Setting;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import me.thef1xer.gateclient.settings.impl.EnumSetting;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import me.thef1xer.gateclient.settings.impl.RGBSetting;
import me.thef1xer.gateclient.util.ChatUtil;
import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.core.util.Integers;

public class SetCommand
extends Command {
    private Module module;

    public SetCommand() {
        super("set", "Changes the settings of a Module", "set <module> <setting> <value>", "set <module> <setting>", "set <module> list");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 3 && this.isModule(args[1])) {
            if (args[2].equalsIgnoreCase("list")) {
                ChatUtil.clientMessage((Object)TextFormatting.BOLD + "Settings for " + this.module.getName() + " module:");
                for (Setting setting : this.module.getSettings()) {
                    this.sendMessageSetting(setting);
                }
                return;
            }
            for (Setting setting : this.module.getSettings()) {
                if (!setting.getId().equalsIgnoreCase(args[2])) continue;
                this.sendMessageSetting(setting);
                return;
            }
        }
        if (args.length >= 4 && this.isModule(args[1])) {
            for (Setting setting : this.module.getSettings()) {
                if (!args[2].equalsIgnoreCase(setting.getId())) continue;
                if (setting instanceof BooleanSetting) {
                    if (args.length == 4) {
                        if (args[3].equalsIgnoreCase("true")) {
                            ((BooleanSetting)setting).setValue(true);
                            ChatUtil.clientMessage(setting.getName() + " set to " + (Object)TextFormatting.GOLD + "true");
                            if (GateClient.getGate().presetManager.isAutoSave()) {
                                GateClient.getGate().presetManager.saveActivePreset();
                            }
                            return;
                        }
                        if (args[3].equalsIgnoreCase("false")) {
                            ((BooleanSetting)setting).setValue(false);
                            ChatUtil.clientMessage(setting.getName() + " set to " + (Object)TextFormatting.GOLD + "false");
                            if (GateClient.getGate().presetManager.isAutoSave()) {
                                GateClient.getGate().presetManager.saveActivePreset();
                            }
                            return;
                        }
                    }
                    ChatUtil.clientMessage("Value must be " + (Object)TextFormatting.GOLD + "true" + (Object)TextFormatting.WHITE + " or " + (Object)TextFormatting.GOLD + "false");
                } else if (setting instanceof RGBSetting) {
                    if (args.length == 6) {
                        int b;
                        int g;
                        int r;
                        try {
                            r = Integers.parseInt((String)args[3]);
                            g = Integers.parseInt((String)args[4]);
                            b = Integers.parseInt((String)args[5]);
                        }
                        catch (Exception e) {
                            ChatUtil.clientMessage("Red, Green and Blue must be integers");
                            return;
                        }
                        if (r < 0 || g < 0 || b < 0 || r > 255 || g > 255 || b > 255) {
                            ChatUtil.clientMessage("Red, Green and Blue must be between " + (Object)TextFormatting.GOLD + "0" + (Object)TextFormatting.WHITE + " and " + (Object)TextFormatting.GOLD + "255");
                            return;
                        }
                        ((RGBSetting)setting).setRed(r);
                        ((RGBSetting)setting).setGreen(g);
                        ((RGBSetting)setting).setBlue(b);
                        ChatUtil.clientMessage(setting.getName() + " set to " + (Object)TextFormatting.GOLD + "(" + r + ", " + g + ", " + b + ")");
                        if (GateClient.getGate().presetManager.isAutoSave()) {
                            GateClient.getGate().presetManager.saveActivePreset();
                        }
                        return;
                    }
                    ChatUtil.clientMessage("Values must be " + (Object)TextFormatting.GOLD + "<red> <green> <blue>");
                } else if (setting instanceof EnumSetting) {
                    if (args.length == 4 && ((EnumSetting)setting).setValueFromName(args[3])) {
                        ChatUtil.clientMessage(setting.getName() + " set to " + (Object)TextFormatting.GOLD + ((EnumSetting)setting).getCurrentValueName());
                        if (GateClient.getGate().presetManager.isAutoSave()) {
                            GateClient.getGate().presetManager.saveActivePreset();
                        }
                        return;
                    }
                    ChatUtil.clientMessage("Value not valid");
                } else if (setting instanceof FloatSetting) {
                    if (args.length == 4) {
                        try {
                            float f = Float.parseFloat(args[3]);
                            if (((FloatSetting)setting).setValue(f)) {
                                ChatUtil.clientMessage(setting.getName() + " set to " + (Object)TextFormatting.GOLD + f);
                                if (GateClient.getGate().presetManager.isAutoSave()) {
                                    GateClient.getGate().presetManager.saveActivePreset();
                                }
                                return;
                            }
                        }
                        catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    ChatUtil.clientMessage("Value must be a float value between " + (Object)TextFormatting.GOLD + ((FloatSetting)setting).getMin() + (Object)TextFormatting.WHITE + " and " + (Object)TextFormatting.GOLD + ((FloatSetting)setting).getMax());
                }
                return;
            }
        }
        this.syntaxError();
    }

    private boolean isModule(String s) {
        for (Module module : GateClient.getGate().moduleManager.MODULE_LIST) {
            if (!module.getId().equalsIgnoreCase(s)) continue;
            this.module = module;
            return true;
        }
        return false;
    }

    private void sendMessageSetting(Setting setting) {
        String value = "";
        String current = "";
        if (setting instanceof BooleanSetting) {
            value = "<true / false>";
            current = ((BooleanSetting)setting).getValue() ? "true" : "false";
        } else if (setting instanceof RGBSetting) {
            value = "<red> <green> <blue>";
            current = ((RGBSetting)setting).getRed() + ", " + ((RGBSetting)setting).getGreen() + ", " + ((RGBSetting)setting).getBlue();
        } else if (setting instanceof EnumSetting) {
            value = "<";
            for (int i = 0; i < ((EnumSetting)setting).getValues().length; ++i) {
                value = value.concat(((EnumSetting)setting).getValues()[i].toString());
                value = i == ((EnumSetting)setting).getValues().length - 1 ? value.concat(">") : value.concat(" / ");
            }
            current = ((EnumSetting)setting).getCurrentValueName() + "]";
        } else if (setting instanceof FloatSetting) {
            value = "<number between " + ((FloatSetting)setting).getMin() + " and " + ((FloatSetting)setting).getMax() + ">";
            current = ((FloatSetting)setting).getValue() + "";
        }
        ChatUtil.clientMessage(TextFormatting.GOLD.toString() + TextFormatting.ITALIC.toString() + setting.getName() + ": " + TextFormatting.RESET.toString() + setting.getId() + " " + value + TextFormatting.GOLD.toString() + TextFormatting.ITALIC.toString() + " [" + current + "]");
    }
}

