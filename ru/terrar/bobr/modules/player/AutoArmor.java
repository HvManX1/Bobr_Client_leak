//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.inventory.EntityEquipmentSlot
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package ru.terrar.bobr.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.util.PlayerUtil;

public class AutoArmor
extends Module {
    public static final AutoArmor INSTANCE = new AutoArmor();

    public AutoArmor() {
        super("Auto Armor", "autoarmor", Module.ModuleCategory.PLAYER);
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

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null) {
            boolean hasHelmet;
            if (Minecraft.getMinecraft().currentScreen instanceof GuiContainer) {
                return;
            }
            boolean hasBoots = !((ItemStack)Minecraft.getMinecraft().player.inventory.armorInventory.get(0)).isEmpty();
            boolean hasLeggings = !((ItemStack)Minecraft.getMinecraft().player.inventory.armorInventory.get(1)).isEmpty();
            boolean hasChestplate = !((ItemStack)Minecraft.getMinecraft().player.inventory.armorInventory.get(2)).isEmpty();
            boolean bl = hasHelmet = !((ItemStack)Minecraft.getMinecraft().player.inventory.armorInventory.get(3)).isEmpty();
            if (!(hasBoots && hasLeggings && hasChestplate && hasHelmet)) {
                for (int slot = 0; slot < Minecraft.getMinecraft().player.inventoryContainer.inventorySlots.size(); ++slot) {
                    Item slotItem = ((Slot)Minecraft.getMinecraft().player.inventoryContainer.inventorySlots.get(slot)).getStack().getItem();
                    if (!(slotItem instanceof ItemArmor)) continue;
                    ItemArmor itemArmor = (ItemArmor)slotItem;
                    System.out.println(slot);
                    if (!hasHelmet && itemArmor.armorType == EntityEquipmentSlot.HEAD) {
                        PlayerUtil.swapInventoryItems(slot, 5);
                        hasHelmet = true;
                    }
                    if (!hasChestplate && itemArmor.armorType == EntityEquipmentSlot.CHEST) {
                        PlayerUtil.swapInventoryItems(slot, 6);
                        hasChestplate = true;
                    }
                    if (!hasLeggings && itemArmor.armorType == EntityEquipmentSlot.LEGS) {
                        PlayerUtil.swapInventoryItems(slot, 7);
                        hasLeggings = true;
                    }
                    if (hasBoots || itemArmor.armorType != EntityEquipmentSlot.FEET) continue;
                    PlayerUtil.swapInventoryItems(slot, 8);
                    hasBoots = true;
                }
            }
        }
    }
}

