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
import me.thef1xer.gateclient.settings.impl.FloatSetting;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TriggerBot
extends Module {
    private transient long time;
    public static final TriggerBot INSTANCE = new TriggerBot();
    public final FloatSetting mincps = new FloatSetting("MinCPS", "mincps", 12.0f, 0.0f, 30.0f);
    public final FloatSetting maxcps = new FloatSetting("MaxCPS", "maxcps", 26.0f, 0.0f, 30.0f);
    public final FloatSetting reach = new FloatSetting("Reach", "reach", 3.0f, 0.0f, 6.0f);
    public final BooleanSetting useCooldown = new BooleanSetting("Cooldown", "cooldown", true);
    public final BooleanSetting attackPlayers = new BooleanSetting("AttackPlayers", "attackplayers", true);
    public final BooleanSetting attackMobs = new BooleanSetting("attackMobs", "attackmobs", false);
    public final BooleanSetting attackAnimals = new BooleanSetting("attackAnimals", "attackanimals", false);
    public final BooleanSetting attackInvisibleEntities = new BooleanSetting("attackInvisibleEntities", "attackinvisibleentities", true);
    public final BooleanSetting useAxeToBreakShield = new BooleanSetting("AutoBreakShield", "autobreakshield", true);
    public final BooleanSetting attackWhileMainhandInUse = new BooleanSetting("attackWhileMainhandInUse", "attackwhileMainhandinUse", true);
    public Entity entity;
    private Entity focusTarget;

    public TriggerBot() {
        super("TriggerBot", "TriggerBot", Module.ModuleCategory.COMBAT);
        this.addSettings(this.mincps, this.maxcps, this.reach, this.useCooldown, this.attackPlayers, this.attackMobs, this.attackAnimals, this.attackInvisibleEntities, this.useAxeToBreakShield, this.attackWhileMainhandInUse);
        this.setEnabled(false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.time = System.currentTimeMillis();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        block23: {
            Entity entity;
            block25: {
                block24: {
                    if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
                        return;
                    }
                    entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
                    if (entity == null || !(entity instanceof EntityPlayer)) break block23;
                    int mincpsi = (int)this.mincps.getValue();
                    int maccpsi = (int)this.maxcps.getValue();
                    float cps = TriggerBot.generateRandomIntIntRange(mincpsi, maccpsi);
                    if (!this.useCooldown.getValue() ? cps == 0.0f || (double)(System.currentTimeMillis() - this.time) < 1000.0 / (double)cps : Minecraft.getMinecraft().player.getCooledAttackStrength(0.0f) < 1.0f) break block24;
                    if (Minecraft.getMinecraft().objectMouseOver != null) break block25;
                }
                return;
            }
            long newTime = System.currentTimeMillis();
            if (entity == null) {
                return;
            }
            if (Minecraft.getMinecraft().player.getDistance(entity) > this.reach.getValue()) {
                return;
            }
            if (entity instanceof EntityLivingBase && (entity.isDead || ((EntityLivingBase)entity).getHealth() < 0.0f)) {
                return;
            }
            if (entity instanceof EntityPlayer) {
                if (!this.attackPlayers.getValue()) {
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
                if (TriggerBot.isPlayerShielded((EntityPlayer)entity) && 180.0 - Math.abs(Math.abs(angle1 - angle2) - 180.0) < 95.0 && (!this.useAxeToBreakShield.getValue() || !(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemAxe))) {
                    return;
                }
            } else if (entity instanceof IMob) {
                if (!this.attackMobs.getValue()) {
                    return;
                }
            } else if (TriggerBot.isAnimal(entity)) {
                if (!this.attackAnimals.getValue()) {
                    return;
                }
            } else {
                return;
            }
            if (!(TriggerBot.isPlayerShielded((EntityPlayer)Minecraft.getMinecraft().player) || !this.attackWhileMainhandInUse.getValue() && TriggerBot.isPlayerUsingMainhand((EntityPlayer)Minecraft.getMinecraft().player))) {
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

