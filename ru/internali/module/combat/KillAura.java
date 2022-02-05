//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.combat;

import event.EventTarget;
import event.events.EventPreMotionUpdate;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.module.combat.AntiBot;
import ru.internali.notifications.Type;
import ru.internali.notifications.notifications;
import ru.internali.settings.Setting;
import ru.internali.utils.FriendManager;
import ru.internali.utils.GCDFix;
import ru.internali.utils.RenderUtil;
import ru.internali.utils.RotationTask;
import ru.internali.utils.TimerUtils;

public class KillAura
extends Module {
    public static Entity target;
    private transient long time;
    List<EntityLivingBase> targets;
    TimerUtils timer = new TimerUtils();
    public Entity entity;
    public float[] facing;
    public Random random;
    public static List<String> Modes;
    public static List<String> Modes2;
    private float height;
    private boolean up;
    private final RotationTask rotationTask = new RotationTask("ScaffoldTask", 3);

    public KillAura() {
        super("Kill Aura", "auto kill", Category.COMBAT);
        CatClient.EVENT_MANAGER.register(this.getClass());
        Modes.add("Silent");
        Modes.add("Client");
        Modes.add("None");
        Modes2.add("Advanced");
        Modes2.add("Wellmore");
        this.random = new Random();
        CatClient.instance.settingsManager.rSetting(new Setting("Range", this, 4.0, 0.0, 6.0, false));
        CatClient.instance.settingsManager.rSetting(new Setting("Cooldown", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("StopSprint", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("TargetMark", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("RangeCyrcle", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("Mode", this, "Advanced", (ArrayList)Modes2));
        CatClient.instance.settingsManager.rSetting(new Setting("Rotation", this, "Silent", (ArrayList)Modes));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        CatClient.EVENT_MANAGER.register(this);
        this.time = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        float Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
        Boolean TargetMark = CatClient.instance.settingsManager.getSettingByName(this, "TargetMark").getValBoolean();
        Boolean RangeCyrcle = CatClient.instance.settingsManager.getSettingByName(this, "RangeCyrcle").getValBoolean();
        if (RangeCyrcle.booleanValue()) {
            RenderUtil.drawLinesAroundPlayer((Entity)KillAura.mc.player, Range, mc.getRenderPartialTicks(), 30, 3.0f, -1);
        }
        if (target != null && TargetMark.booleanValue()) {
            this.height = this.up ? (float)((double)this.height + 0.05) : (float)((double)this.height - 0.05);
            if (this.height >= KillAura.target.height) {
                this.up = false;
            }
            if (this.height <= 0.0f) {
                this.up = true;
            }
            if (target != null) {
                RenderUtil.drawLinesAroundPlayer(target, 0.5, mc.getRenderPartialTicks(), 6, 2.0, Color.white.getRGB(), this.height);
            }
        }
    }

    public boolean isTarget(Entity entity) {
        float Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
        return entity != Minecraft.getMinecraft().player && entity != Minecraft.getMinecraft().getRenderViewEntity() && Minecraft.getMinecraft().player.getDistanceSq(entity) <= Math.pow(Range, 2.0) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }

    public boolean attackCheck(Entity entity) {
        return !entity.isInvisible() && entity instanceof EntityPlayer;
    }

    private boolean lambdagetTarget(Entity entity) {
        return this.attackCheck(entity);
    }

    public Entity getTarget() {
        float Range;
        block5: {
            block4: {
                Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
                if (KillAura.mc.player == null) break block4;
                if (!KillAura.mc.player.isDead) break block5;
            }
            return null;
        }
        List list = KillAura.mc.world.loadedEntityList.stream().filter(entity -> entity != KillAura.mc.player).filter(entity -> KillAura.mc.player.getDistance(entity) <= Range).filter(entity -> !entity.isDead).filter(entity -> !FriendManager.isFriend(entity.getName())).filter(entity -> !AntiBot.isBot(entity.getName())).filter(entity -> !entity.isDead).filter(this::lambdagetTarget).sorted(Comparator.comparing(entity -> Float.valueOf(KillAura.mc.player.getDistance(entity)))).collect(Collectors.toList());
        if (list.size() > 0) {
            return (Entity)list.get(0);
        }
        return null;
    }

    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    private static boolean range(EntityLivingBase entity, double range) {
        KillAura.mc.player.getDistance((Entity)entity);
        return (double)KillAura.mc.player.getDistance((Entity)entity) <= range;
    }

    public boolean canAttack(EntityLivingBase player) {
        float Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
        return (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) && player instanceof EntityPlayer && KillAura.range(player, Range) && !FriendManager.isFriend(player.getName()) && !AntiBot.isBot(player.getName());
    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : KillAura.mc.world.loadedEntityList) {
            double currentDist;
            EntityLivingBase player;
            Entity entity = (Entity)object;
            if (!(entity instanceof EntityLivingBase) || !this.canAttack(player = (EntityLivingBase)entity) || !((currentDist = (double)KillAura.mc.player.getDistance((Entity)player)) <= dist)) continue;
            dist = currentDist;
            target = player;
            this.targets.add(player);
        }
        return target;
    }

    public static float nextFloat(float startInclusive, float endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0f) {
            return startInclusive;
        }
        return (float)((double)startInclusive + (double)(endInclusive - startInclusive) * Math.random());
    }

    public static float[] getRatations(Entity e) {
        try {
            double diffX = e.posX - KillAura.mc.player.posX;
            double diffZ = e.posZ - KillAura.mc.player.posZ;
            double diffY = e instanceof EntityLivingBase ? e.posY + (double)e.getEyeHeight() - (KillAura.mc.player.posY + (double)KillAura.mc.player.getEyeHeight()) - 0.4 : (e.getEntityBoundingBox().minY + e.getEntityBoundingBox().maxY) / 2.0 - (KillAura.mc.player.posY + (double)KillAura.mc.player.getEyeHeight());
            double dist = MathHelper.sqrt((double)(diffX * diffX + diffZ * diffZ));
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0) + KillAura.nextFloat(-2.0f, 2.0f);
            float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + KillAura.nextFloat(-2.0f, 2.0f);
            yaw = KillAura.mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees((float)(yaw - KillAura.mc.player.rotationYaw)));
            pitch = KillAura.mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees((float)(pitch - KillAura.mc.player.rotationPitch)));
            pitch = MathHelper.clamp((float)pitch, (float)-90.0f, (float)90.0f);
            return new float[]{yaw, pitch};
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @EventTarget
    public void onSendPacket(EventPreMotionUpdate e) {
        notifications.add("rotation", "work", Type.OK);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        float Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
        boolean Cooldown = CatClient.instance.settingsManager.getSettingByName(this, "Cooldown").getValBoolean();
        boolean StopSprint = CatClient.instance.settingsManager.getSettingByName(this, "StopSprint").getValBoolean();
        String Mode = CatClient.instance.settingsManager.getSettingByName(this, "Mode").getValString();
        String Rotation2 = CatClient.instance.settingsManager.getSettingByName(this, "Rotation").getValString();
        if (Minecraft.getMinecraft().world == null) {
            this.toggle();
        }
        if (KillAura.mc.player.isDead) {
            this.toggle();
        }
        if (Mode.equalsIgnoreCase("Advanced")) {
            int currentCPS = KillAura.generateRandomIntIntRange(10, 15);
            if ((double)KillAura.mc.player.getCooledAttackStrength(0.0f) >= 1.0 || this.timer.isDelay(1000 / currentCPS) && !Cooldown) {
                this.timer.setLastMS();
                for (Entity en : KillAura.mc.world.loadedEntityList) {
                    if (!(en instanceof EntityPlayer) || en == KillAura.mc.player || !(KillAura.mc.player.getDistance(en) <= Range)) continue;
                    float[] rots = KillAura.getRatations(en);
                    if (Rotation2.equalsIgnoreCase("Silent")) {
                        KillAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rots[0], rots[1], KillAura.mc.player.onGround));
                        KillAura.mc.player.renderYawOffset = rots[0];
                        KillAura.mc.player.rotationYawHead = rots[0];
                    }
                    if (Rotation2.equalsIgnoreCase("Client")) {
                        KillAura.mc.player.rotationYaw = rots[0];
                    }
                    Minecraft.getMinecraft().playerController.attackEntity((EntityPlayer)Minecraft.getMinecraft().player, en);
                    Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }

    static {
        Modes = new ArrayList<String>();
        Modes2 = new ArrayList<String>();
    }
}

