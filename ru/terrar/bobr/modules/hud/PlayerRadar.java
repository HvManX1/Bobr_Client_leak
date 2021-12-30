//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 */
package ru.terrar.bobr.modules.hud;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import ru.terrar.bobr.managers.FriendManager;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.modules.combat.AntiBot;
import ru.terrar.bobr.settings.impl.FloatSetting;

public class PlayerRadar
extends Module {
    public static final PlayerRadar INSTANCE = new PlayerRadar();
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
    public final FloatSetting reach;

    public PlayerRadar() {
        super("PlayerRadar", "PlayerRadar", Module.ModuleCategory.HUD);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.reach = new FloatSetting("Range", "range", 100.0f, 0.0f, 300.0f);
        this.setEnabled(false);
        this.addSettings(this.reach);
    }

    public boolean isTarget(Entity entity) {
        return entity != Minecraft.getMinecraft().player && entity != Minecraft.getMinecraft().getRenderViewEntity() && Minecraft.getMinecraft().player.getDistanceSq(entity) <= Math.pow(this.reach.getValue(), 2.0) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }

    public void drawradar() {
        this.target = null;
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        int n = 0;
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (!(entity instanceof EntityPlayer) || entity == Minecraft.getMinecraft().player) continue;
            String s = entity.getName();
            int color = Color.WHITE.getRGB();
            if (AntiBot.isBot(entity.getName())) {
                color = Color.RED.getRGB();
            } else if (FriendManager.isFriend(entity.getName())) {
                color = Color.GREEN.getRGB();
            }
            s = s + " [" + ((EntityPlayer)entity).getHealth() + "/" + ((EntityPlayer)entity).getMaxHealth() + "] [" + (int)Minecraft.getMinecraft().player.getDistance(entity) + ']';
            this.fr.drawStringWithShadow(s, 0.0f, (float)(30 + n * this.fr.FONT_HEIGHT), color);
            ++n;
        }
    }
}

