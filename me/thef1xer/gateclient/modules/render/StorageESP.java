//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityEnderChest
 *  net.minecraft.tileentity.TileEntityShulkerBox
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.thef1xer.gateclient.modules.render;

import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import me.thef1xer.gateclient.settings.impl.RGBSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StorageESP
extends Module {
    public static final StorageESP INSTANCE = new StorageESP();
    public final RGBSetting chestColor = new RGBSetting("Chest Color", "chestcolor", 50, 50, 190);
    public final RGBSetting shulkerColor = new RGBSetting("Shulker Color", "shulkercolor", 255, 80, 240);
    public final RGBSetting enderChestColor = new RGBSetting("E-Chest Color", "echestcolor", 130, 0, 150);
    public final FloatSetting colorAlpha = new FloatSetting("Color Alpha", "coloralpha", 1.0f, 0.0f, 1.0f);

    public StorageESP() {
        super("Storage ESP", "storageesp", Module.ModuleCategory.RENDER);
        this.chestColor.setParent("Color");
        this.shulkerColor.setParent("Color");
        this.enderChestColor.setParent("Color");
        this.addSettings(this.chestColor, this.shulkerColor, this.enderChestColor, this.colorAlpha);
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
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.disableDepth();
        for (TileEntity entity : Minecraft.getMinecraft().world.loadedTileEntityList) {
            AxisAlignedBB bb;
            if (entity instanceof TileEntityChest) {
                bb = new AxisAlignedBB(entity.getPos()).offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);
                RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)bb, (float)((float)this.chestColor.getRed() / 255.0f), (float)((float)this.chestColor.getGreen() / 255.0f), (float)((float)this.chestColor.getBlue() / 255.0f), (float)this.colorAlpha.getValue());
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)((float)this.chestColor.getRed() / 255.0f), (float)((float)this.chestColor.getGreen() / 255.0f), (float)((float)this.chestColor.getBlue() / 255.0f), (float)(this.colorAlpha.getValue() / 3.0f));
                continue;
            }
            if (entity instanceof TileEntityShulkerBox) {
                bb = new AxisAlignedBB(entity.getPos()).offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);
                RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)bb, (float)((float)this.shulkerColor.getRed() / 255.0f), (float)((float)this.shulkerColor.getGreen() / 255.0f), (float)((float)this.shulkerColor.getBlue() / 255.0f), (float)this.colorAlpha.getValue());
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)((float)this.shulkerColor.getRed() / 255.0f), (float)((float)this.shulkerColor.getGreen() / 255.0f), (float)((float)this.shulkerColor.getBlue() / 255.0f), (float)(this.colorAlpha.getValue() / 3.0f));
                continue;
            }
            if (!(entity instanceof TileEntityEnderChest)) continue;
            bb = new AxisAlignedBB(entity.getPos()).offset(-rm.viewerPosX, -rm.viewerPosY, -rm.viewerPosZ);
            RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)bb, (float)((float)this.enderChestColor.getRed() / 255.0f), (float)((float)this.enderChestColor.getGreen() / 255.0f), (float)((float)this.enderChestColor.getBlue() / 255.0f), (float)this.colorAlpha.getValue());
            RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)((float)this.enderChestColor.getRed() / 255.0f), (float)((float)this.enderChestColor.getGreen() / 255.0f), (float)((float)this.enderChestColor.getBlue() / 255.0f), (float)(this.colorAlpha.getValue() / 3.0f));
        }
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }
}

