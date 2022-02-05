//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.module.combat.AntiBot;
import ru.internali.settings.Setting;
import ru.internali.utils.FriendManager;

public class HitBoxMod
extends Module {
    public HitBoxMod() {
        super("HitBoxMod", "big hitbox", Category.COMBAT);
        CatClient.instance.settingsManager.rSetting(new Setting("Horizontal", this, 0.1, 0.0, 3.0, false));
        CatClient.instance.settingsManager.rSetting(new Setting("Vertical", this, 0.1, 0.0, 3.0, false));
    }

    public HitBox createHitBox(Entity entity) {
        return new HitBox(0.6f, 1.8f);
    }

    public void changeEntityHitBox(Entity entity, float width, float height) {
        entity.width = width;
        entity.height = height;
        double d = (double)width / 2.0;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d, entity.posY, entity.posZ - d, entity.posX + d, entity.posY + (double)entity.height, d + entity.posZ));
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        float horizontal = (float)CatClient.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
        float vertical = (float)CatClient.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
                if (entity == null || entity == Minecraft.getMinecraft().player || !(entity instanceof EntityPlayer) || AntiBot.isBot(entity.getName()) || FriendManager.isFriend(entity.getName())) continue;
                HitBox hitbox = this.createHitBox(entity);
                this.changeEntityHitBox(entity, hitbox.width + horizontal, hitbox.height + vertical);
            }
        }
    }

    private static class HitBox {
        public float width;
        public float height;

        public HitBox(float width, float height) {
            this.width = width;
            this.height = height;
        }
    }
}

