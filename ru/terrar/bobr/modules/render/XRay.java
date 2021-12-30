//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Blocks
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.terrar.bobr.modules.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.terrar.bobr.events.GetAmbientOcclusionLightValueEvent;
import ru.terrar.bobr.events.RenderModelEvent;
import ru.terrar.bobr.events.SetOpaqueCubeEvent;
import ru.terrar.bobr.events.ShouldSideBeRenderedEvent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.modules.render.FullBright;

public class XRay
extends Module {
    public static final XRay INSTANCE = new XRay();
    private float lastGamma;

    public XRay() {
        super("XRay", "xray", Module.ModuleCategory.RENDER);
        this.lastGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
        this.lastGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
        Minecraft.getMinecraft().gameSettings.gammaSetting = 10000.0f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
        if (!FullBright.INSTANCE.isEnabled()) {
            Minecraft.getMinecraft().gameSettings.gammaSetting = this.lastGamma;
        }
    }

    @SubscribeEvent
    public void onRenderModel(RenderModelEvent event) {
        if (!this.isXrayBlock(event.getState().getBlock())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onSetOpaqueCube(SetOpaqueCubeEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onSideRendered(ShouldSideBeRenderedEvent event) {
        event.setShouldBeRendered(this.isXrayBlock(event.getBlockState().getBlock()));
    }

    @SubscribeEvent
    public void onGetAmbientOcclusion(GetAmbientOcclusionLightValueEvent event) {
        event.setLightValue(1.0f);
    }

    public boolean isXrayBlock(Block block) {
        return block == Blocks.DIAMOND_ORE || block == Blocks.EMERALD_ORE || block == Blocks.GOLD_BLOCK || block == Blocks.IRON_ORE || block == Blocks.REDSTONE_ORE || block == Blocks.LIT_REDSTONE_ORE || block == Blocks.LAPIS_ORE || block == Blocks.COAL_ORE;
    }
}

