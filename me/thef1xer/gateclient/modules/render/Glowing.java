//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.thef1xer.gateclient.modules.render;

import me.thef1xer.gateclient.managers.FriendManager;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Glowing
extends Module {
    public static final Glowing INSTANCE = new Glowing();
    public final BooleanSetting targetPlayer = new BooleanSetting("Players", "players", true);
    public final BooleanSetting targetHostile = new BooleanSetting("Monsters", "monsters", true);
    public final BooleanSetting targetPassive = new BooleanSetting("Passive", "passive", true);

    public Glowing() {
        super("Glowing", "Glowing", Module.ModuleCategory.RENDER);
        this.targetPlayer.setParent("Target");
        this.targetHostile.setParent("Target");
        this.addSettings(this.targetPlayer, this.targetHostile, this.targetPassive);
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

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (this.targetPlayer.getValue() && entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().getRenderViewEntity()) {
                if (FriendManager.isFriend(entity.getName())) {
                    if (entity.isGlowing()) continue;
                    entity.setGlowing(true);
                    continue;
                }
                if (entity.isGlowing()) continue;
                entity.setGlowing(true);
                continue;
            }
            if (this.targetHostile.getValue() && entity.isCreatureType(EnumCreatureType.MONSTER, false)) {
                if (entity.isGlowing()) continue;
                entity.setGlowing(true);
                continue;
            }
            if (!this.targetPassive.getValue() || !entity.isCreatureType(EnumCreatureType.AMBIENT, false) && !entity.isCreatureType(EnumCreatureType.WATER_CREATURE, false) && !entity.isCreatureType(EnumCreatureType.CREATURE, false) || entity.isGlowing()) continue;
            entity.setGlowing(true);
        }
    }
}

