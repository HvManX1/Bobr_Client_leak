//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.Vec3d
 */
package me.thef1xer.gateclient.util;

import me.thef1xer.gateclient.settings.impl.RGBSetting;
import me.thef1xer.gateclient.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class RenderUtil {
    public static void renderEntityBoundingBox(Entity entity, float red, float green, float blue, float alpha) {
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        Vec3d entityPos = MathUtil.interpolateEntity(entity);
        AxisAlignedBB bb = new AxisAlignedBB(entityPos.x - (double)(entity.width / 2.0f), entityPos.y, entityPos.z - (double)(entity.width / 2.0f), entityPos.x + (double)(entity.width / 2.0f), entityPos.y + (double)entity.height, entityPos.z + (double)(entity.width / 2.0f)).offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);
        RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)bb, (float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void renderEntityBoundingBox(Entity entity, RGBSetting color, float alpha) {
        RenderUtil.renderEntityBoundingBox(entity, (float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, alpha);
    }

    public static void renderEntityFilledBoundingBox(Entity entity, float red, float green, float blue, float alpha) {
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        Vec3d entityPos = MathUtil.interpolateEntity(entity);
        AxisAlignedBB bb = new AxisAlignedBB(entityPos.x - (double)(entity.width / 2.0f), entityPos.y, entityPos.z - (double)(entity.width / 2.0f), entityPos.x + (double)(entity.width / 2.0f), entityPos.y + (double)entity.height, entityPos.z + (double)(entity.width / 2.0f)).offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);
        RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void renderEntityFilledBoundingBox(Entity entity, RGBSetting color, float alpha) {
        RenderUtil.renderEntityFilledBoundingBox(entity, (float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, alpha);
    }

    public static void draw2DTriangleDown(double x, double y, double width, double height, float red, float green, float blue, float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.color((float)red, (float)green, (float)blue, (float)alpha);
        buffer.begin(4, DefaultVertexFormats.POSITION);
        buffer.pos(x, y, 0.0).endVertex();
        buffer.pos(x + width / 2.0, y + height, 0.0).endVertex();
        buffer.pos(x + width, y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void draw2DTriangleRight(double x, double y, double width, double height, float red, float green, float blue, float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.color((float)red, (float)green, (float)blue, (float)alpha);
        buffer.begin(4, DefaultVertexFormats.POSITION);
        buffer.pos(x, y, 0.0).endVertex();
        buffer.pos(x, y + height, 0.0).endVertex();
        buffer.pos(x + width, y + height / 2.0, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void draw2DRect(double x, double y, double width, double height, float red, float green, float blue, float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.color((float)red, (float)green, (float)blue, (float)alpha);
        buffer.begin(5, DefaultVertexFormats.POSITION);
        buffer.pos(x, y, 0.0).endVertex();
        buffer.pos(x, y + height, 0.0).endVertex();
        buffer.pos(x + width, y, 0.0).endVertex();
        buffer.pos(x + width, y + height, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void draw2DRectLines(double x, double y, double width, double height, float red, float green, float blue, float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.color((float)red, (float)green, (float)blue, (float)alpha);
        buffer.begin(2, DefaultVertexFormats.POSITION);
        buffer.pos(x, y, 0.0).endVertex();
        buffer.pos(x, y + height, 0.0).endVertex();
        buffer.pos(x + width, y + height, 0.0).endVertex();
        buffer.pos(x + width, y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}

