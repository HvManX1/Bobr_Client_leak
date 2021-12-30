//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ru.terrar.bobr.modules.combat;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.terrar.bobr.events.SendPacketEvent;
import ru.terrar.bobr.managers.FriendManager;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.modules.combat.AntiBot;
import ru.terrar.bobr.settings.impl.BooleanSetting;
import ru.terrar.bobr.settings.impl.FloatSetting;

public class AimPistot
extends Module {
    public static final AimPistot INSTANCE = new AimPistot();
    public Minecraft mc = Minecraft.getMinecraft();
    public final FloatSetting reach = new FloatSetting("Reach", "reach", 250.0f, 0.0f, 250.0f);
    public final FloatSetting Predict = new FloatSetting("Predict", "Predict", 6.0f, 0.0f, 10.0f);
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
        this.addSettings(this.reach, this.wals, this.Predict);
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
    public void onSendPacket(SendPacketEvent event) {
        CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
            this.focusTarget = packet.getEntityFromWorld((World)Minecraft.getMinecraft().world);
        }
        if (this.target != null && (event.getPacket() instanceof CPacketPlayer.PositionRotation || event.getPacket() instanceof CPacketPlayer.Rotation)) {
            double deltaX = this.target.posX - Minecraft.getMinecraft().player.posX;
            double deltaY = this.target.posY + (double)(this.target.height / 2.0f) - Minecraft.getMinecraft().player.posY - (double)Minecraft.getMinecraft().player.getEyeHeight();
            double deltaZ = this.target.posZ - Minecraft.getMinecraft().player.posZ;
            double deltaGround = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
            float pitch = (float)(-Math.toDegrees(Math.atan(deltaY / deltaGround)));
            float yaw = (float)(-Math.toDegrees(Math.atan(deltaX / deltaZ)));
            if (deltaZ <= 0.0) {
                yaw = deltaX > 0.0 ? (yaw -= 180.0f) : (yaw += 180.0f);
            }
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            event.setPacket((Packet<?>)new CPacketPlayer.PositionRotation(player.posX, player.posY, player.posZ, yaw, pitch, player.onGround));
        }
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        this.target = null;
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        this.target = null;
        int yaw_min = 1000000000;
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            int n;
            float f2;
            float f;
            if (!this.isTarget(entity)) continue;
            if (!this.wals.getValue() && !AntiBot.isBot(entity.getName()) && !FriendManager.isFriend(entity.getName()) && entity instanceof EntityPlayer && Minecraft.getMinecraft().player.canEntityBeSeen(entity)) {
                this.facing = AimPistot.faceTarget(entity, 360.0f, 360.0f, false);
                f = this.facing[0];
                f2 = this.facing[1];
                n = Math.abs(Minecraft.getMinecraft().player.rotationYaw) >= Math.abs(f) ? (int)(Math.abs(Minecraft.getMinecraft().player.rotationYaw) - Math.abs(f)) : (int)((float)((int)Math.abs(f)) - Math.abs(Minecraft.getMinecraft().player.rotationYaw));
                if (n < yaw_min) {
                    yaw_min = n;
                    this.target = entity;
                }
            }
            if (!this.wals.getValue() || AntiBot.isBot(entity.getName()) || FriendManager.isFriend(entity.getName()) || !(entity instanceof EntityPlayer)) continue;
            this.facing = AimPistot.faceTarget(entity, 360.0f, 360.0f, false);
            f = this.facing[0];
            f2 = this.facing[1];
            n = Math.abs(Minecraft.getMinecraft().player.rotationYaw) >= Math.abs(f) ? (int)(Math.abs(Minecraft.getMinecraft().player.rotationYaw) - Math.abs(f)) : (int)((float)((int)Math.abs(f)) - Math.abs(Minecraft.getMinecraft().player.rotationYaw));
            if (n >= yaw_min) continue;
            yaw_min = n;
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
            Minecraft.getMinecraft().player.rotationYaw = pred_f;
            Minecraft.getMinecraft().player.rotationPitch = pred_f2;
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

