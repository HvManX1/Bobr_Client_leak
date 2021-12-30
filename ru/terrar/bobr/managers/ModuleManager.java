/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.managers;

import java.util.ArrayList;
import java.util.List;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.modules.combat.AimPistot;
import ru.terrar.bobr.modules.combat.AntiAim;
import ru.terrar.bobr.modules.combat.AntiBot;
import ru.terrar.bobr.modules.combat.AutoTotem;
import ru.terrar.bobr.modules.combat.HitBoxMod;
import ru.terrar.bobr.modules.combat.KillAura;
import ru.terrar.bobr.modules.combat.MiddleClickPearl;
import ru.terrar.bobr.modules.combat.TriggerBot;
import ru.terrar.bobr.modules.combat.Velocity;
import ru.terrar.bobr.modules.combat.aimAssist;
import ru.terrar.bobr.modules.hud.ClickGuiModule;
import ru.terrar.bobr.modules.hud.Coords;
import ru.terrar.bobr.modules.hud.DiscordRPC;
import ru.terrar.bobr.modules.hud.ModuleList;
import ru.terrar.bobr.modules.hud.PlayerRadar;
import ru.terrar.bobr.modules.hud.TargetHud;
import ru.terrar.bobr.modules.hud.Watermark;
import ru.terrar.bobr.modules.movement.GuiMove;
import ru.terrar.bobr.modules.movement.NoSlow;
import ru.terrar.bobr.modules.movement.SafeWalk;
import ru.terrar.bobr.modules.movement.Sprint;
import ru.terrar.bobr.modules.player.AutoArmor;
import ru.terrar.bobr.modules.player.AutoDisconnect;
import ru.terrar.bobr.modules.player.Freecam;
import ru.terrar.bobr.modules.player.NoPush;
import ru.terrar.bobr.modules.player.Throwpot;
import ru.terrar.bobr.modules.render.EntityESP;
import ru.terrar.bobr.modules.render.FullBright;
import ru.terrar.bobr.modules.render.Glowing;
import ru.terrar.bobr.modules.render.NameTags;
import ru.terrar.bobr.modules.render.NoOverlay;
import ru.terrar.bobr.modules.render.StorageESP;
import ru.terrar.bobr.modules.render.Tracers;
import ru.terrar.bobr.modules.render.WallHack;
import ru.terrar.bobr.modules.render.XRay;

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
        this.MODULE_LIST.add(AntiAim.INSTANCE);
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

