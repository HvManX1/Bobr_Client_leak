//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.combat;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.settings.Setting;
import ru.internali.utils.FriendManager;
import ru.internali.utils.TimerUtils;

public class AimPistol
extends Module {
    public float[] facing;
    private float[] old_pred;
    public TimerUtils timer = new TimerUtils();
    private int life_time_pred;
    public Entity target;

    public AimPistol() {
        super("AimPistol", "aim to players", Category.COMBAT);
        CatClient.instance.settingsManager.rSetting(new Setting("Range", this, 300.0, 0.0, 300.0, true));
        CatClient.instance.settingsManager.rSetting(new Setting("Predict", this, 3.5, 0.0, 6.0, false));
        CatClient.instance.settingsManager.rSetting(new Setting("Walls", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("AutoShoot", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("AutoAim", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("AutoShootDeley", this, 200.0, 0.0, 500.0, true));
    }

    public boolean isTarget(Entity entity) {
        float Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
        return entity != Minecraft.getMinecraft().player && entity != Minecraft.getMinecraft().getRenderViewEntity() && Minecraft.getMinecraft().player.getDistanceSq(entity) <= Math.pow(Range, 2.0) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var7;
        double var4 = target.posX - AimPistol.mc.player.posX;
        double var5 = target.posZ - AimPistol.mc.player.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase)target;
            var7 = var6.posY + (double)var6.getEyeHeight() - (AimPistol.mc.player.posY + (double)AimPistol.mc.player.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (AimPistol.mc.player.posY + (double)AimPistol.mc.player.getEyeHeight());
        }
        double var8 = MathHelper.sqrt((double)(var4 * var4 + var5 * var5));
        float var9 = (float)(Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7 - (target instanceof EntityPlayer ? 0.25 : 0.0), var8) * 180.0 / Math.PI));
        float f = AimPistol.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float gcd = f * f * f * 1.2f;
        float pitch = AimPistol.updateRotation(AimPistol.mc.player.rotationPitch, var10, p_706253);
        float yaw = AimPistol.updateRotation(AimPistol.mc.player.rotationYaw, var9, p_706252);
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

    public float[] facePredict(Entity target, double old_posX, double old_posZ) {
        float Predict = (float)CatClient.instance.settingsManager.getSettingByName(this, "Predict").getValDouble();
        float new_posX = (float)target.posX;
        float new_posZ = (float)target.posZ;
        float differenceX = (float)(new_posX >= 0.0f ? (double)Math.abs(new_posX) - Math.abs(old_posX) : Math.abs(old_posX) - (double)Math.abs(new_posX));
        float differenceZ = (float)(new_posZ >= 0.0f ? (double)Math.abs(new_posZ) - Math.abs(old_posZ) : Math.abs(old_posZ) - (double)Math.abs(new_posZ));
        if (differenceX != 0.0f) {
            if (3 != this.life_time_pred) {
                ++this.life_time_pred;
            }
        } else if (0 != this.life_time_pred) {
            differenceX = this.old_pred[0];
            --this.life_time_pred;
        }
        if (differenceZ != 0.0f) {
            if (3 != this.life_time_pred) {
                ++this.life_time_pred;
            }
        } else if (0 != this.life_time_pred) {
            differenceZ = this.old_pred[1];
            --this.life_time_pred;
        }
        float dist = AimPistol.mc.player.getDistance(target);
        if (differenceX != 0.0f && differenceZ != 0.0f) {
            differenceX = differenceX >= 0.0f ? (differenceX += dist / 100.0f) : (differenceX -= dist / 100.0f);
            differenceZ = differenceZ >= 0.0f ? (differenceZ += dist / 100.0f) : (differenceZ -= dist / 100.0f);
        }
        this.old_pred = new float[]{differenceX, differenceZ};
        float returnX = new_posX + differenceX * Predict;
        float returnZ = new_posZ + differenceZ * Predict;
        return new float[]{returnX, returnZ};
    }

    public static float[] faceCords(float posX, float posY, float posZ, float p_706252, float p_706253, boolean miss) {
        double var4 = (double)posX - Minecraft.getMinecraft().player.posX;
        double var5 = (double)posZ - Minecraft.getMinecraft().player.posZ;
        double var7 = (double)posY + (double)1.62f - (Minecraft.getMinecraft().player.posY + (double)Minecraft.getMinecraft().player.getEyeHeight());
        double var8 = MathHelper.sqrt((double)(var4 * var4 + var5 * var5));
        float var9 = (float)(Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7 - 0.25, var8) * 180.0 / Math.PI));
        float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float gcd = f * f * f * 1.2f;
        float pitch = AimPistol.updateRotation(Minecraft.getMinecraft().player.rotationPitch, var10, p_706253);
        float yaw = AimPistol.updateRotation(Minecraft.getMinecraft().player.rotationYaw, var9, p_706252);
        yaw -= yaw % gcd;
        pitch -= pitch % gcd;
        return new float[]{yaw, pitch};
    }

    public boolean attackCheck(Entity entity) {
        boolean Walls = CatClient.instance.settingsManager.getSettingByName(this, "Walls").getValBoolean();
        if (Walls) {
            return !entity.isInvisible() && entity instanceof EntityPlayer && !FriendManager.isFriend(entity.getName());
        }
        if (!Walls && AimPistol.mc.player.canEntityBeSeen(entity)) {
            return !entity.isInvisible() && entity instanceof EntityPlayer && !FriendManager.isFriend(entity.getName());
        }
        return false;
    }

    private boolean lambdagetTarget(Entity entity) {
        return this.attackCheck(entity);
    }

    public Entity getTarget() {
        block5: {
            block4: {
                if (AimPistol.mc.player == null) break block4;
                if (!AimPistol.mc.player.isDead) break block5;
            }
            return null;
        }
        List list = AimPistol.mc.world.loadedEntityList.stream().filter(entity -> entity != AimPistol.mc.player).filter(entity -> AimPistol.mc.player.getDistance(entity) <= 200.0f).filter(entity -> !entity.isDead).filter(this::lambdagetTarget).sorted(Comparator.comparing(entity -> Float.valueOf(AimPistol.mc.player.getDistance(entity)))).collect(Collectors.toList());
        if (list.size() > 0) {
            return (Entity)list.get(0);
        }
        return null;
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        float Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
        boolean Walls = CatClient.instance.settingsManager.getSettingByName(this, "Walls").getValBoolean();
        boolean AutoShoot = CatClient.instance.settingsManager.getSettingByName(this, "AutoShoot").getValBoolean();
        boolean AutoAim = CatClient.instance.settingsManager.getSettingByName(this, "AutoAim").getValBoolean();
        float AutoShootDeley = (float)CatClient.instance.settingsManager.getSettingByName(this, "AutoShootDeley").getValDouble();
        this.target = null;
        this.target = this.getTarget();
        if (this.target != null) {
            this.facing = this.facePredict(this.target, this.target.lastTickPosX, this.target.lastTickPosZ);
            this.facing = AimPistol.faceCords(this.facing[0], (float)this.target.posY, this.facing[1], 360.0f, 360.0f, false);
            float f = this.facing[0];
            float f2 = this.facing[1];
            AimPistol.mc.player.rotationYaw = this.facing[0];
            AimPistol.mc.player.rotationPitch = this.facing[1];
            if (AutoShoot && AimPistol.mc.player.canEntityBeSeen(this.target)) {
                if (this.timer.isDelay((long)AutoShootDeley)) {
                    if (AutoAim) {
                        AimPistol.clickMouse(1);
                    }
                    AimPistol.mc.player.swingArm(EnumHand.MAIN_HAND);
                    if (AutoAim) {
                        AimPistol.clickMouse(1);
                    }
                }
                this.timer.setLastMS();
            }
        } else {
            this.timer.setLastMS();
        }
    }

    public static void clickMouse(int button) {
        block4: {
            try {
                Robot bot = new Robot();
                if (button == 0) {
                    bot.mousePress(16);
                    bot.mouseRelease(16);
                    break block4;
                }
                if (button == 1) {
                    bot.mousePress(4096);
                    bot.mouseRelease(4096);
                    break block4;
                }
                return;
            }
            catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }
}

