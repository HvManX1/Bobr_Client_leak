//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package ru.terrar.bobr.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.FloatSetting;
import ru.terrar.bobr.settings.impl.RGBSetting;

public class BlockOverlay
extends Module {
    public static final BlockOverlay INSTANCE = new BlockOverlay();
    private float lastGamma;
    public final RGBSetting color = new RGBSetting("Color", "color", 50, 50, 190);
    public final FloatSetting colorAlpha = new FloatSetting("Color Alpha", "coloralpha", 1.0f, 0.0f, 1.0f);

    public BlockOverlay() {
        super("Block Overlay", "BlockOverlay", Module.ModuleCategory.RENDER);
        this.color.setParent("Color");
        this.lastGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
        this.addSettings(this.color, this.colorAlpha);
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

    public static AxisAlignedBB Standardbb(BlockPos pos) {
        double renderPosX = (double)pos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double renderPosY = (double)pos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double renderPosZ = (double)pos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        return new AxisAlignedBB(renderPosX, renderPosY, renderPosZ, renderPosX + 1.0, renderPosY + 1.0, renderPosZ + 1.0);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        if (Minecraft.getMinecraft().objectMouseOver != null) {
            BlockPos blockPos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
            RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)BlockOverlay.Standardbb(blockPos), (float)((float)this.color.getRed() / 255.0f), (float)((float)this.color.getGreen() / 255.0f), (float)((float)this.color.getBlue() / 255.0f), (float)this.colorAlpha.getValue());
            RenderGlobal.renderFilledBox((AxisAlignedBB)BlockOverlay.Standardbb(blockPos), (float)((float)this.color.getRed() / 255.0f), (float)((float)this.color.getGreen() / 255.0f), (float)((float)this.color.getBlue() / 255.0f), (float)(this.colorAlpha.getValue() / 3.0f));
        }
    }
}

