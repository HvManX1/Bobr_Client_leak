//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.potion.Potion
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package ru.internali.module.movement;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.w3c.dom.css.Counter;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.settings.Setting;
import ru.internali.utils.TimerUtils;

public class TargetStrafe
extends Module {
    public Counter counter;
    public TimerUtils timerUtils;
    public static int direction = -1;

    public TargetStrafe() {
        super("TargetStrafe", "Spiin", Category.MOVEMENT);
        CatClient.instance.settingsManager.rSetting(new Setting("Range", this, 3.0, 0.1, 7.0, false));
        CatClient.instance.settingsManager.rSetting(new Setting("Boost", this, 2.8, 0.1, 7.0, false));
        CatClient.instance.settingsManager.rSetting(new Setting("AutoSneak", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("DamageBoost", this, true));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (TargetStrafe.mc.player.collidedHorizontally) {
            this.invertStrafe();
        }
        TargetStrafe.mc.player.movementInput.moveForward = 0.0f;
        double d = this.getMovementSpeed();
        boolean damageBoost = CatClient.instance.settingsManager.getSettingByName(this, "DamageBoost").getValBoolean();
        float boost = (float)CatClient.instance.settingsManager.getSettingByName(this, "Boost").getValDouble();
        if (TargetStrafe.mc.player.hurtTime > 0 && damageBoost) {
            this.doStrafeAtSpeed(d *= (double)boost);
        } else {
            this.doStrafeAtSpeed(d);
        }
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var7;
        double var4 = target.posX - TargetStrafe.mc.player.posX;
        double var5 = target.posZ - TargetStrafe.mc.player.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase)target;
            var7 = var6.posY + (double)var6.getEyeHeight() - (TargetStrafe.mc.player.posY + (double)TargetStrafe.mc.player.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (TargetStrafe.mc.player.posY + (double)TargetStrafe.mc.player.getEyeHeight());
        }
        double var8 = MathHelper.sqrt((double)(var4 * var4 + var5 * var5));
        float var9 = (float)(Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7 - (target instanceof EntityPlayer ? 0.25 : 0.0), var8) * 180.0 / Math.PI));
        float f = TargetStrafe.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float gcd = f * f * f * 1.2f;
        float pitch = TargetStrafe.updateRotation(TargetStrafe.mc.player.rotationPitch, var10, p_706253);
        float yaw = TargetStrafe.updateRotation(TargetStrafe.mc.player.rotationYaw, var9, p_706252);
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

    public final void doStrafeAtSpeed(double d) {
        boolean autoSneak = CatClient.instance.settingsManager.getSettingByName(this, "AutoSneak").getValBoolean();
        if (!TargetStrafe.mc.player.isDead) {
            boolean bl = true;
            float range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
            Entity entity = this.getTarget();
            if (entity != null) {
                if (TargetStrafe.mc.player.onGround) {
                    TargetStrafe.mc.player.jump();
                }
                float[] arrf = TargetStrafe.faceTarget(entity, 360.0f, 360.0f, false);
                if ((double)Minecraft.getMinecraft().player.getDistance(entity) <= (double)range) {
                    TargetStrafe.mc.player.renderYawOffset = arrf[0];
                    TargetStrafe.mc.player.rotationYawHead = arrf[0];
                    this.setSpeed(d - 0.05, arrf[0], direction, 0.0);
                } else {
                    this.setSpeed(d - 0.05, arrf[0], direction, 1.0);
                    TargetStrafe.mc.player.renderYawOffset = arrf[0];
                    TargetStrafe.mc.player.rotationYawHead = arrf[0];
                }
                if (this.isToggled() && (double)Minecraft.getMinecraft().player.getDistance(entity) <= 7.0 && autoSneak) {
                    KeyBinding.setKeyBindState((int)TargetStrafe.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)true);
                } else {
                    KeyBinding.setKeyBindState((int)TargetStrafe.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
                }
            }
        }
    }

    public Entity getTarget() {
        block5: {
            block4: {
                if (TargetStrafe.mc.player == null) break block4;
                if (!TargetStrafe.mc.player.isDead) break block5;
            }
            return null;
        }
        List list = TargetStrafe.mc.world.loadedEntityList.stream().filter(entity -> entity != TargetStrafe.mc.player).filter(entity -> TargetStrafe.mc.player.getDistance(entity) <= 35.0f).filter(entity -> !entity.isDead).filter(this::lambdagetTarget).sorted(Comparator.comparing(entity -> Float.valueOf(TargetStrafe.mc.player.getDistance(entity)))).collect(Collectors.toList());
        if (list.size() > 0) {
            return (Entity)list.get(0);
        }
        return null;
    }

    public double getMovementSpeed() {
        boolean damageBoost = CatClient.instance.settingsManager.getSettingByName(this, "DamageBoost").getValBoolean();
        boolean boosted = false;
        double d = 0.2873;
        if (Minecraft.getMinecraft().player.isPotionActive(Objects.requireNonNull(Potion.getPotionById((int)1)))) {
            int n = Objects.requireNonNull(Minecraft.getMinecraft().player.getActivePotionEffect(Objects.requireNonNull(Potion.getPotionById((int)1)))).getAmplifier();
            d *= 1.0 + 0.2 * (double)(n + 1);
        }
        return d;
    }

    private boolean lambdagetTarget(Entity entity) {
        return this.attackCheck(entity);
    }

    private void invertStrafe() {
        direction = -direction;
    }

    public void setSpeed(double d, float f, double d2, double d3) {
        double d4 = d3;
        double d5 = d2;
        float f2 = f;
        if (d4 == 0.0 && d5 == 0.0) {
            TargetStrafe.mc.player.motionZ = 0.0;
            TargetStrafe.mc.player.motionX = 0.0;
        } else {
            if (d4 != 0.0) {
                if (d5 > 0.0) {
                    f2 += (float)(d4 > 0.0 ? -45 : 45);
                } else if (d5 < 0.0) {
                    f2 += (float)(d4 > 0.0 ? 45 : -45);
                }
                d5 = 0.0;
                if (d4 > 0.0) {
                    d4 = 1.0;
                } else if (d4 < 0.0) {
                    d4 = -1.0;
                }
            }
            double d6 = Math.cos(Math.toRadians(f2 + 90.0f));
            double d7 = Math.sin(Math.toRadians(f2 + 90.0f));
            TargetStrafe.mc.player.motionX = d4 * d * d6 + d5 * d * d7;
            TargetStrafe.mc.player.motionZ = d4 * d * d7 - d5 * d * d6;
        }
    }

    public boolean attackCheck(Entity entity) {
        return !entity.isInvisible() && entity instanceof EntityPlayer;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        KeyBinding.setKeyBindState((int)TargetStrafe.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)false);
    }
}

