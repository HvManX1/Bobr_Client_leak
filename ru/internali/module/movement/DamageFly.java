//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.settings.Setting;

public class DamageFly
extends Module {
    public DamageFly() {
        super("DamageFly", "fly", Category.MOVEMENT);
        CatClient.instance.settingsManager.rSetting(new Setting("Horizontal", this, 300.0, 0.0, 600.0, true));
        CatClient.instance.settingsManager.rSetting(new Setting("Vertical", this, 300.0, 0.0, 600.0, true));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        float horizontal = (float)CatClient.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
        float vertical = (float)CatClient.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
        if (DamageFly.mc.player.hurtTime == DamageFly.mc.player.maxHurtTime && DamageFly.mc.player.maxHurtTime > 0) {
            DamageFly.mc.player.jump();
            EntityPlayerSP player = DamageFly.mc.player;
            player.motionX *= (double)(horizontal / 100.0f);
            EntityPlayerSP player2 = DamageFly.mc.player;
            player2.motionY *= (double)(vertical / 100.0f);
            EntityPlayerSP player3 = DamageFly.mc.player;
            player3.motionZ *= (double)(horizontal / 100.0f);
        }
    }
}

