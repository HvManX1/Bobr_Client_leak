//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketClickWindow
 */
package me.thef1xer.gateclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;

public class PlayerUtil {
    public static void swapItems(int slot1, int slot2) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot1, 0, ClickType.PICKUP, (EntityPlayer)Minecraft.getMinecraft().player);
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot2, 0, ClickType.PICKUP, (EntityPlayer)Minecraft.getMinecraft().player);
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot1, 0, ClickType.PICKUP, (EntityPlayer)Minecraft.getMinecraft().player);
        Minecraft.getMinecraft().playerController.updateController();
    }

    public static void swapInventoryItems(int slot1, int slot2) {
        short short1 = Minecraft.getMinecraft().player.inventoryContainer.getNextTransactionID(Minecraft.getMinecraft().player.inventory);
        ItemStack itemstack = Minecraft.getMinecraft().player.inventoryContainer.slotClick(slot1, 0, ClickType.PICKUP, (EntityPlayer)Minecraft.getMinecraft().player);
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketClickWindow(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot1, 0, ClickType.PICKUP, itemstack, short1));
        itemstack = Minecraft.getMinecraft().player.inventoryContainer.slotClick(slot2, 0, ClickType.PICKUP, (EntityPlayer)Minecraft.getMinecraft().player);
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketClickWindow(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot2, 0, ClickType.PICKUP, itemstack, short1));
        itemstack = Minecraft.getMinecraft().player.inventoryContainer.slotClick(slot1, 0, ClickType.PICKUP, (EntityPlayer)Minecraft.getMinecraft().player);
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketClickWindow(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot1, 0, ClickType.PICKUP, itemstack, short1));
        Minecraft.getMinecraft().playerController.updateController();
    }

    public static double[] getPlayerMoveVec() {
        float yaw = Minecraft.getMinecraft().player.rotationYaw;
        float forward = Minecraft.getMinecraft().player.moveForward;
        float strafe = Minecraft.getMinecraft().player.moveStrafing;
        if (forward == 0.0f && strafe == 0.0f) {
            return new double[]{0.0, 0.0};
        }
        if (forward > 0.0f) {
            if (strafe > 0.0f) {
                yaw -= 45.0f;
            } else if (strafe < 0.0f) {
                yaw += 45.0f;
            }
        } else if (forward < 0.0f) {
            yaw -= 180.0f;
            if (strafe > 0.0f) {
                yaw += 45.0f;
            } else if (strafe < 0.0f) {
                yaw -= 45.0f;
            }
        } else if (strafe > 0.0f) {
            yaw -= 90.0f;
        } else if (strafe < 0.0f) {
            yaw += 90.0f;
        }
        return new double[]{-Math.sin(Math.toRadians(yaw)), Math.cos(Math.toRadians(yaw))};
    }
}

