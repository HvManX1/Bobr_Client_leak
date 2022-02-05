//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.player;

import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.settings.Setting;

public class FreeCum
extends Module {
    private float yaw;
    private float pitch;
    private float yawHead;
    private float gamma;
    private EntityOtherPlayerMP other;
    private float old;
    private EntityOtherPlayerMP fakePlayer = null;
    private double oldX;
    private double oldY;
    private double oldZ;

    public FreeCum() {
        super("FreeCum", "FreeCum", Category.PLAYER);
        CatClient.instance.settingsManager.rSetting(new Setting("Speed", this, 0.1, 0.01, 0.5, false));
    }

    @Override
    public void onDisable() {
        FreeCum.mc.player.capabilities.isFlying = false;
        FreeCum.mc.player.capabilities.setFlySpeed(this.old);
        FreeCum.mc.player.rotationPitch = this.pitch;
        FreeCum.mc.player.rotationYaw = this.yaw;
        FreeCum.mc.world.removeEntityFromWorld(-1);
        FreeCum.mc.player.noClip = false;
        FreeCum.mc.renderGlobal.loadRenderers();
        FreeCum.mc.player.noClip = false;
        FreeCum.mc.player.setPositionAndRotation(this.oldX, this.oldY, this.oldZ, FreeCum.mc.player.rotationYaw, FreeCum.mc.player.rotationPitch);
        FreeCum.mc.world.removeEntityFromWorld(-69);
        this.fakePlayer = null;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.oldX = FreeCum.mc.player.posX;
        this.oldY = FreeCum.mc.player.posY;
        this.oldZ = FreeCum.mc.player.posZ;
        FreeCum.mc.player.noClip = true;
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)FreeCum.mc.world, FreeCum.mc.player.getGameProfile());
        fakePlayer.copyLocationAndAnglesFrom((Entity)FreeCum.mc.player);
        fakePlayer.posY -= 0.0;
        fakePlayer.rotationYawHead = FreeCum.mc.player.rotationYawHead;
        FreeCum.mc.world.addEntityToWorld(-69, (Entity)fakePlayer);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        FreeCum.mc.player.noClip = true;
        FreeCum.mc.player.onGround = false;
        FreeCum.mc.player.capabilities.setFlySpeed((float)CatClient.instance.settingsManager.getSettingByName(this, "Speed").getValDouble());
        FreeCum.mc.player.capabilities.isFlying = true;
        if (!(FreeCum.mc.player.isInsideOfMaterial(Material.AIR) || FreeCum.mc.player.isInsideOfMaterial(Material.LAVA) || FreeCum.mc.player.isInsideOfMaterial(Material.WATER))) {
            if (!(FreeCum.mc.gameSettings.gammaSetting < 100.0f)) {
                return;
            }
            FreeCum.mc.gameSettings.gammaSetting += 0.08f;
            return;
        }
        FreeCum.mc.gameSettings.gammaSetting = this.gamma;
    }
}

