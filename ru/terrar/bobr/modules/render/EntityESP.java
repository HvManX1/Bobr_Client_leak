//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.terrar.bobr.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.terrar.bobr.managers.FriendManager;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;
import ru.terrar.bobr.settings.impl.FloatSetting;
import ru.terrar.bobr.settings.impl.RGBSetting;
import ru.terrar.bobr.util.RenderUtil;

public class EntityESP
extends Module {
    public static final EntityESP INSTANCE = new EntityESP();
    public final BooleanSetting targetPlayer = new BooleanSetting("Players", "players", true);
    public final BooleanSetting targetHostile = new BooleanSetting("Monsters", "monsters", true);
    public final BooleanSetting targetPassive = new BooleanSetting("Passive", "passive", true);
    public final RGBSetting playerColor = new RGBSetting("Player Color", "playercolor", 255, 0, 0);
    public final RGBSetting friendColor = new RGBSetting("Friend Color", "friendcolor", 0, 255, 255);
    public final RGBSetting hostileColor = new RGBSetting("Monster Color", "hostilecolor", 255, 255, 0);
    public final RGBSetting passiveColor = new RGBSetting("Passive Color", "passivecolor", 0, 255, 0);
    public final FloatSetting colorAlpha = new FloatSetting("Color Alpha", "coloralpha", 1.0f, 0.0f, 1.0f);

    public EntityESP() {
        super("Entity ESP", "entityesp", Module.ModuleCategory.RENDER);
        this.targetPlayer.setParent("Target");
        this.targetHostile.setParent("Target");
        this.playerColor.setParent("Color");
        this.hostileColor.setParent("Color");
        this.addSettings(this.targetPlayer, this.targetHostile, this.targetPassive, this.playerColor, this.friendColor, this.hostileColor, this.passiveColor, this.colorAlpha);
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
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.disableDepth();
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (this.targetPlayer.getValue() && entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().getRenderViewEntity()) {
                if (FriendManager.isFriend(entity.getName())) {
                    RenderUtil.renderEntityBoundingBox(entity, this.friendColor, this.colorAlpha.getValue());
                    RenderUtil.renderEntityFilledBoundingBox(entity, this.friendColor, this.colorAlpha.getValue() / 3.0f);
                    continue;
                }
                RenderUtil.renderEntityBoundingBox(entity, this.playerColor, this.colorAlpha.getValue());
                RenderUtil.renderEntityFilledBoundingBox(entity, this.playerColor, this.colorAlpha.getValue() / 3.0f);
                continue;
            }
            if (this.targetHostile.getValue() && entity.isCreatureType(EnumCreatureType.MONSTER, false)) {
                RenderUtil.renderEntityBoundingBox(entity, this.hostileColor, this.colorAlpha.getValue());
                RenderUtil.renderEntityFilledBoundingBox(entity, this.hostileColor, this.colorAlpha.getValue() / 3.0f);
                continue;
            }
            if (!this.targetPassive.getValue() || !entity.isCreatureType(EnumCreatureType.AMBIENT, false) && !entity.isCreatureType(EnumCreatureType.WATER_CREATURE, false) && !entity.isCreatureType(EnumCreatureType.CREATURE, false)) continue;
            RenderUtil.renderEntityBoundingBox(entity, this.passiveColor, this.colorAlpha.getValue());
            RenderUtil.renderEntityFilledBoundingBox(entity, this.passiveColor, this.colorAlpha.getValue() / 3.0f);
        }
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }
}

