//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ru.terrar.bobr.modules.combat;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.terrar.bobr.managers.FriendManager;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.modules.combat.AntiBot;
import ru.terrar.bobr.settings.impl.FloatSetting;

public class aimAssist
extends Module {
    public static final aimAssist INSTANCE = new aimAssist();
    public Minecraft mc = Minecraft.getMinecraft();
    public final FloatSetting reach = new FloatSetting("Reach", "reach", 6.0f, 0.0f, 16.0f);
    public final FloatSetting aimSpeed = new FloatSetting("AimSpeed", "aimspeed", 0.0f, 0.0f, 5.0f);
    public float[] facing;
    public Entity target;
    private Entity focusTarget;

    public aimAssist() {
        super("AimAssist", "aimAssist", Module.ModuleCategory.COMBAT);
        this.addSettings(this.reach, this.aimSpeed);
        this.setEnabled(false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        this.target = null;
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        this.target = null;
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (!this.isTarget(entity) || AntiBot.isBot(entity.getName()) || FriendManager.isFriend(entity.getName()) || !(entity instanceof EntityPlayer)) continue;
            if (this.target == null) {
                this.target = entity;
                continue;
            }
            if (!(Minecraft.getMinecraft().player.getDistanceSq(entity) < Minecraft.getMinecraft().player.getDistanceSq(this.target))) continue;
            this.target = entity;
        }
        if (this.target != null) {
            this.facing = aimAssist.faceTarget(this.target, 360.0f, 360.0f, false);
            float f = this.facing[0];
            float f2 = this.facing[1];
            float sp = 0.2f;
            sp = this.aimSpeed.getValue();
            if (Minecraft.getMinecraft().player.rotationYaw < f) {
                Minecraft.getMinecraft().player.rotationYaw += sp;
            }
            if (Minecraft.getMinecraft().player.rotationYaw > f) {
                Minecraft.getMinecraft().player.rotationYaw -= sp;
            }
            if (Minecraft.getMinecraft().player.rotationPitch < f2) {
                Minecraft.getMinecraft().player.rotationPitch += sp;
            }
            if (!(Minecraft.getMinecraft().player.rotationPitch > f2)) {
                Minecraft.getMinecraft().player.rotationPitch -= sp;
            }
        }
    }

    public boolean isTarget(Entity entity) {
        return entity != Minecraft.getMinecraft().player && entity != Minecraft.getMinecraft().getRenderViewEntity() && Minecraft.getMinecraft().player.getDistanceSq(entity) <= Math.pow(this.reach.getValue(), 2.0) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var7;
        double var4 = target.posX - Minecraft.getMinecraft().player.posX;
        double var5 = target.posZ - Minecraft.getMinecraft().player.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase)target;
            var7 = var6.posY + (double)var6.getEyeHeight() - (Minecraft.getMinecraft().player.posY + (double)Minecraft.getMinecraft().player.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.getMinecraft().player.posY + (double)Minecraft.getMinecraft().player.getEyeHeight());
        }
        double var8 = MathHelper.sqrt((double)(var4 * var4 + var5 * var5));
        float var9 = (float)(Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7 - (target instanceof EntityPlayer ? 0.25 : 0.0), var8) * 180.0 / Math.PI));
        float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float gcd = f * f * f * 1.2f;
        float pitch = aimAssist.updateRotation(Minecraft.getMinecraft().player.rotationPitch, var10, p_706253);
        float yaw = aimAssist.updateRotation(Minecraft.getMinecraft().player.rotationYaw, var9, p_706252);
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

    public static enum Priority {
        CLOSEST("Closest"),
        FOCUS("Focus");

        private final String name;

        private Priority(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

