//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 */
package me.thef1xer.gateclient.commands.impl;

import me.thef1xer.gateclient.commands.Command;
import me.thef1xer.gateclient.util.ChatUtil;
import me.thef1xer.gateclient.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class ClipCommand
extends Command {
    private final Minecraft mc = Minecraft.getMinecraft();

    public ClipCommand() {
        super("clip", "Allows you to clip in a direction", "clip <v/h> <blocks>", "clip <blocks horizontal> <blocks vertical>");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("h")) {
                try {
                    EntityPlayerSP entity;
                    double distance = Double.parseDouble(args[2]);
                    double[] vector = MathUtil.getDirection(this.mc.player.rotationYaw);
                    Object object = entity = this.mc.player.isRiding() ? this.mc.player.getRidingEntity() : this.mc.player;
                    if (entity == null) {
                        return;
                    }
                    entity.setPosition(entity.posX + vector[0] * distance, entity.posY, entity.posZ + vector[1] * distance);
                    ChatUtil.clientMessage("Teleported you " + args[2] + " blocks forward");
                }
                catch (Exception e) {
                    this.syntaxError();
                }
            } else if (args[1].equalsIgnoreCase("v")) {
                try {
                    EntityPlayerSP entity;
                    double distance = Double.parseDouble(args[2]);
                    Object object = entity = this.mc.player.isRiding() ? this.mc.player.getRidingEntity() : this.mc.player;
                    if (entity == null) {
                        return;
                    }
                    entity.setPosition(entity.posX, entity.posY + distance, entity.posZ);
                    ChatUtil.clientMessage("Teleported you " + args[2] + " blocks up");
                }
                catch (Exception e) {
                    this.syntaxError();
                }
            } else {
                try {
                    EntityPlayerSP entity;
                    double dh = Double.parseDouble(args[1]);
                    double dv = Double.parseDouble(args[2]);
                    double[] vector = MathUtil.getDirection(this.mc.player.rotationYaw);
                    Object object = entity = this.mc.player.isRiding() ? this.mc.player.getRidingEntity() : this.mc.player;
                    if (entity == null) {
                        return;
                    }
                    entity.setPosition(entity.posX + vector[0] * dh, entity.posY + dv, entity.posZ + vector[1] * dh);
                    ChatUtil.clientMessage("Teleported you " + args[1] + " blocks forward and " + args[2] + " blocks up");
                }
                catch (Exception e) {
                    this.syntaxError();
                }
            }
            return;
        }
        this.syntaxError();
    }
}

