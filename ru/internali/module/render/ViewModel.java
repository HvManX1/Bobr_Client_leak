/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.RenderSpecificHandEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package ru.internali.module.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.settings.Setting;

public class ViewModel
extends Module {
    public static List<String> listA = new ArrayList<String>();

    public ViewModel() {
        super("ViewModel", "Show Tracers to players", Category.RENDER);
        CatClient.instance.settingsManager.rSetting(new Setting("PosX", this, 0.0, -2.0, 2.0, false));
        CatClient.instance.settingsManager.rSetting(new Setting("PosY", this, 0.0, -2.0, 2.0, false));
        CatClient.instance.settingsManager.rSetting(new Setting("PosZ", this, 0.0, -2.0, 2.0, false));
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
        float PosX = (float)CatClient.instance.settingsManager.getSettingByName(this, "PosX").getValDouble();
        float PosY = (float)CatClient.instance.settingsManager.getSettingByName(this, "PosY").getValDouble();
        float PosZ = (float)CatClient.instance.settingsManager.getSettingByName(this, "PosZ").getValDouble();
        GL11.glTranslated((double)PosX, (double)PosY, (double)PosZ);
    }
}

