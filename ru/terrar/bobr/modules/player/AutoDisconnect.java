//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package ru.terrar.bobr.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.modules.player.Freecam;
import ru.terrar.bobr.settings.impl.BooleanSetting;
import ru.terrar.bobr.settings.impl.FloatSetting;

public class AutoDisconnect
extends Module {
    public static final AutoDisconnect INSTANCE = new AutoDisconnect();
    public final BooleanSetting players = new BooleanSetting("Players", "players", false);
    public final FloatSetting health = new FloatSetting("Health", "health", 10.0f, 0.0f, 20.0f, 1.0f);
    public final BooleanSetting ignoreTotems = new BooleanSetting("Ignore Totems", "ignoretotems", true);
    public final BooleanSetting disable = new BooleanSetting("Disable", "disable", true);

    public AutoDisconnect() {
        super("Auto Disconnect", "autodisconnect", Module.ModuleCategory.PLAYER);
        this.addSettings(this.players, this.health, this.ignoreTotems, this.disable);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
        if (this.players.getValue()) {
            for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
                if (!(entity instanceof EntityPlayer) || entity == Minecraft.getMinecraft().player || entity == Freecam.INSTANCE.camera) continue;
                Minecraft.getMinecraft().player.connection.onDisconnect((ITextComponent)new TextComponentString("A Player was found"));
                if (this.disable.getValue()) {
                    this.setEnabled(false);
                }
                return;
            }
        }
        if (!this.ignoreTotems.getValue() && Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() == Item.getItemById((int)449)) {
            return;
        }
        if (Minecraft.getMinecraft().player.getHealth() <= this.health.getValue()) {
            Minecraft.getMinecraft().player.connection.onDisconnect((ITextComponent)new TextComponentString("Health was " + Minecraft.getMinecraft().player.getHealth()));
            if (this.disable.getValue()) {
                this.setEnabled(false);
            }
        }
    }
}

