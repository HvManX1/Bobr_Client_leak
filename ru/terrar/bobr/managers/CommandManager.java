/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.managers;

import java.util.ArrayList;
import java.util.List;
import ru.terrar.bobr.commands.Command;
import ru.terrar.bobr.commands.impl.BindCommand;
import ru.terrar.bobr.commands.impl.ClipCommand;
import ru.terrar.bobr.commands.impl.FriendCommand;
import ru.terrar.bobr.commands.impl.HelpCommand;
import ru.terrar.bobr.commands.impl.ModuleListCommand;
import ru.terrar.bobr.commands.impl.PrefixCommand;
import ru.terrar.bobr.commands.impl.PresetCommand;
import ru.terrar.bobr.commands.impl.SayCommand;
import ru.terrar.bobr.commands.impl.SetCommand;
import ru.terrar.bobr.commands.impl.ToggleCommand;

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

