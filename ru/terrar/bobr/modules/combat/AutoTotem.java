//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package ru.terrar.bobr.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.util.PlayerUtil;

public class AutoTotem
extends Module {
    public static final AutoTotem INSTANCE = new AutoTotem();

    public AutoTotem() {
        super("Auto Totem", "autototem", Module.ModuleCategory.COMBAT);
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

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiContainer) {
            return;
        }
        if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() != Item.getItemById((int)449)) {
            for (int slot = 0; slot < Minecraft.getMinecraft().player.inventoryContainer.inventorySlots.size(); ++slot) {
                ItemStack itemStack = ((Slot)Minecraft.getMinecraft().player.inventoryContainer.inventorySlots.get(slot)).getStack();
                if (itemStack.getItem() != Item.getItemById((int)449)) continue;
                PlayerUtil.swapInventoryItems(slot, 45);
                break;
            }
        }
    }
}

