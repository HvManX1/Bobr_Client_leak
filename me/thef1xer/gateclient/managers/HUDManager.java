/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Pre
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.thef1xer.gateclient.managers;

import java.io.IOException;
import me.thef1xer.gateclient.modules.hud.Coords;
import me.thef1xer.gateclient.modules.hud.ModuleList;
import me.thef1xer.gateclient.modules.hud.PlayerRadar;
import me.thef1xer.gateclient.modules.hud.TargetHud;
import me.thef1xer.gateclient.modules.hud.Watermark;
import me.thef1xer.gateclient.modules.render.NoOverlay;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUDManager {
    public void init() {
        ModuleList.INSTANCE.sortList();
    }

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent event) throws IOException {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            if (ModuleList.INSTANCE.isEnabled()) {
                ModuleList.INSTANCE.drawList(event.getResolution());
            }
            if (Coords.INSTANCE.isEnabled()) {
                Coords.INSTANCE.drawCoords(event.getResolution());
            }
            if (Watermark.INSTANCE.isEnabled()) {
                Watermark.INSTANCE.drawWatermark();
            }
            if (TargetHud.INSTANCE.isEnabled()) {
                TargetHud.INSTANCE.drawtartet();
            }
            if (PlayerRadar.INSTANCE.isEnabled()) {
                PlayerRadar.INSTANCE.drawradar();
            }
        }
        if (NoOverlay.INSTANCE.isEnabled() && event instanceof RenderGameOverlayEvent.Pre && (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO || event.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS)) {
            event.setCanceled(false);
            event.setCanceled(false);
        }
    }
}

