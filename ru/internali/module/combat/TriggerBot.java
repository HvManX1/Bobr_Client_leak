//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityGolem
 *  net.minecraft.entity.monster.IMob
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
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.combat;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
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
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.module.combat.AntiBot;
import ru.internali.settings.Setting;
import ru.internali.utils.FriendManager;

public class TriggerBot
extends Module {
    private transient long time;
    public Entity entity;

    public TriggerBot() {
        super("Trigger Bot", "hit players on your croshair", Category.COMBAT);
        CatClient.instance.settingsManager.rSetting(new Setting("MinCPS", this, 12.0, 0.0, 30.0, true));
        CatClient.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 26.0, 0.0, 30.0, true));
        CatClient.instance.settingsManager.rSetting(new Setting("Range", this, 4.0, 0.0, 6.0, true));
        CatClient.instance.settingsManager.rSetting(new Setting("Cooldown", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("AttackPlayers", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("attackMobs", this, false));
        CatClient.instance.settingsManager.rSetting(new Setting("attackAnimals", this, false));
        CatClient.instance.settingsManager.rSetting(new Setting("attackInvisibleEntities", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("AutoBreakShield", this, true));
        CatClient.instance.settingsManager.rSetting(new Setting("attackWhileMainhandInUse", this, true));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.time = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        block25: {
            float MinCPS = (float)CatClient.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble();
            float MaxCPS = (float)CatClient.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble();
            float Range = (float)CatClient.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
            boolean Cooldown = CatClient.instance.settingsManager.getSettingByName(this, "Cooldown").getValBoolean();
            boolean AttackPlayers = CatClient.instance.settingsManager.getSettingByName(this, "AttackPlayers").getValBoolean();
            boolean attackMobs = CatClient.instance.settingsManager.getSettingByName(this, "attackMobs").getValBoolean();
            boolean attackAnimals = CatClient.instance.settingsManager.getSettingByName(this, "attackAnimals").getValBoolean();
            boolean attackInvisibleEntities = CatClient.instance.settingsManager.getSettingByName(this, "attackInvisibleEntities").getValBoolean();
            boolean AutoBreakShield = CatClient.instance.settingsManager.getSettingByName(this, "AutoBreakShield").getValBoolean();
            boolean attackWhileMainhandInUse = CatClient.instance.settingsManager.getSettingByName(this, "attackWhileMainhandInUse").getValBoolean();
            if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
                return;
            }
            try {
                Entity entity;
                block27: {
                    block26: {
                        entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
                        if (entity == null) break block25;
                        int mincpsi = (int)MinCPS;
                        int maccpsi = (int)MaxCPS;
                        float cps = TriggerBot.generateRandomIntIntRange(mincpsi, maccpsi);
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
                    if (!AttackPlayers) {
                        return;
                    }
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
                    if (TriggerBot.isPlayerShielded((EntityPlayer)entity) && 180.0 - Math.abs(Math.abs(angle1 - angle2) - 180.0) < 95.0 && (!AutoBreakShield || !(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemAxe))) {
                        return;
                    }
                } else if (entity instanceof IMob) {
                    if (!attackMobs) {
                        return;
                    }
                } else if (TriggerBot.isAnimal(entity)) {
                    if (!attackAnimals) {
                        return;
                    }
                } else {
                    return;
                }
                if (!(TriggerBot.isPlayerShielded((EntityPlayer)Minecraft.getMinecraft().player) || !attackWhileMainhandInUse && TriggerBot.isPlayerUsingMainhand((EntityPlayer)Minecraft.getMinecraft().player))) {
                    Minecraft.getMinecraft().playerController.attackEntity((EntityPlayer)Minecraft.getMinecraft().player, entity);
                    Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                    this.time = newTime;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isAnimal(Entity entity) {
        return entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature || entity instanceof EntityWaterMob || entity instanceof EntityGolem;
    }

    private static boolean isPlayerShielded(EntityPlayer player) {
        return player.getItemInUseCount() > 0 && (player.getHeldItemMainhand().getItem() instanceof ItemShield || player.getHeldItemOffhand().getItem() instanceof ItemShield && !TriggerBot.isPlayerUsingMainhand(player));
    }

    private static boolean isPlayerUsingMainhand(EntityPlayer player) {
        ItemStack main = player.getHeldItemMainhand();
        return player.getItemInUseCount() > 0 && (main.getItemUseAction() == EnumAction.EAT && !player.isCreative() && (player.getFoodStats().needFood() || main.getItem() instanceof ItemAppleGold) || main.getItemUseAction() == EnumAction.BOW && TriggerBot.canShootBow(player) || main.getItemUseAction() == EnumAction.DRINK || main.getItemUseAction() == EnumAction.BLOCK);
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
}

