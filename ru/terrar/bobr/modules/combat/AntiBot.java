//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.input.Mouse
 */
package ru.terrar.bobr.modules.combat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;
import ru.terrar.bobr.settings.impl.EnumSetting;
import ru.terrar.bobr.util.ChatUtil;

public class AntiBot
extends Module {
    private transient long time;
    public static final AntiBot INSTANCE = new AntiBot();
    public final EnumSetting click = new EnumSetting("Click", "Click", Click.values(), Click.LEFT);
    public final BooleanSetting clicks = new BooleanSetting("Clicks", "Clicks", true);
    public final BooleanSetting InvisibleRemove = new BooleanSetting("InvisibleRemove", "InvisibleRemove", true);
    private int but = 0;
    public static List<String> BOTS = new ArrayList<String>();
    public boolean toggle;

    public AntiBot() {
        super("AntiBot", "antibot", Module.ModuleCategory.COMBAT);
        this.addSettings(this.clicks, this.click, this.InvisibleRemove);
    }

    @Override
    public void onEnable() {
        this.toggle = true;
        super.onEnable();
        this.time = System.currentTimeMillis();
        MinecraftForge.EVENT_BUS.register((Object)this);
        BOTS.clear();
    }

    @Override
    public void onDisable() {
        this.toggle = false;
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (!entity.isInvisibleToPlayer((EntityPlayer)Minecraft.getMinecraft().player) || !this.InvisibleRemove.getValue() || !(entity instanceof EntityPlayer)) continue;
            Minecraft.getMinecraft().world.removeEntity(entity);
            ChatUtil.clientMessage(entity.getName() + " Removed");
        }
        try {
            for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                if (e == Minecraft.getMinecraft().player || e.ticksExisted >= 5 || !(e instanceof EntityOtherPlayerMP) || !(e instanceof EntityPlayer) || !(Minecraft.getMinecraft().player.getDistance(e) <= 8.0f) || Minecraft.getMinecraft().getConnection().getPlayerInfo(e.getUniqueID()).getResponseTime() == 0) continue;
                Minecraft.getMinecraft().world.removeEntity(e);
                ChatUtil.clientMessage(e.getName() + " Removed");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Entity entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
            if (entity != null && entity instanceof EntityPlayer && this.clicks.getValue()) {
                if (this.click.getCurrentValue() == Click.LEFT) {
                    this.but = 0;
                } else if (this.click.getCurrentValue() == Click.RIGHT) {
                    this.but = 1;
                } else if (this.click.getCurrentValue() == Click.MIDDLE) {
                    this.but = 2;
                } else if (this.click.getCurrentValue() == Click.NO && AntiBot.isBot(entity.getName())) {
                    BOTS.add(entity.getName());
                    ChatUtil.clientMessage(entity.getName() + " is Not Bot");
                }
                if (Mouse.isButtonDown((int)this.but) && this.click.getCurrentValue() != Click.NO && AntiBot.isBot(entity.getName())) {
                    BOTS.add(entity.getName());
                    ChatUtil.clientMessage(entity.getName() + " is Not Bot");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isBot(String nick) {
        if (AntiBot.INSTANCE.toggle && AntiBot.INSTANCE.clicks.getValue()) {
            for (String friend : BOTS) {
                if (!friend.equalsIgnoreCase(nick)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static enum Click {
        NO("NO"),
        LEFT("Left"),
        MIDDLE("Middle"),
        RIGHT("Right");

        private final String name;

        private Click(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

