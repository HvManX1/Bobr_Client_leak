/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.managers;

import java.util.ArrayList;
import java.util.List;
import me.thef1xer.gateclient.commands.Command;
import me.thef1xer.gateclient.commands.impl.BindCommand;
import me.thef1xer.gateclient.commands.impl.ClipCommand;
import me.thef1xer.gateclient.commands.impl.FriendCommand;
import me.thef1xer.gateclient.commands.impl.HelpCommand;
import me.thef1xer.gateclient.commands.impl.ModuleListCommand;
import me.thef1xer.gateclient.commands.impl.PrefixCommand;
import me.thef1xer.gateclient.commands.impl.PresetCommand;
import me.thef1xer.gateclient.commands.impl.SayCommand;
import me.thef1xer.gateclient.commands.impl.SetCommand;
import me.thef1xer.gateclient.commands.impl.ToggleCommand;

public class CommandManager {
    private String prefix = ".";
    public final List<Command> COMMAND_LIST = new ArrayList<Command>();

    public void close() {
        this.COMMAND_LIST.clear();
    }

    public void init() {
        this.COMMAND_LIST.add(new BindCommand());
        this.COMMAND_LIST.add(new ClipCommand());
        this.COMMAND_LIST.add(new HelpCommand());
        this.COMMAND_LIST.add(new ModuleListCommand());
        this.COMMAND_LIST.add(new PrefixCommand());
        this.COMMAND_LIST.add(new PresetCommand());
        this.COMMAND_LIST.add(new SayCommand());
        this.COMMAND_LIST.add(new SetCommand());
        this.COMMAND_LIST.add(new ToggleCommand());
        this.COMMAND_LIST.add(new FriendCommand());
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}

