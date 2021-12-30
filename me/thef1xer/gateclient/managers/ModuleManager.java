/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.managers;

import java.util.ArrayList;
import java.util.List;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.modules.combat.AimPistot;
import me.thef1xer.gateclient.modules.combat.AntiBot;
import me.thef1xer.gateclient.modules.combat.AutoTotem;
import me.thef1xer.gateclient.modules.combat.HitBoxMod;
import me.thef1xer.gateclient.modules.combat.KillAura;
import me.thef1xer.gateclient.modules.combat.MiddleClickPearl;
import me.thef1xer.gateclient.modules.combat.TriggerBot;
import me.thef1xer.gateclient.modules.combat.Velocity;
import me.thef1xer.gateclient.modules.combat.aimAssist;
import me.thef1xer.gateclient.modules.hud.ClickGuiModule;
import me.thef1xer.gateclient.modules.hud.Coords;
import me.thef1xer.gateclient.modules.hud.DiscordRPC;
import me.thef1xer.gateclient.modules.hud.ModuleList;
import me.thef1xer.gateclient.modules.hud.PlayerRadar;
import me.thef1xer.gateclient.modules.hud.TargetHud;
import me.thef1xer.gateclient.modules.hud.Watermark;
import me.thef1xer.gateclient.modules.movement.GuiMove;
import me.thef1xer.gateclient.modules.movement.NoSlow;
import me.thef1xer.gateclient.modules.movement.SafeWalk;
import me.thef1xer.gateclient.modules.movement.Sprint;
import me.thef1xer.gateclient.modules.player.AutoArmor;
import me.thef1xer.gateclient.modules.player.AutoDisconnect;
import me.thef1xer.gateclient.modules.player.Freecam;
import me.thef1xer.gateclient.modules.player.NoPush;
import me.thef1xer.gateclient.modules.player.Throwpot;
import me.thef1xer.gateclient.modules.render.EntityESP;
import me.thef1xer.gateclient.modules.render.FullBright;
import me.thef1xer.gateclient.modules.render.Glowing;
import me.thef1xer.gateclient.modules.render.NameTags;
import me.thef1xer.gateclient.modules.render.NoOverlay;
import me.thef1xer.gateclient.modules.render.StorageESP;
import me.thef1xer.gateclient.modules.render.Tracers;
import me.thef1xer.gateclient.modules.render.WallHack;
import me.thef1xer.gateclient.modules.render.XRay;

public class ModuleManager {
    public final List<Module> MODULE_LIST = new ArrayList<Module>();

    public void init() {
        this.MODULE_LIST.add(AutoTotem.INSTANCE);
        this.MODULE_LIST.add(TriggerBot.INSTANCE);
        this.MODULE_LIST.add(KillAura.INSTANCE);
        this.MODULE_LIST.add(aimAssist.INSTANCE);
        this.MODULE_LIST.add(HitBoxMod.INSTANCE);
        this.MODULE_LIST.add(Velocity.INSTANCE);
        this.MODULE_LIST.add(AntiBot.INSTANCE);
        this.MODULE_LIST.add(MiddleClickPearl.INSTANCE);
        this.MODULE_LIST.add(AimPistot.INSTANCE);
        this.MODULE_LIST.add(ClickGuiModule.INSTANCE);
        this.MODULE_LIST.add(Coords.INSTANCE);
        this.MODULE_LIST.add(ModuleList.INSTANCE);
        this.MODULE_LIST.add(Watermark.INSTANCE);
        this.MODULE_LIST.add(TargetHud.INSTANCE);
        this.MODULE_LIST.add(DiscordRPC.INSTANCE);
        this.MODULE_LIST.add(PlayerRadar.INSTANCE);
        this.MODULE_LIST.add(GuiMove.INSTANCE);
        this.MODULE_LIST.add(NoSlow.INSTANCE);
        this.MODULE_LIST.add(SafeWalk.INSTANCE);
        this.MODULE_LIST.add(Sprint.INSTANCE);
        this.MODULE_LIST.add(AutoArmor.INSTANCE);
        this.MODULE_LIST.add(AutoDisconnect.INSTANCE);
        this.MODULE_LIST.add(Freecam.INSTANCE);
        this.MODULE_LIST.add(NoPush.INSTANCE);
        this.MODULE_LIST.add(Throwpot.INSTANCE);
        this.MODULE_LIST.add(EntityESP.INSTANCE);
        this.MODULE_LIST.add(StorageESP.INSTANCE);
        this.MODULE_LIST.add(FullBright.INSTANCE);
        this.MODULE_LIST.add(NoOverlay.INSTANCE);
        this.MODULE_LIST.add(Tracers.INSTANCE);
        this.MODULE_LIST.add(XRay.INSTANCE);
        this.MODULE_LIST.add(NameTags.INSTANCE);
        this.MODULE_LIST.add(Glowing.INSTANCE);
        this.MODULE_LIST.add(WallHack.INSTANCE);
    }
}

