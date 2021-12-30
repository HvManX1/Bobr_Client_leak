//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ru.terrar.bobr.modules.combat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;
import ru.terrar.bobr.settings.impl.EnumSetting;
import ru.terrar.bobr.util.ChatUtil;

public class AntiBot
extends Module {
    private transient long time;
    public static final AntiBot INSTANCE = new AntiBot();
    public final EnumSetting mode = new EnumSetting("Mode", "Mode", Click.values(), Click.MATRIX);
    public final BooleanSetting Remove = new BooleanSetting("Remove", "Remove", true);
    private int but = 0;
    public static List<String> BOTS = new ArrayList<String>();
    public boolean toggle;

    public AntiBot() {
        super("AntiBot", "antibot", Module.ModuleCategory.COMBAT);
        this.addSettings(this.mode, this.Remove);
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
        block5: {
            block4: {
                if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
                    return;
                }
                if (this.mode.getCurrentValue() != Click.MATRIX) break block4;
                for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                    if (e == Minecraft.getMinecraft().player || e.ticksExisted >= 5 || !(e instanceof EntityOtherPlayerMP) || ((EntityOtherPlayerMP)e).hurtTime <= 0 || !(Minecraft.getMinecraft().player.getDistance(e) <= 25.0f) || Minecraft.getMinecraft().getConnection().getPlayerInfo(e.getUniqueID()).getResponseTime() == 0) continue;
                    BOTS.add(e.getName());
                    if (!this.Remove.getValue()) continue;
                    Minecraft.getMinecraft().world.removeEntity(e);
                }
                break block5;
            }
            if (this.mode.getCurrentValue() != Click.RUSTME) break block5;
            for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
                if (e == Minecraft.getMinecraft().player || e.ticksExisted >= 5 || !(e instanceof EntityOtherPlayerMP)) continue;
                if (e.isInvisibleToPlayer((EntityPlayer)Minecraft.getMinecraft().player)) {
                    BOTS.add(e.getName());
                    ChatUtil.clientMessage((Object)TextFormatting.RED + "[" + (Object)TextFormatting.GREEN + "AntiBot" + (Object)TextFormatting.RED + "] " + (Object)TextFormatting.RESET + e.getName() + (Object)TextFormatting.RESET + " Is Bot.");
                    if (!this.Remove.getValue()) continue;
                    Minecraft.getMinecraft().world.removeEntity(e);
                    continue;
                }
                if (((EntityOtherPlayerMP)e).hurtTime <= 0 || !(Minecraft.getMinecraft().player.getDistance(e) <= 7.0f) || Minecraft.getMinecraft().getConnection().getPlayerInfo(e.getUniqueID()).getResponseTime() == 0) continue;
                BOTS.add(e.getName());
                ChatUtil.clientMessage((Object)TextFormatting.RED + "[" + (Object)TextFormatting.GREEN + "AntiBot" + (Object)TextFormatting.RED + "] " + (Object)TextFormatting.RESET + e.getName() + (Object)TextFormatting.RESET + " Is Bot.");
                if (!this.Remove.getValue()) continue;
                Minecraft.getMinecraft().world.removeEntity(e);
            }
        }
    }

    public static boolean isBot(String nick) {
        if (AntiBot.INSTANCE.toggle) {
            for (String friend : BOTS) {
                if (!friend.equalsIgnoreCase(nick)) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public static enum Click {
        RUSTME("RustMe"),
        MATRIX("Matrix");

        private final String name;

        private Click(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

