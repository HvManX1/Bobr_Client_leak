//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 *  net.minecraft.network.play.client.CPacketPlayer$PositionRotation
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package me.thef1xer.gateclient.modules.player;

import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Throwpot
extends Module {
    public static final Throwpot INSTANCE = new Throwpot();
    public Minecraft mc = Minecraft.getMinecraft();
    private Entity target;
    private String hef;
    private Entity focusTarget;
    private boolean checks = false;
    private boolean old = false;
    private String enter;
    private int x;
    private int y;
    public final FloatSetting hp = new FloatSetting("HP", "hp", 26.0f, 0.0f, 20.0f);
    private final FontRenderer fr;

    public Throwpot() {
        super("AutoPot", "autopot", Module.ModuleCategory.PLAYER);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.addSettings(this.hp);
    }

    @Override
    public void onEnable() {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        super.onEnable();
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
        if (Minecraft.getMinecraft().player.getHealth() <= this.hp.getValue()) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemStack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
                if (itemStack.getItem() != Items.SPLASH_POTION) continue;
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketHeldItemChange(i));
                int yaw = (int)Minecraft.getMinecraft().player.rotationYaw;
                int pitch = (int)Minecraft.getMinecraft().player.rotationPitch;
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, (float)yaw, 90.0f, Minecraft.getMinecraft().player.onGround));
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ, (float)yaw, (float)pitch, Minecraft.getMinecraft().player.onGround));
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketHeldItemChange(Minecraft.getMinecraft().player.inventory.currentItem));
                return;
            }
        }
    }
}

