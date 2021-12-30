//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  org.lwjgl.opengl.GL11
 */
package ru.terrar.bobr.modules.hud;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.FloatSetting;
import ru.terrar.bobr.wint.RenderrUtil;

public class cartridgesHud
extends Module {
    public static final cartridgesHud INSTANCE = new cartridgesHud();
    public Minecraft mc = Minecraft.getMinecraft();
    private Entity target;
    private String hef;
    private Entity focusTarget;
    private boolean checks = false;
    private boolean old = false;
    private String enter;
    private int x;
    private int y;
    private static RenderItem kappita;
    private static final RenderItem itemRender;
    private final FontRenderer fr;
    ScaledResolution sr;
    public final FloatSetting posX;
    public final FloatSetting posY;
    private int STICK;
    private int BRICK;
    private int PRISMARINE_CRYSTALS;
    private int PRISMARINE_SHARD;
    private int SHULKER_SHELL;
    private int RABBIT_HIDE;
    private int GUNPOWDER;
    private int ARROW;
    private ItemStack STICK2;
    private ItemStack BRICK2;
    private ItemStack PRISMARINE_CRYSTALS2;
    private ItemStack PRISMARINE_SHARD2;
    private ItemStack SHULKER_SHELL2;
    private ItemStack RABBIT_HIDE2;
    private ItemStack GUNPOWDER2;
    private ItemStack ARROW2;

    public cartridgesHud() {
        super("cartridgesHud", "cartridgesHud", Module.ModuleCategory.HUD);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.sr = new ScaledResolution(this.mc);
        this.posX = new FloatSetting("PosX", "posX", 0.0f, 0.0f, this.sr.getScaledWidth());
        this.posY = new FloatSetting("PosY", "posY", 0.0f, 0.0f, this.sr.getScaledHeight());
        this.STICK = 0;
        this.BRICK = 0;
        this.PRISMARINE_CRYSTALS = 0;
        this.PRISMARINE_SHARD = 0;
        this.SHULKER_SHELL = 0;
        this.RABBIT_HIDE = 0;
        this.GUNPOWDER = 0;
        this.ARROW = 0;
        this.setEnabled(false);
        this.addSettings(this.posX, this.posY);
    }

    public void drawitem(ItemStack item, int x, int y, int count) {
        GlStateManager.enableDepth();
        cartridgesHud.itemRender.zLevel = 200.0f;
        this.fr.drawString("", 0, 0, Color.white.getRGB());
        RenderrUtil.drawSmoothRect(x - 5, y, x + 20, 25 + y, new Color(35, 35, 40, 230).getRGB());
        GlStateManager.resetColor();
        itemRender.renderItemAndEffectIntoGUI(item, x, y);
        itemRender.renderItemOverlayIntoGUI(this.mc.fontRenderer, item, x, y, "" + count);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
    }

    public void drawcard() {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        try {
            GlStateManager.enableTexture2D();
            GL11.glPushMatrix();
            GL11.glTranslated((double)this.posX.getValue(), (double)this.posY.getValue(), (double)0.0);
            for (int slot = 0; slot < Minecraft.getMinecraft().player.inventoryContainer.inventorySlots.size(); ++slot) {
                Item slotItem = ((Slot)Minecraft.getMinecraft().player.inventoryContainer.inventorySlots.get(slot)).getStack().getItem();
                ItemStack Item_to_render = ((Slot)Minecraft.getMinecraft().player.inventoryContainer.inventorySlots.get(slot)).getStack();
                if (slotItem == Items.STICK) {
                    this.STICK += Item_to_render.getCount();
                    this.STICK2 = Item_to_render;
                    continue;
                }
                if (slotItem == Items.BRICK) {
                    this.BRICK += Item_to_render.getCount();
                    this.BRICK2 = Item_to_render;
                    continue;
                }
                if (slotItem == Items.PRISMARINE_CRYSTALS) {
                    this.PRISMARINE_CRYSTALS += Item_to_render.getCount();
                    this.PRISMARINE_CRYSTALS2 = Item_to_render;
                    continue;
                }
                if (slotItem == Items.PRISMARINE_SHARD) {
                    this.PRISMARINE_SHARD += Item_to_render.getCount();
                    this.PRISMARINE_SHARD2 = Item_to_render;
                    continue;
                }
                if (slotItem == Items.SHULKER_SHELL) {
                    this.SHULKER_SHELL += Item_to_render.getCount();
                    this.SHULKER_SHELL2 = Item_to_render;
                    continue;
                }
                if (slotItem == Items.RABBIT_HIDE) {
                    this.RABBIT_HIDE += Item_to_render.getCount();
                    this.RABBIT_HIDE2 = Item_to_render;
                    continue;
                }
                if (slotItem == Items.GUNPOWDER) {
                    this.GUNPOWDER += Item_to_render.getCount();
                    this.GUNPOWDER2 = Item_to_render;
                    continue;
                }
                if (slotItem != Items.ARROW) continue;
                this.ARROW += Item_to_render.getCount();
                this.ARROW2 = Item_to_render;
            }
            int n = 0;
            if (this.STICK != 0) {
                this.drawitem(this.STICK2, 0, n * 25, this.STICK);
                ++n;
            }
            if (this.BRICK != 0) {
                this.drawitem(this.BRICK2, 0, n * 25, this.BRICK);
                ++n;
            }
            if (this.PRISMARINE_CRYSTALS != 0) {
                this.drawitem(this.PRISMARINE_CRYSTALS2, 0, n * 25, this.PRISMARINE_CRYSTALS);
                ++n;
            }
            if (this.PRISMARINE_SHARD != 0) {
                this.drawitem(this.PRISMARINE_SHARD2, 0, n * 25, this.PRISMARINE_SHARD);
                ++n;
            }
            if (this.SHULKER_SHELL != 0) {
                this.drawitem(this.SHULKER_SHELL2, 0, n * 25, this.SHULKER_SHELL);
                ++n;
            }
            if (this.RABBIT_HIDE != 0) {
                this.drawitem(this.RABBIT_HIDE2, 0, n * 25, this.RABBIT_HIDE);
                ++n;
            }
            if (this.GUNPOWDER != 0) {
                this.drawitem(this.GUNPOWDER2, 0, n * 25, this.GUNPOWDER);
                ++n;
            }
            if (this.ARROW != 0) {
                this.drawitem(this.ARROW2, 0, n * 25, this.ARROW);
                ++n;
            }
            this.STICK = 0;
            this.BRICK = 0;
            this.PRISMARINE_CRYSTALS = 0;
            this.PRISMARINE_SHARD = 0;
            this.SHULKER_SHELL = 0;
            this.RABBIT_HIDE = 0;
            this.GUNPOWDER = 0;
            this.ARROW = 0;
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
            GL11.glPopMatrix();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        itemRender = Minecraft.getMinecraft().getRenderItem();
    }
}

