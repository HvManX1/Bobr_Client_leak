//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.combat;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.settings.Setting;

public class Velocity
extends Module {
    public Velocity() {
        super("Anti Knockback", "i hate being knocked back", Category.COMBAT);
        CatClient.instance.settingsManager.rSetting(new Setting("Horizontal", this, 90.0, 0.0, 100.0, true));
        CatClient.instance.settingsManager.rSetting(new Setting("Vertical", this, 100.0, 0.0, 100.0, true));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        float horizontal = (float)CatClient.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
        float vertical = (float)CatClient.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
        if (Velocity.mc.player.hurtTime == Velocity.mc.player.maxHurtTime && Velocity.mc.player.maxHurtTime > 0) {
            Velocity.mc.player.motionX *= (double)(horizontal / 100.0f);
            Velocity.mc.player.motionY *= (double)(vertical / 100.0f);
            Velocity.mc.player.motionZ *= (double)(horizontal / 100.0f);
        }
    }
}

