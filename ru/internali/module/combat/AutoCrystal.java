//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.settings.Setting;

public class AutoCrystal
extends Module {
    public float[] facing;
    public static int deley;

    public AutoCrystal() {
        super("AutoCrystal", "AutoBreakCrystal", Category.COMBAT);
        CatClient.instance.settingsManager.rSetting(new Setting("Range", this, 3.8, 0.0, 6.0, false));
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var7;
        double var4 = target.posX - Minecraft.getMinecraft().player.posX;
        double var5 = target.posZ - Minecraft.getMinecraft().player.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase)target;
            double s = target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY;
            var7 = var6.posY + (double)var6.getEyeHeight() - (Minecraft.getMinecraft().player.posY + (double)Minecraft.getMinecraft().player.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.getMinecraft().player.posY + (double)Minecraft.getMinecraft().player.getEyeHeight()) + 2.0;
        }
        double var8 = MathHelper.sqrt((double)(var4 * var4 + var5 * var5));
        float var9 = (float)(Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7 - (target instanceof EntityPlayer ? 0.25 : 0.0), var8) * 180.0 / Math.PI));
        float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float gcd = f * f * f * 1.2f;
        float pitch = AutoCrystal.updateRotation(Minecraft.getMinecraft().player.rotationPitch, var10, p_706253);
        float yaw = AutoCrystal.updateRotation(Minecraft.getMinecraft().player.rotationYaw, var9, p_706252);
        yaw -= yaw % gcd;
        pitch -= pitch % gcd;
        return new float[]{yaw, pitch};
    }

    public static float updateRotation(float current, float intended, float speed) {
        float f = MathHelper.wrapDegrees((float)(intended - current));
        if (f > speed) {
            f = speed;
        }
        if (f < -speed) {
            f = -speed;
        }
        return current + f;
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        float Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
        if (++deley == 0) {
            for (Entity e : AutoCrystal.mc.world.loadedEntityList) {
                if (!(e instanceof EntityEnderCrystal) || !(AutoCrystal.mc.player.getDistance(e) <= Range)) continue;
                this.facing = AutoCrystal.faceTarget(e, 360.0f, 360.0f, false);
                float f = this.facing[0];
                float f2 = this.facing[1];
                mc.getConnection().sendPacket((Packet)new CPacketPlayer.Rotation(f, f2, AutoCrystal.mc.player.onGround));
                AutoCrystal.mc.player.rotationYawHead = f;
                AutoCrystal.mc.player.renderYawOffset = f;
                AutoCrystal.mc.playerController.attackEntity((EntityPlayer)AutoCrystal.mc.player, e);
            }
        }
        if (deley >= 500) {
            deley = -1;
        }
    }
}

