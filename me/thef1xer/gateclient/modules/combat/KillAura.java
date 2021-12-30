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
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
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
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KillAura
extends Module {
    private transient long time;
    public static final KillAura INSTANCE = new KillAura();
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
    private Minecraft mc = Minecraft.getMinecraft();
    public Entity target;
    private Entity focusTarget;

    public KillAura() {
        super("KillAura", "killaura", Module.ModuleCategory.COMBAT);
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

    public boolean isTarget(Entity entity) {
        return entity != Minecraft.getMinecraft().player && entity != Minecraft.getMinecraft().getRenderViewEntity() && Minecraft.getMinecraft().player.getDistanceSq(entity) <= Math.pow(this.reach.getValue(), 2.0) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        block28: {
            block30: {
                block29: {
                    this.target = null;
                    if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
                        return;
                    }
                    for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
                        if (!this.isTarget(entity) || AntiBot.isBot(entity.getName()) || FriendManager.isFriend(entity.getName()) || !(entity instanceof EntityPlayer)) continue;
                        if (this.target == null) {
                            this.target = entity;
                            continue;
                        }
                        if (!(Minecraft.getMinecraft().player.getDistanceSq(entity) < Minecraft.getMinecraft().player.getDistanceSq(this.target))) continue;
                        this.target = entity;
                    }
                    if (this.target == null || !(this.target instanceof EntityPlayer)) break block28;
                    int mincpsi = (int)this.mincps.getValue();
                    int maccpsi = (int)this.maxcps.getValue();
                    float cps = KillAura.generateRandomIntIntRange(mincpsi, maccpsi);
                    if (!this.useCooldown.getValue() ? cps == 0.0f || (double)(System.currentTimeMillis() - this.time) < 1000.0 / (double)cps : Minecraft.getMinecraft().player.getCooledAttackStrength(0.0f) < 1.0f) break block29;
                    if (Minecraft.getMinecraft().objectMouseOver != null) break block30;
                }
                return;
            }
            long newTime = System.currentTimeMillis();
            if (this.target == null) {
                return;
            }
            if (Minecraft.getMinecraft().player.getDistance(this.target) > this.reach.getValue()) {
                return;
            }
            if (this.target instanceof EntityLivingBase && (this.target.isDead || ((EntityLivingBase)this.target).getHealth() < 0.0f)) {
                return;
            }
            if (this.target instanceof EntityPlayer) {
                if (!this.attackPlayers.getValue()) {
                    return;
                }
                if (AntiBot.isBot(this.target.getName())) {
                    return;
                }
                if (FriendManager.isFriend(this.target.getName())) {
                    return;
                }
                double angle1 = (this.target.rotationYaw + 180.0f) % 360.0f;
                double angle2 = Minecraft.getMinecraft().player.rotationYaw % 360.0f;
                if (angle1 < 0.0) {
                    angle1 += 360.0;
                }
                if (angle2 < 0.0) {
                    angle2 += 360.0;
                }
                if (KillAura.isPlayerShielded((EntityPlayer)this.target) && 180.0 - Math.abs(Math.abs(angle1 - angle2) - 180.0) < 95.0 && (!this.useAxeToBreakShield.getValue() || !(Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemAxe))) {
                    return;
                }
            } else if (this.target instanceof IMob) {
                if (!this.attackMobs.getValue()) {
                    return;
                }
            } else if (KillAura.isAnimal(this.target)) {
                if (!this.attackAnimals.getValue()) {
                    return;
                }
            } else {
                return;
            }
            double d = this.target.posX + (this.target.posX - this.target.lastTickPosX) - this.mc.player.posX;
            double d2 = this.target.posY + (double)this.target.getEyeHeight() - this.mc.player.posY + (double)this.mc.player.getEyeHeight() - 3.5;
            double d3 = this.target.posZ + (this.target.posZ - this.target.lastTickPosZ) - this.mc.player.posZ;
            double d4 = Math.sqrt(Math.pow(d, 2.0) + Math.pow(d3, 2.0));
            float f = (float)Math.toDegrees(-Math.atan(d / d3));
            float f2 = (float)(-Math.toDegrees(Math.atan(d2 / d4)));
            if (d < 0.0 && d3 < 0.0) {
                f = (float)(90.0 + Math.toDegrees(Math.atan(d3 / d)));
            } else if (d > 0.0 && d3 < 0.0) {
                f = (float)(-90.0 + Math.toDegrees(Math.atan(d3 / d)));
            }
            Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(f, f2, Minecraft.getMinecraft().player.onGround));
            if (!(KillAura.isPlayerShielded((EntityPlayer)Minecraft.getMinecraft().player) || !this.attackWhileMainhandInUse.getValue() && KillAura.isPlayerUsingMainhand((EntityPlayer)Minecraft.getMinecraft().player))) {
                Minecraft.getMinecraft().playerController.attackEntity((EntityPlayer)Minecraft.getMinecraft().player, this.target);
                Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
                this.time = newTime;
            }
        }
    }

    public static boolean isAnimal(Entity entity) {
        return entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature || entity instanceof EntityWaterMob || entity instanceof EntityGolem;
    }

    private static boolean isPlayerShielded(EntityPlayer player) {
        return player.getItemInUseCount() > 0 && (player.getHeldItemMainhand().getItem() instanceof ItemShield || player.getHeldItemOffhand().getItem() instanceof ItemShield && !KillAura.isPlayerUsingMainhand(player));
    }

    private static boolean isPlayerUsingMainhand(EntityPlayer player) {
        ItemStack main = player.getHeldItemMainhand();
        return player.getItemInUseCount() > 0 && (main.getItemUseAction() == EnumAction.EAT && !player.isCreative() && (player.getFoodStats().needFood() || main.getItem() instanceof ItemAppleGold) || main.getItemUseAction() == EnumAction.BOW && KillAura.canShootBow(player) || main.getItemUseAction() == EnumAction.DRINK || main.getItemUseAction() == EnumAction.BLOCK);
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

