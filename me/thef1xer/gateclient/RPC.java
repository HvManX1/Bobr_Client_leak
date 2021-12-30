//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.thef1xer.gateclient;

import me.thef1xer.gateclient.GateClient;
import me.thef1xer.gateclient.discord.DiscordEventHandlers;
import me.thef1xer.gateclient.discord.DiscordRPC;
import me.thef1xer.gateclient.discord.DiscordRichPresence;
import me.thef1xer.gateclient.modules.Module;
import net.minecraft.client.Minecraft;

public class RPC {
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;

    public static void startRPC() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        eventHandlers.disconnected = (var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + var1 + ", var2: " + var2);
        String discordID = "909559441907675136";
        discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);
        RPC.discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        RPC.discordRichPresence.details = "Null";
        RPC.discordRichPresence.largeImageKey = "bobr";
        RPC.discordRichPresence.largeImageText = "Bobr Client  0.0.8";
        discordRPC.Discord_UpdatePresence(discordRichPresence);
        new Thread(() -> {
            while (true) {
                int use = 0;
                int max = GateClient.getGate().moduleManager.MODULE_LIST.size();
                for (Module module : GateClient.getGate().moduleManager.MODULE_LIST) {
                    if (!module.isEnabled() || !module.drawOnHud.getValue()) continue;
                    ++use;
                }
                RPC.discordRichPresence.state = "Hacks [" + use + "/" + max + ']';
                discordRPC.Discord_UpdatePresence(discordRichPresence);
                RPC.discordRichPresence.details = Minecraft.getMinecraft().world != null ? (Minecraft.getMinecraft().isSingleplayer() ? "Singleplayer" : "server: " + Minecraft.getMinecraft().getCurrentServerData().serverIP) : "MainMenu";
                discordRPC.Discord_RunCallbacks();
                try {
                    Thread.sleep(5000L);
                    continue;
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                }
                break;
            }
        }).start();
    }

    public static void stopRPC() {
        discordRPC.Discord_Shutdown();
        discordRPC.Discord_ClearPresence();
    }
}

