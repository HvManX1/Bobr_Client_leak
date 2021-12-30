//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.network.Packet
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package me.thef1xer.gateclient.util;

import me.thef1xer.gateclient.events.GetAmbientOcclusionLightValueEvent;
import me.thef1xer.gateclient.events.GetLiquidCollisionBoundingBoxEvent;
import me.thef1xer.gateclient.events.PlayerIsUserEvent;
import me.thef1xer.gateclient.events.ReceivePacketEvent;
import me.thef1xer.gateclient.events.RenderModelEvent;
import me.thef1xer.gateclient.events.SendPacketEvent;
import me.thef1xer.gateclient.events.SetOpaqueCubeEvent;
import me.thef1xer.gateclient.events.ShouldSideBeRenderedEvent;
import me.thef1xer.gateclient.events.UpdateWalkingPlayerEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventFactory {
    public static boolean setOpaqueCube() {
        SetOpaqueCubeEvent setOpaqueCube = new SetOpaqueCubeEvent();
        return MinecraftForge.EVENT_BUS.post((Event)setOpaqueCube);
    }

    public static Packet<?> onSendPacket(Packet<?> packet) {
        SendPacketEvent event = new SendPacketEvent(packet);
        return MinecraftForge.EVENT_BUS.post((Event)event) ? null : event.getPacket();
    }

    public static Packet<?> onReceivePacket(Packet<?> packet) {
        ReceivePacketEvent event = new ReceivePacketEvent(packet);
        return MinecraftForge.EVENT_BUS.post((Event)event) ? null : event.getPacket();
    }

    public static boolean renderBlock(IBlockState stateIn) {
        return MinecraftForge.EVENT_BUS.post((Event)new RenderModelEvent(stateIn));
    }

    public static boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing facing) {
        ShouldSideBeRenderedEvent event = new ShouldSideBeRenderedEvent(state, state.getBlock().shouldSideBeRendered(state, blockAccess, pos, facing));
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.getShouldBeRendered();
    }

    public static float getAmbientOcclusionLightValue(IBlockState state) {
        GetAmbientOcclusionLightValueEvent event = new GetAmbientOcclusionLightValueEvent(state);
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.getLightValue();
    }

    public static AxisAlignedBB getCollisionBoundingBox() {
        GetLiquidCollisionBoundingBoxEvent event = new GetLiquidCollisionBoundingBoxEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        return event.getCollisionBoundingBox();
    }

    public static boolean isUser() {
        return !MinecraftForge.EVENT_BUS.post((Event)new PlayerIsUserEvent());
    }

    public static boolean onUpdateWalkingPlayer() {
        return MinecraftForge.EVENT_BUS.post((Event)new UpdateWalkingPlayerEvent());
    }
}

