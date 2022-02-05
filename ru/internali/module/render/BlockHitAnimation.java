//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.client.event.RenderSpecificHandEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.module.Category;
import ru.internali.module.Module;

public class BlockHitAnimation
extends Module {
    public static List<String> listA = new ArrayList<String>();

    public BlockHitAnimation() {
        super("BlockHitAnimation", "Show Tracers to players", Category.RENDER);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @SubscribeEvent
    public void onRender(RenderSpecificHandEvent event) {
        if (event.getHand() == EnumHand.MAIN_HAND) {
            GlStateManager.translate((double)0.29, (double)0.1, (double)-0.31);
        }
    }
}

