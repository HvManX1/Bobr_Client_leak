//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.terrar.bobr.modules.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.terrar.bobr.events.GetLiquidCollisionBoundingBoxEvent;
import ru.terrar.bobr.events.PlayerMoveEvent;
import ru.terrar.bobr.events.UpdateWalkingPlayerEvent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.FloatSetting;

public class Jesus
extends Module {
    public static final Jesus INSTANCE = new Jesus();
    public final FloatSetting offset = new FloatSetting("Offset", "offset", 0.2f, 0.0f, 1.0f);
    private final Minecraft mc = Minecraft.getMinecraft();

    public Jesus() {
        super("Jesus", "jesus", Module.ModuleCategory.MOVEMENT);
        this.addSettings(this.offset);
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    public void onLiquidCollision(GetLiquidCollisionBoundingBoxEvent event) {
        if (this.mc.world == null || this.mc.player == null) {
            return;
        }
        if (this.mc.player.isSneaking() || this.mc.player.fallDistance > 3.0f || this.isInLiquid() || this.mc.player.motionY >= (double)0.1f) {
            return;
        }
        event.setCollisionBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0 - (double)this.offset.getValue(), 1.0));
    }

    @SubscribeEvent
    public void onUndateWalking(UpdateWalkingPlayerEvent event) {
        if (this.isWaterWalking() && !this.mc.player.isSneaking() && this.mc.player.ticksExisted % 2 == 0) {
            EntityPlayerSP player = this.mc.player;
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(player.posX, player.posY + (double)this.offset.getValue(), player.posZ, player.rotationYaw, player.rotationPitch, player.onGround));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onMove(PlayerMoveEvent event) {
        if (this.isInLiquid() && !this.mc.player.isSneaking()) {
            event.y = 0.1;
        }
    }

    private boolean isOverLiquid() {
        if (this.mc.player != null) {
            AxisAlignedBB bb = this.mc.player.getEntityBoundingBox().offset(0.0, -0.1, 0.0);
            for (int x = MathHelper.floor((double)bb.minX); x < MathHelper.floor((double)bb.maxX) + 1; ++x) {
                for (int z = MathHelper.floor((double)bb.minZ); z < MathHelper.floor((double)bb.minZ) + 1; ++z) {
                    Block block = this.mc.world.getBlockState(new BlockPos((double)x, bb.minY, (double)z)).getBlock();
                    if (block instanceof BlockLiquid) continue;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean isWaterWalking() {
        return !this.isInLiquid() && this.isOverLiquid();
    }

    private boolean isInLiquid() {
        AxisAlignedBB bb = this.mc.player.getEntityBoundingBox().offset(0.0, (double)this.offset.getValue(), 0.0);
        for (int x = MathHelper.floor((double)bb.minX); x < MathHelper.ceil((double)bb.maxX); ++x) {
            for (int z = MathHelper.floor((double)bb.minZ); z < MathHelper.ceil((double)bb.minZ); ++z) {
                Block block = this.mc.world.getBlockState(new BlockPos((double)x, bb.minY, (double)z)).getBlock();
                if (!(block instanceof BlockLiquid)) continue;
                return true;
            }
        }
        return false;
    }
}

