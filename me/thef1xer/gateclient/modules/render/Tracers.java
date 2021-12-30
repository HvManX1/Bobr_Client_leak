//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ActiveRenderInfo
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.thef1xer.gateclient.modules.render;

import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import me.thef1xer.gateclient.settings.impl.RGBSetting;
import me.thef1xer.gateclient.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Tracers
extends Module {
    public static final Tracers INSTANCE = new Tracers();
    public final BooleanSetting targetPlayer = new BooleanSetting("Players", "players", true);
    public final BooleanSetting targetHostile = new BooleanSetting("Monsters", "monsters", false);
    public final RGBSetting color = new RGBSetting("Tracer Color", "color", 255, 255, 255);
    public final FloatSetting colorAlpha = new FloatSetting("Color Alpha", "coloralpha", 1.0f, 0.0f, 1.0f);

    public Tracers() {
        super("Tracers", "tracers", Module.ModuleCategory.RENDER);
        this.targetPlayer.setParent("Entities to Target");
        this.targetHostile.setParent("Entities to Target");
        this.addSettings(this.targetPlayer, this.targetHostile, this.color, this.colorAlpha);
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
    public void onRender(RenderWorldLastEvent event) {
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            RenderManager rm = Minecraft.getMinecraft().getRenderManager();
            Vec3d playerVector = ActiveRenderInfo.getCameraPosition();
            GlStateManager.pushMatrix();
            GlStateManager.clear((int)256);
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
            GlStateManager.disableDepth();
            GlStateManager.glLineWidth((float)0.5f);
            for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
                if (!this.isTarget(entity) || entity == Minecraft.getMinecraft().getRenderViewEntity()) continue;
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                Vec3d entityPos = MathUtil.interpolateEntity(entity);
                buffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
                buffer.pos(playerVector.x, playerVector.y, playerVector.z).color((float)this.color.getRed() / 255.0f, (float)this.color.getGreen() / 255.0f, (float)this.color.getBlue() / 255.0f, this.colorAlpha.getValue()).endVertex();
                buffer.pos(entityPos.x - rm.viewerPosX, entityPos.y - rm.viewerPosY, entityPos.z - rm.viewerPosZ).color((float)this.color.getRed() / 255.0f, (float)this.color.getGreen() / 255.0f, (float)this.color.getBlue() / 255.0f, this.colorAlpha.getValue()).endVertex();
                tessellator.draw();
            }
            GlStateManager.enableDepth();
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
        }
    }

    public boolean isTarget(Entity entity) {
        if (this.targetPlayer.getValue() && entity instanceof EntityPlayer) {
            return true;
        }
        return this.targetHostile.getValue() && entity.isCreatureType(EnumCreatureType.MONSTER, false);
    }
}

