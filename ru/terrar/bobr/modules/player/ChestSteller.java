//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.item.Item
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ru.terrar.bobr.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.FloatSetting;
import ru.terrar.bobr.wint.TimerHelper;

public class ChestSteller
extends Module {
    public static final ChestSteller INSTANCE = new ChestSteller();
    public Minecraft mc = Minecraft.getMinecraft();
    private Entity target;
    private String hef;
    private Entity focusTarget;
    private boolean checks = false;
    private boolean old = false;
    private String enter;
    private int x;
    private int y;
    private final FontRenderer fr;
    TimerHelper timer;
    public final FloatSetting speed;

    public ChestSteller() {
        super("ChestSteller", "ChestSteller", Module.ModuleCategory.PLAYER);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.timer = new TimerHelper();
        this.speed = new FloatSetting("Speed", "Speed", 0.0f, 0.0f, 1000.0f);
        this.addSettings(this.speed);
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        if (Minecraft.getMinecraft().player.openContainer != null && Minecraft.getMinecraft().player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)Minecraft.getMinecraft().player.openContainer;
            for (int i = 0; i < container.inventorySlots.size(); ++i) {
                if (container.getLowerChestInventory().getStackInSlot(i).getItem() != Item.getItemById((int)0) && this.timer.check(this.speed.getValue())) {
                    Minecraft.getMinecraft().playerController.windowClick(container.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)Minecraft.getMinecraft().player);
                    this.timer.reset();
                    continue;
                }
                if (!this.empty((Container)container)) continue;
                Minecraft.getMinecraft().player.closeScreen();
            }
        }
    }

    public boolean empty(Container container) {
        boolean voll = true;
        int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        int n = slotAmount;
        for (int i = 0; i < slotAmount; ++i) {
            if (!container.getSlot(i).getHasStack()) continue;
            voll = false;
        }
        return voll;
    }
}

