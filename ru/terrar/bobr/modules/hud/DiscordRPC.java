/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 */
package ru.terrar.bobr.modules.hud;

import net.minecraftforge.common.MinecraftForge;
import ru.terrar.bobr.RPC;
import ru.terrar.bobr.modules.Module;

public class DiscordRPC
extends Module {
    public static final DiscordRPC INSTANCE = new DiscordRPC();

    public DiscordRPC() {
        super("Discord RPC", "discordRPC", Module.ModuleCategory.HUD);
    }

    @Override
    public void onEnable() {
        RPC.startRPC();
        super.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        RPC.stopRPC();
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
}

