//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package me.thef1xer.gateclient.modules.hud;

import me.thef1xer.gateclient.managers.CommandManager;
import me.thef1xer.gateclient.managers.ModuleManager;
import me.thef1xer.gateclient.managers.PresetManager;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SelfDestruct
extends Module {
    private transient long time;
    public CommandManager commandManager;
    public ModuleManager moduleManager;
    public static final SelfDestruct INSTANCE = new SelfDestruct();
    public boolean toggle;
    public PresetManager presetManager;

    public SelfDestruct() {
        super("SelfDestruct", "selfdestruct", Module.ModuleCategory.HUD);
        this.addSettings(new Setting[0]);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.time = System.currentTimeMillis();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        this.toggle = false;
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
    }
}
