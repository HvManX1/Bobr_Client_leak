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
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.settings.Setting;

public class Tracers
extends Module {
    public static List<String> listA = new ArrayList<String>();

    public Tracers() {
        super("Tracers", "Show Tracers to players", Category.RENDER);
        CatClient.instance.settingsManager.rSetting(new Setting("Alpha", this, 0.7, 0.0, 1.0, false));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public static Vec3d interpolateEntity(Entity entity) {
        double partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            float Alpha = (float)CatClient.instance.settingsManager.getSettingByName(this, "Alpha").getValDouble();
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
            GlStateManager.color((float)CatClient.getClientColor().getRed(), (float)CatClient.getClientColor().getGreen(), (float)CatClient.getClientColor().getBlue());
            for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
                if (!(entity instanceof EntityPlayer) || entity == Tracers.mc.player) continue;
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                Vec3d entityPos = Tracers.interpolateEntity(entity);
                buffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
                buffer.pos(playerVector.x, playerVector.y, playerVector.z).color(255.0f, 255.0f, 255.0f, Alpha).endVertex();
                buffer.pos(entityPos.x - rm.viewerPosX, entityPos.y - rm.viewerPosY, entityPos.z - rm.viewerPosZ).color(255.0f, 255.0f, 255.0f, Alpha).endVertex();
                tessellator.draw();
            }
            GlStateManager.enableDepth();
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
        }
    }
}

