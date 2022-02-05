//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package ru.internali.module.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;

public class Chams
extends Module {
    public Chams() {
        super("Chams", "Chams", Category.RENDER);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (!(entity instanceof EntityPlayer) || entity == Minecraft.getMinecraft().getRenderViewEntity()) continue;
            RenderHelper.enableStandardItemLighting();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getRenderPartialTicks();
            double d = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getRenderPartialTicks();
            double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getRenderPartialTicks();
            float f = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * mc.getRenderPartialTicks();
            Chams.mc.entityRenderer.disableLightmap();
            GlStateManager.color((float)CatClient.getClientColor().getRed(), (float)CatClient.getClientColor().getGreen(), (float)CatClient.getClientColor().getBlue());
            mc.getRenderManager().renderEntity(entity, d3 - Chams.mc.getRenderManager().viewerPosX, d - Chams.mc.getRenderManager().viewerPosY, d2 - Chams.mc.getRenderManager().viewerPosZ, f, mc.getRenderPartialTicks(), false);
            Chams.mc.entityRenderer.enableLightmap();
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2929);
            RenderHelper.disableStandardItemLighting();
        }
    }
}

