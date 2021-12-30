//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.entity.item.EntityArmorStand
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.terrar.bobr.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.terrar.bobr.managers.FriendManager;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;

public class WallHack
extends Module {
    public static final WallHack INSTANCE = new WallHack();
    public final BooleanSetting targetPlayer = new BooleanSetting("Players", "players", true);
    public final BooleanSetting friend = new BooleanSetting("Friends", "Friends", true);
    public final BooleanSetting targetHostile = new BooleanSetting("Monsters", "monsters", true);
    public final BooleanSetting targetPassive = new BooleanSetting("Passive", "passive", true);
    public final BooleanSetting rustme = new BooleanSetting("rustme", "rustme", true);

    public WallHack() {
        super("WallHack", "WallHack", Module.ModuleCategory.RENDER);
        this.targetPlayer.setParent("Target");
        this.targetHostile.setParent("Target");
        this.addSettings(this.targetPlayer, this.friend, this.targetHostile, this.targetPassive, this.rustme);
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

    void render(Entity entity, float ticks) {
        try {
            if (entity == null || entity == Minecraft.getMinecraft().player) {
                return;
            }
            if (entity == Minecraft.getMinecraft().getRenderViewEntity() && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
                return;
            }
            Minecraft.getMinecraft().entityRenderer.disableLightmap();
            Minecraft.getMinecraft().getRenderManager().renderEntityStatic(entity, ticks, false);
            Minecraft.getMinecraft().entityRenderer.enableLightmap();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        GlStateManager.clear((int)256);
        RenderHelper.enableStandardItemLighting();
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (this.targetPlayer.getValue() && entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().getRenderViewEntity()) {
                if (FriendManager.isFriend(entity.getName()) && this.friend.getValue()) {
                    this.render(entity, event.getPartialTicks());
                    continue;
                }
                this.render(entity, event.getPartialTicks());
                continue;
            }
            if (this.targetHostile.getValue() && entity.isCreatureType(EnumCreatureType.MONSTER, false)) {
                this.render(entity, event.getPartialTicks());
                continue;
            }
            if (this.targetPassive.getValue() && (entity.isCreatureType(EnumCreatureType.AMBIENT, false) || entity.isCreatureType(EnumCreatureType.WATER_CREATURE, false) || entity.isCreatureType(EnumCreatureType.CREATURE, false))) {
                this.render(entity, event.getPartialTicks());
                continue;
            }
            if (!this.rustme.getValue() || !(entity instanceof EntityArmorStand)) continue;
            this.render(entity, event.getPartialTicks());
        }
    }
}

