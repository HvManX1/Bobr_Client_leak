//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagList
 *  org.lwjgl.opengl.GL11
 */
package me.thef1xer.gateclient.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.opengl.GL11;

public class ItemStackRenderHelper {
    public static void drawitemStackEnchants(ItemStack itemStack, int n, int n2) {
        NBTTagList nBTTagList = itemStack.getEnchantmentTagList();
        if (nBTTagList != null) {
            int n3 = 0;
            for (int i = 0; i < nBTTagList.tagCount(); ++i) {
                short s = nBTTagList.getCompoundTagAt(i).getShort("id");
                short s2 = nBTTagList.getCompoundTagAt(i).getShort("lvl");
                Enchantment enchantment = Enchantment.getEnchantmentByID((int)s);
                if (enchantment == null) continue;
                Enchantment enchantment2 = enchantment;
                String string = enchantment2.getTranslatedName((int)s2).substring(0, 2).toLowerCase();
                String[] arrstring = new String[]{"Efficiency", "Unbreaking", "Sharpness", "FireAspect", ""};
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(String.valueOf(new StringBuilder().append(String.valueOf(String.valueOf(string))).append("\u00a7b").append(s2)), (float)n, (float)(n2 + n3), -5592406);
                n3 += Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
                if (i <= 4) continue;
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("\u00a7f+ others", (float)n, (float)(n2 + n3), -5592406);
                break;
            }
        }
    }

    public static final void finish3DOGLConstants() {
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
    }

    public static void renderItemStack(ItemStack itemStack, int n, int n2) {
        GL11.glPushMatrix();
        GL11.glDepthMask((boolean)true);
        GlStateManager.clear((int)256);
        GlStateManager.disableLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = -150.0f;
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(itemStack, n, n2);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRenderer, itemStack, n, n2);
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.disableDepth();
        ItemStackRenderHelper.drawitemStackEnchants(itemStack, n * 2, n2 * 2);
        GlStateManager.enableDepth();
        GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
        GlStateManager.enableLighting();
        GL11.glPopMatrix();
    }

    public static final void start3DOGLConstants() {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
    }
}

