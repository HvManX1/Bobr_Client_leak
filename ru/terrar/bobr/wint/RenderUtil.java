//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.AxisAlignedBB
 *  org.lwjgl.opengl.GL11
 */
package ru.terrar.bobr.wint;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import ru.terrar.bobr.wint.MinecraftHelper;

public class RenderUtil
implements MinecraftHelper {
    private static final Frustum frustrum = new Frustum();
    private static final double DOUBLE_PI = Math.PI * 2;

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static void renderItem(ItemStack itemStack, int x, int y) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
        mc.getRenderItem().renderItemOverlays(RenderUtil.mc.fontRenderer, itemStack, x, y);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.disableDepth();
    }

    public static void drawRectWithEdge(double x, double y, double width, double height, Color color, Color color2) {
        RenderUtil.drawRect(x, y, x + width, y + height, color.getRGB());
        int c = color2.getRGB();
        RenderUtil.drawRect(x - 1.0, y, x, y + height, c);
        RenderUtil.drawRect(x + width, y, x + width + 1.0, y + height, c);
        RenderUtil.drawRect(x - 1.0, y - 1.0, x + width + 1.0, y, c);
        RenderUtil.drawRect(x - 1.0, y + height, x + width + 1.0, y + height + 1.0, c);
    }

    public static void drawRoundedRect(double x, double y, double x1, double y1, int borderC, int insideC) {
        RenderUtil.drawRect(x + 0.5, y, x1 - 0.5, y + 0.5, insideC);
        RenderUtil.drawRect(x + 0.5, y1 - 0.5, x1 - 0.5, y1, insideC);
        RenderUtil.drawRect(x, y + 0.5, x1, y1 - 0.5, insideC);
    }

    public static void drawRoundedRect(int xCoord, int yCoord, int xSize, int ySize, int colour) {
        int width = xCoord + xSize;
        int height = yCoord + ySize;
        RenderUtil.drawRect(xCoord + 1, yCoord, width - 1, height, colour);
        RenderUtil.drawRect(xCoord, yCoord + 1, width, height - 1, colour);
    }

    public static void drawBoundingBox(AxisAlignedBB axisalignedbb) {
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.minX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.minZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.maxY, (double)axisalignedbb.maxZ);
        GL11.glVertex3d((double)axisalignedbb.maxX, (double)axisalignedbb.minY, (double)axisalignedbb.maxZ);
        GL11.glEnd();
    }

    public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
        int sections = 6;
        double dAngle = Math.PI * 2 / (double)sections;
        GL11.glPushAttrib((int)8192);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)6);
        for (int i = 0; i < sections; ++i) {
            float x = (float)((double)radius * Math.sin((double)i * dAngle));
            float y = (float)((double)radius * Math.cos((double)i * dAngle));
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            GL11.glVertex2f((float)((float)xx + x), (float)((float)yy + y));
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static final void drawSmoothRect(float left, float top, float right, float bottom, int color) {
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        RenderUtil.drawRect(left, top, right, bottom, color);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        RenderUtil.drawRect(left * 2.0f - 1.0f, top * 2.0f, left * 2.0f, bottom * 2.0f - 1.0f, color);
        RenderUtil.drawRect(left * 2.0f, top * 2.0f - 1.0f, right * 2.0f, top * 2.0f, color);
        RenderUtil.drawRect(right * 2.0f, top * 2.0f, right * 2.0f + 1.0f, bottom * 2.0f - 1.0f, color);
        RenderUtil.drawRect(left * 2.0f, bottom * 2.0f - 1.0f, right * 2.0f, bottom * 2.0f, color);
        GL11.glDisable((int)3042);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        GL11.glEnable((int)2848);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, (float)0.0f, (float)0.0f, (int)width, (int)height, (float)width, (float)height);
        RenderUtil.disableGL2D();
        GL11.glPopMatrix();
    }

    public static void drawCircle(float cx, double cy, float r, int num_segments, int c) {
        GL11.glPushMatrix();
        cx *= 2.0f;
        cy *= 2.0;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        float theta = (float)(6.2831852 / (double)num_segments);
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = r *= 2.0f;
        float y = 0.0f;
        GL11.glEnable((int)2848);
        RenderUtil.enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glBegin((int)2);
        for (int ii = 0; ii < num_segments; ++ii) {
            GL11.glVertex2f((float)(x + cx), (float)((float)((double)y + cy)));
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        GL11.glEnd();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        RenderUtil.disableGL2D();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glVertex2d((double)left, (double)top);
        GL11.glVertex2d((double)left, (double)bottom);
        GL11.glColor4f((float)f5, (float)f6, (float)f7, (float)f4);
        GL11.glVertex2d((double)right, (double)bottom);
        GL11.glVertex2d((double)right, (double)top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void relativeRect(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.color((float)f, (float)f1, (float)f2, (float)f3);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos((double)left, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)bottom, 0.0).endVertex();
        bufferBuilder.pos((double)right, (double)top, 0.0).endVertex();
        bufferBuilder.pos((double)left, (double)top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        RenderUtil.drawRect(left - (!borderIncludedInBounds ? borderWidth : 0.0), top - (!borderIncludedInBounds ? borderWidth : 0.0), right + (!borderIncludedInBounds ? borderWidth : 0.0), bottom + (!borderIncludedInBounds ? borderWidth : 0.0), borderColor);
        RenderUtil.drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0), top + (borderIncludedInBounds ? borderWidth : 0.0), right - (borderIncludedInBounds ? borderWidth : 0.0), bottom - (borderIncludedInBounds ? borderWidth : 0.0), insideColor);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.color((float)f, (float)f1, (float)f2, (float)f3);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(left, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, top, 0.0).endVertex();
        bufferBuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRectOpacity(double left, double top, double right, double bottom, float opacity) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.color((float)0.1f, (float)0.1f, (float)0.1f, (float)opacity);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(left, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, top, 0.0).endVertex();
        bufferBuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawNewRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)f, (float)f1, (float)f2, (float)f3);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(left, bottom, 0.0).endVertex();
        vertexbuffer.pos(right, bottom, 0.0).endVertex();
        vertexbuffer.pos(right, top, 0.0).endVertex();
        vertexbuffer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return RenderUtil.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    public static void drawLinesAroundPlayer(Entity entity, double radius, float partialTicks, int points, float width, int color) {
        GL11.glPushMatrix();
        RenderUtil.enableGL2D3();
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glDisable((int)2929);
        GL11.glLineWidth((float)width);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2929);
        GL11.glBegin((int)3);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - renderManager.viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - renderManager.viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - renderManager.viewerPosZ;
        RenderUtil.color228(color);
        for (int i = 0; i <= points; ++i) {
            GL11.glVertex3d((double)(x + radius * Math.cos((double)i * (Math.PI * 2) / (double)points)), (double)y, (double)(z + radius * Math.sin((double)i * (Math.PI * 2) / (double)points)));
        }
        GL11.glEnd();
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        RenderUtil.disableGL2D3();
        GL11.glPopMatrix();
    }

    public static void enableGL2D3() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void disableGL2D3() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void color228(int color) {
        GL11.glColor4ub((byte)((byte)(color >> 16 & 0xFF)), (byte)((byte)(color >> 8 & 0xFF)), (byte)((byte)(color & 0xFF)), (byte)((byte)(color >> 24 & 0xFF)));
    }

    private static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = mc.getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hex & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderUtil.drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        RenderUtil.drawRect(x + width, y, x1 - width, y + width, borderColor);
        RenderUtil.drawRect(x, y, x + width, y1, borderColor);
        RenderUtil.drawRect(x1 - width, y, x1, y1, borderColor);
        RenderUtil.drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
    }
}

