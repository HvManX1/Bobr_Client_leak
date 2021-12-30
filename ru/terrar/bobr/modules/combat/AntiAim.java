//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ru.terrar.bobr.modules.combat;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.Setting;

public class AntiAim
extends Module {
    private transient long time;
    public static final AntiAim INSTANCE = new AntiAim();
    private float rot = 0.0f;
    private final Random e;
    private final Random e2;
    public int serv = 0;

    public AntiAim() {
        super("AntiAim", "antaim", Module.ModuleCategory.COMBAT);
        this.addSettings(new Setting[0]);
        this.e = new Random();
        this.e2 = new Random();
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
        float f = this.e2.nextFloat() * 360.0f;
        float cfr_ignored_0 = this.e2.nextFloat() * 180.0f - 90.0f;
        Minecraft.getMinecraft().player.rotationYawHead = f;
        Minecraft.getMinecraft().player.renderYawOffset = f;
    }
}

