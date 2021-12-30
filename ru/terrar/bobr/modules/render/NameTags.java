//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package ru.terrar.bobr.modules.render;

import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.Setting;
import ru.terrar.bobr.settings.impl.BooleanSetting;
import ru.terrar.bobr.settings.impl.EnumSetting;

public class NameTags
extends Module {
    public static final NameTags INSTANCE = new NameTags();
    private Minecraft mc = Minecraft.getMinecraft();
    public final EnumSetting mode = new EnumSetting("Mode", "Mode", Modes.values(), Modes.Simple);
    public final BooleanSetting Items = new BooleanSetting("Items", "Items", true);
    private FontRenderer fontRenderer;

    public NameTags() {
        super("NameTags", "NameTags", Module.ModuleCategory.RENDER);
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
        this.addSettings(new Setting[0]);
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
        for (Entity e : this.mc.world.loadedEntityList) {
            if (!(e instanceof EntityPlayer) || e == this.mc.player) continue;
            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)event.getPartialTicks() - this.mc.getRenderManager().viewerPosX;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)event.getPartialTicks() - this.mc.getRenderManager().viewerPosY;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)event.getPartialTicks() - this.mc.getRenderManager().viewerPosZ;
            GL11.glPushMatrix();
            GL11.glDisable((int)2929);
            GL11.glDisable((int)3553);
            GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            float size = Math.min(Math.max(1.2f * (this.mc.player.getDistance(e) * 0.15f), 1.25f), 6.0f) * 0.015f;
            GL11.glTranslatef((float)((float)x), (float)((float)y + e.height + 0.6f), (float)((float)z));
            GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)(-this.mc.getRenderManager().playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)this.mc.getRenderManager().playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScalef((float)(-size), (float)(-size), (float)(-size));
            int health = (int)(((EntityPlayer)e).getHealth() / ((EntityPlayer)e).getMaxHealth() * 100.0f);
            Gui.drawRect((int)(-this.mc.fontRenderer.getStringWidth(e.getName() + " " + health + "%") / 2 - 2), (int)-2, (int)(this.mc.fontRenderer.getStringWidth(e.getName()) / 2 + 16), (int)10, (int)Integer.MIN_VALUE);
            float light = this.mc.fontRenderer.getStringWidth(e.getName() + " " + (Object)TextFormatting.GREEN + health + "%");
            this.mc.fontRenderer.drawStringWithShadow(e.getName() + " " + (Object)TextFormatting.GREEN + health + "%", 0.0f - light / 2.0f, 1.0f, -1);
            int posX = -this.mc.fontRenderer.getStringWidth(e.getName()) / 2 - 8;
            if (Item.getIdFromItem((Item)((EntityPlayer)e).inventory.getCurrentItem().getItem()) != 0) {
                this.mc.getRenderItem().zLevel = -100.0f;
                this.mc.getRenderItem().renderItemIntoGUI(new ItemStack(((EntityPlayer)e).inventory.getCurrentItem().getItem()), posX - 2, -20);
                this.mc.getRenderItem().zLevel = 0.0f;
                int posY = -30;
                Map enchantments = EnchantmentHelper.getEnchantments((ItemStack)((EntityPlayer)e).inventory.getCurrentItem());
                for (Enchantment enchantment : enchantments.keySet()) {
                    int level = EnchantmentHelper.getEnchantmentLevel((Enchantment)enchantment, (ItemStack)((EntityPlayer)e).inventory.getCurrentItem());
                    light = this.mc.fontRenderer.getStringWidth(String.valueOf(enchantment.getName().substring(12).charAt(0)).toUpperCase() + level);
                    this.fontRenderer.drawStringWithShadow(String.valueOf(enchantment.getName().substring(12).charAt(0)).toUpperCase() + level, (float)(posX + 6) - light / 2.0f, (float)posY, -1);
                    posY -= 12;
                }
                posX += 15;
            }
            for (ItemStack item : e.getArmorInventoryList()) {
                this.mc.getRenderItem().zLevel = -100.0f;
                this.mc.getRenderItem().renderItemIntoGUI(new ItemStack(item.getItem()), posX, -20);
                this.mc.getRenderItem().zLevel = 0.0f;
                int posY = -30;
                Map enchantments = EnchantmentHelper.getEnchantments((ItemStack)item);
                for (Enchantment enchantment : enchantments.keySet()) {
                    int level = EnchantmentHelper.getEnchantmentLevel((Enchantment)enchantment, (ItemStack)item);
                    light = this.mc.fontRenderer.getStringWidth(String.valueOf(enchantment.getName().substring(12).charAt(0)).toUpperCase() + level);
                    this.fontRenderer.drawStringWithShadow(String.valueOf(enchantment.getName().substring(12).charAt(0)).toUpperCase() + level, (float)(posX + 9) - light / 2.0f, (float)posY, -1);
                    posY -= 12;
                }
                posX += 17;
            }
            int gapples = 0;
            if (Item.getIdFromItem((Item)((EntityPlayer)e).inventory.getCurrentItem().getItem()) == 322) {
                gapples = ((EntityPlayer)e).inventory.getCurrentItem().getCount();
            } else if (Item.getIdFromItem((Item)((EntityPlayer)e).getHeldItemOffhand().getItem()) == 322) {
                gapples = ((EntityPlayer)e).getHeldItemOffhand().getCount();
            }
            if (gapples > 0) {
                this.mc.getRenderItem().zLevel = -100.0f;
                this.mc.getRenderItem().renderItemIntoGUI(new ItemStack(net.minecraft.init.Items.GOLDEN_APPLE), posX, -20);
                this.mc.getRenderItem().zLevel = 0.0f;
                this.fontRenderer.drawStringWithShadow(String.valueOf(gapples), (float)(posX + 9), -30.0f, -1);
            }
            GL11.glEnable((int)2929);
            GL11.glPopMatrix();
        }
    }

    public static enum Modes {
        Simple("Simple"),
        Box("Box");

        private final String name;

        private Modes(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

