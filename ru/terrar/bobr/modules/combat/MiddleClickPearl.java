//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.input.Mouse
 */
package ru.terrar.bobr.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.Setting;

public class MiddleClickPearl
extends Module {
    private transient long time;
    public static final MiddleClickPearl INSTANCE = new MiddleClickPearl();

    public MiddleClickPearl() {
        super("MiddleClickPearl", "middleclickpearl", Module.ModuleCategory.COMBAT);
        this.addSettings(new Setting[0]);
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
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null && Mouse.isButtonDown((int)2)) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemStack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
                if (itemStack.getItem() != Items.ENDER_PEARL) continue;
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketHeldItemChange(Minecraft.getMinecraft().player.inventory.currentItem));
            }
        }
    }
}

