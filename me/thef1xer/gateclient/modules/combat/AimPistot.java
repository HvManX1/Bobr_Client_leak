//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package me.thef1xer.gateclient.modules.combat;

import java.util.Random;
import me.thef1xer.gateclient.managers.FriendManager;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.modules.combat.AntiBot;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import me.thef1xer.gateclient.settings.impl.EnumSetting;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimPistot
extends Module {
    public static final AimPistot INSTANCE = new AimPistot();
    public Minecraft mc = Minecraft.getMinecraft();
    public final EnumSetting Mode = new EnumSetting("Mode", "Mode", Modes.values(), Modes.Client);
    public final EnumSetting PredictMode = new EnumSetting("PredictMode", "PredictMode", Pred.values(), Pred.Client);
    public final FloatSetting reach = new FloatSetting("Reach", "reach", 6.0f, 0.0f, 250.0f);
    public final FloatSetting Predict = new FloatSetting("Predict", "Predict", 6.0f, 0.0f, 100.0f);
    public final FloatSetting Predictuse = new FloatSetting("Predictuse", "Predictuse", 6.0f, 0.0f, 100.0f);
    public final BooleanSetting wals = new BooleanSetting("Wals", "Wals", false);
    public Entity target;
    public float[] facing;
    public boolean can_attack;
    public float[] pred;
    private Entity focusTarget;
    private float old_must;
    private float old_posX;
    private float old_posZ;
    private float[] old_pred;
    private int life_time_pred;
    private float post_f;
    private float post_f2;

    public AimPistot() {
        super("AimPistol", "AimPistol", Module.ModuleCategory.COMBAT);
        this.addSettings(this.Mode, this.reach, this.wals, this.PredictMode, this.Predict);
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
            if (!this.isTarget(entity)) continue;
            if (!this.wals.getValue() && !AntiBot.isBot(entity.getName()) && !FriendManager.isFriend(entity.getName()) && entity instanceof EntityPlayer && Minecraft.getMinecraft().player.canEntityBeSeen(entity)) {
                if (this.target == null) {
                    this.target = entity;
                } else if (Minecraft.getMinecraft().player.getDistanceSq(entity) < Minecraft.getMinecraft().player.getDistanceSq(this.target)) {
                    this.target = entity;
                }
            }
            if (!this.wals.getValue() || AntiBot.isBot(entity.getName()) || FriendManager.isFriend(entity.getName()) || !(entity instanceof EntityPlayer)) continue;
            if (this.target == null) {
                this.target = entity;
                continue;
            }
            if (!(Minecraft.getMinecraft().player.getDistanceSq(entity) < Minecraft.getMinecraft().player.getDistanceSq(this.target))) continue;
            this.target = entity;
        }
        if (this.target != null) {
            this.facing = AimPistot.faceTarget(this.target, 360.0f, 360.0f, false);
            float f = this.facing[0];
            float f2 = this.facing[1];
            float old_f = f;
            float[] n = AimPistot.facePredict(this.target, this.old_posX, this.old_posZ);
            float pred_f = n[0];
            float pred_f2 = n[1];
            this.can_attack = Minecraft.getMinecraft().player.canEntityBeSeen(this.target);
            n = AimPistot.faceCords(pred_f, (float)this.target.posY, pred_f2, 360.0f, 360.0f, false);
            pred_f = n[0];
            pred_f2 = n[1];
            if (this.Mode.getCurrentValue() == Modes.Client) {
                if (this.PredictMode.getCurrentValue() == Pred.Client) {
                    Minecraft.getMinecraft().player.rotationYaw = pred_f;
                    Minecraft.getMinecraft().player.rotationPitch = pred_f2;
                } else if (this.PredictMode.getCurrentValue() == Pred.Silent) {
                    Minecraft.getMinecraft().player.rotationYaw = f;
                    Minecraft.getMinecraft().player.rotationPitch = f2;
                    Minecraft.getMinecraft().player.renderYawOffset = pred_f;
                    Minecraft.getMinecraft().player.rotationYawHead = pred_f;
                    if (Minecraft.getMinecraft().player.onGround) {
                        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(pred_f, f2, Minecraft.getMinecraft().player.onGround));
                    } else if (!Minecraft.getMinecraft().player.onGround) {
                        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(pred_f2, f2, !Minecraft.getMinecraft().player.onGround));
                    }
                }
            } else if (this.Mode.getCurrentValue() == Modes.Silent) {
                Minecraft.getMinecraft().player.renderYawOffset = f;
                Minecraft.getMinecraft().player.rotationYawHead = f;
                if (Minecraft.getMinecraft().player.onGround) {
                    Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(f, f2, Minecraft.getMinecraft().player.onGround));
                } else if (!Minecraft.getMinecraft().player.onGround) {
                    Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(f, f2, !Minecraft.getMinecraft().player.onGround));
                }
            }
            this.old_must = old_f;
            this.old_posX = (float)this.target.posX;
            this.old_posZ = (float)this.target.posZ;
        }
    }

    public boolean isTarget(Entity entity) {
        return entity != Minecraft.getMinecraft().player && entity != Minecraft.getMinecraft().getRenderViewEntity() && Minecraft.getMinecraft().player.getDistanceSq(entity) <= Math.pow(this.reach.getValue(), 2.0) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }

    public static float[] facePredict(Entity target, float old_posX, float old_posZ) {
        float new_posX = (float)target.posX;
        float new_posZ = (float)target.posZ;
        float differenceX = new_posX >= 0.0f ? Math.abs(new_posX) - Math.abs(old_posX) : Math.abs(old_posX) - Math.abs(new_posX);
        float differenceZ = new_posZ >= 0.0f ? Math.abs(new_posZ) - Math.abs(old_posZ) : Math.abs(old_posZ) - Math.abs(new_posZ);
        if (differenceX != 0.0f) {
            if (3 != AimPistot.INSTANCE.life_time_pred) {
                ++AimPistot.INSTANCE.life_time_pred;
            }
        } else if (0 != AimPistot.INSTANCE.life_time_pred) {
            differenceX = AimPistot.INSTANCE.old_pred[0];
            --AimPistot.INSTANCE.life_time_pred;
        }
        if (differenceZ != 0.0f) {
            if (3 != AimPistot.INSTANCE.life_time_pred) {
                ++AimPistot.INSTANCE.life_time_pred;
            }
        } else if (0 != AimPistot.INSTANCE.life_time_pred) {
            differenceZ = AimPistot.INSTANCE.old_pred[1];
            --AimPistot.INSTANCE.life_time_pred;
        }
        AimPistot.INSTANCE.old_pred = new float[]{differenceX, differenceZ};
        float returnX = new_posX + differenceX * AimPistot.INSTANCE.Predict.getValue();
        float returnZ = new_posZ + differenceZ * AimPistot.INSTANCE.Predict.getValue();
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
        float pitch = AimPistot.updateRotation(Minecraft.getMinecraft().player.rotationPitch, var10, p_706253);
        float yaw = AimPistot.updateRotation(Minecraft.getMinecraft().player.rotationYaw, var9, p_706252);
        yaw -= yaw % gcd;
        pitch -= pitch % gcd;
        return new float[]{yaw, pitch};
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
        float pitch = AimPistot.updateRotation(Minecraft.getMinecraft().player.rotationPitch, var10, p_706253);
        float yaw = AimPistot.updateRotation(Minecraft.getMinecraft().player.rotationYaw, var9, p_706252);
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

    public static enum Modes {
        Client("Client"),
        Silent("Silent");

        private final String name;

        private Modes(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public static enum Pred {
        Client("Client"),
        Silent("Silent");

        private final String name;

        private Pred(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

