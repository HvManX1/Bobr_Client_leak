//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.entity.passive.EntityWaterMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.EnumAction
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemAxe
 *  net.minecraft.item.ItemShield
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.module.combat.AntiBot;
import ru.internali.settings.Setting;
import ru.internali.utils.FriendManager;
import ru.internali.utils.RenderUtil;
import ru.internali.utils.RotationUtils;

public class KillAuralegit
extends Module {
    public static Entity target;
    private transient long time;
    public Entity entity;
    public float[] facing;
    public Random random;
    public static List<String> Modes;
    private float height;
    private boolean up;

    public KillAuralegit() {
        super("Kill Aura legit", "auto kill", Category.COMBAT);
        Modes.add("Silent");
        Modes.add("Client");
        Modes.add("None");
        this.random = new Random();
        CatClient.instance.settingsManager.rSetting(new Setting("Range", this, 4.0, 0.0, 6.0, false));
        CatClient.instance.settingsManager.rSetting(new Setting("CriticalsFalling", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("StopSprint", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("TargetMark", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("RangeCyrcle", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("Cooldown", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("Rotation", this, "Silent", (ArrayList)Modes));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.time = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        float Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
        Boolean TargetMark = CatClient.instance.settingsManager.getSettingByName(this, "TargetMark").getValBoolean();
        Boolean RangeCyrcle = CatClient.instance.settingsManager.getSettingByName(this, "RangeCyrcle").getValBoolean();
        if (RangeCyrcle.booleanValue()) {
            RenderUtil.drawLinesAroundPlayer((Entity)KillAuralegit.mc.player, Range, mc.getRenderPartialTicks(), 30, 3.0f, -1);
        }
        if (target != null && TargetMark.booleanValue()) {
            this.height = this.up ? (float)((double)this.height + 0.05) : (float)((double)this.height - 0.05);
            if (this.height >= KillAuralegit.target.height) {
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
                if (KillAuralegit.mc.player == null) break block4;
                if (!KillAuralegit.mc.player.isDead) break block5;
            }
            return null;
        }
        List list = KillAuralegit.mc.world.loadedEntityList.stream().filter(entity -> entity != KillAuralegit.mc.player).filter(entity -> KillAuralegit.mc.player.getDistance(entity) <= Range).filter(entity -> !entity.isDead).filter(entity -> !FriendManager.isFriend(entity.getName())).filter(entity -> !AntiBot.isBot(entity.getName())).filter(entity -> !entity.isDead).filter(this::lambdagetTarget).sorted(Comparator.comparing(entity -> Float.valueOf(KillAuralegit.mc.player.getDistance(entity)))).collect(Collectors.toList());
        if (list.size() > 0) {
            return (Entity)list.get(0);
        }
        return null;
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        block25: {
            Entity entity;
            float Range;
            block27: {
                block26: {
                    Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
                    boolean criticals = CatClient.instance.settingsManager.getSettingByName(this, "CriticalsFalling").getValBoolean();
                    boolean StopSprint = CatClient.instance.settingsManager.getSettingByName(this, "StopSprint").getValBoolean();
                    boolean Cooldown = CatClient.instance.settingsManager.getSettingByName(this, "Cooldown").getValBoolean();
                    String Rotation2 = CatClient.instance.settingsManager.getSettingByName(this, "Rotation").getValString();
                    if (Minecraft.getMinecraft().world == null) {
                        this.toggle();
                    }
                    if (KillAuralegit.mc.player.isDead) {
                        this.toggle();
                    }
                    if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
                        return;
                    }
                    boolean go = true;
                    for (Entity entity2 : KillAuralegit.mc.world.loadedEntityList) {
                        if (!go || !(entity2 instanceof EntityPlayer) || !(KillAuralegit.mc.player.getDistance(entity2) <= Range) || entity2 == KillAuralegit.mc.player || FriendManager.isFriend(entity2.getName()) || AntiBot.isBot(entity2.getName())) continue;
                        target = entity2;
                        go = false;
                    }
                    if (go) {
                        target = null;
                    }
                    if (target == null) break block25;
                    if (StopSprint) {
                        KeyBinding.setKeyBindState((int)KillAuralegit.mc.gameSettings.keyBindSprint.getKeyCode(), (boolean)false);
                    }
                    float f = RotationUtils.getNeededRotations(target)[0];
                    float f2 = RotationUtils.getNeededRotations(target)[1];
                    float speed = Math.abs(Math.abs(f) - Math.abs(KillAuralegit.mc.player.rotationYaw)) / 15.0f;
                    if (KillAuralegit.mc.player.rotationYaw < f) {
                        KillAuralegit.mc.player.rotationYaw += speed;
                    }
                    if (KillAuralegit.mc.player.rotationYaw > f) {
                        KillAuralegit.mc.player.rotationYaw -= speed;
                    }
                    if (KillAuralegit.mc.player.rotationPitch < f2) {
                        KillAuralegit.mc.player.rotationPitch += 5.0f;
                    }
                    if (!(KillAuralegit.mc.player.rotationPitch > f2)) {
                        KillAuralegit.mc.player.rotationPitch -= 5.0f;
                    }
                    this.entity = null;
                    entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
                    if (entity == null) break block25;
                    int mincpsi = 10;
                    int maccpsi = 18;
                    float cps = KillAuralegit.generateRandomIntIntRange(mincpsi, maccpsi);
                    if (!Cooldown ? cps == 0.0f || (double)(System.currentTimeMillis() - this.time) < 1000.0 / (double)cps : Minecraft.getMinecraft().player.getCooledAttackStrength(0.0f) < 1.0f) break block26;
                    if (Minecraft.getMinecraft().objectMouseOver != null) break block27;
                }
                return;
            }
            long newTime = System.currentTimeMillis();
            if (entity == null) {
                return;
            }
            if (Minecraft.getMinecraft().player.getDistance(entity) > Range) {
                return;
            }
            if (entity instanceof EntityLivingBase && (entity.isDead || ((EntityLivingBase)entity).getHealth() < 0.0f)) {
                return;
            }
            if (entity instanceof EntityPlayer) {
                if (AntiBot.isBot(entity.getName())) {
                    return;
                }
                if (FriendManager.isFriend(entity.getName())) {
                    return;
                }
                double angle1 = (entity.rotationYaw + 180.0f) % 360.0f;
                double angle2 = Minecraft.getMinecraft().player.rotationYaw % 360.0f;
                if (angle1 < 0.0) {
                    angle1 += 360.0;
                }
                if (angle2 < 0.0) {
                    angle2 += 360.0;
                }
                if (KillAuralegit.isPlayerShielded((EntityPlayer)entity) && 180.0 - Math.abs(Math.abs(angle1 - angle2) - 180.0) < 95.0 && !(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemAxe)) {
                    return;
                }
            } else {
                return;
            }
            if (!KillAuralegit.isPlayerShielded((EntityPlayer)Minecraft.getMinecraft().player)) {
                Minecraft.getMinecraft().playerController.attackEntity((EntityPlayer)Minecraft.getMinecraft().player, entity);
                Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                this.time = newTime;
            }
        }
    }

    public static boolean isAnimal(Entity entity) {
        return entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature || entity instanceof EntityWaterMob || entity instanceof EntityGolem;
    }

    private static boolean isPlayerShielded(EntityPlayer player) {
        return player.getItemInUseCount() > 0 && (player.getHeldItemMainhand().getItem() instanceof ItemShield || player.getHeldItemOffhand().getItem() instanceof ItemShield && !KillAuralegit.isPlayerUsingMainhand(player));
    }

    private static boolean isPlayerUsingMainhand(EntityPlayer player) {
        ItemStack main = player.getHeldItemMainhand();
        return player.getItemInUseCount() > 0 && (main.getItemUseAction() == EnumAction.EAT && !player.isCreative() && (player.getFoodStats().needFood() || main.getItem() instanceof ItemAppleGold) || main.getItemUseAction() == EnumAction.BOW && KillAuralegit.canShootBow(player) || main.getItemUseAction() == EnumAction.DRINK || main.getItemUseAction() == EnumAction.BLOCK);
    }

    private static boolean canShootBow(EntityPlayer player) {
        if (player.isCreative()) {
            return true;
        }
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem() != Items.ARROW) continue;
            return true;
        }
        return false;
    }

    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    static {
        Modes = new ArrayList<String>();
    }
}

