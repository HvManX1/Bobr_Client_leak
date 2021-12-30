//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.terrar.bobr.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.terrar.bobr.events.PlayerMoveEvent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;

public class Strafe
extends Module {
    public static final Strafe INSTANCE = new Strafe();
    int waitCounter = 0;
    int forward = 1;
    private double motionSpeed;
    private int currentState;
    private double prevDist;
    private final Minecraft mc = Minecraft.getMinecraft();
    public final BooleanSetting jump = new BooleanSetting("Jump", "jump", false);

    public Strafe() {
        super("Strafe", "Strafe", Module.ModuleCategory.MOVEMENT);
        this.addSettings(this.jump);
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        super.onDisable();
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        boolean boost;
        if (this.mc.player.isSneaking() || this.mc.player.isInWater() || this.mc.player.isInLava() || this.mc.player.isOnLadder() || this.mc.player.isElytraFlying() || this.mc.player.capabilities.isFlying) {
            return;
        }
        boolean bl = boost = Math.abs(this.mc.player.rotationYawHead - this.mc.player.rotationYaw) < 90.0f;
        if ((this.isPlayerTryingMoveForward() || this.isPlayerTryingStrafe()) && this.mc.player.onGround) {
            this.mc.player.motionY = 0.4;
        }
        if (this.mc.player.moveForward != 0.0f) {
            if (!this.mc.player.isSprinting()) {
                this.mc.player.setSprinting(true);
            }
            float yaw = this.mc.player.rotationYaw;
            if (this.mc.player.moveForward > 0.0f) {
                if (this.mc.player.movementInput.moveStrafe != 0.0f) {
                    yaw += this.mc.player.movementInput.moveStrafe > 0.0f ? -45.0f : 45.0f;
                }
                this.forward = 1;
                this.mc.player.moveForward = 1.5f;
                this.mc.player.moveStrafing = 1.5f;
            } else if (this.mc.player.moveForward < 0.0f) {
                if (this.mc.player.movementInput.moveStrafe != 0.0f) {
                    yaw += this.mc.player.movementInput.moveStrafe > 0.0f ? 45.0f : -45.0f;
                }
                this.forward = -1;
                this.mc.player.moveForward = -1.5f;
                this.mc.player.moveStrafing = 1.5f;
            }
            if (this.mc.player.onGround) {
                float f = (float)Math.toRadians(yaw);
                if (this.jump.getValue() && this.mc.gameSettings.keyBindJump.isPressed()) {
                    this.Move(f);
                }
            } else {
                double speed;
                if (this.waitCounter < 1) {
                    ++this.waitCounter;
                    return;
                }
                this.waitCounter = 0;
                double currentSpeed = Math.sqrt(this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ);
                double d = speed = boost ? 1.05 : 1.025;
                if (this.mc.player.motionY < 0.0) {
                    speed = 1.0;
                }
                double direction = Math.toRadians(yaw);
                this.mc.player.motionX = -Math.sin(direction) * speed * currentSpeed * (double)this.forward;
                this.mc.player.motionZ = Math.cos(direction) * speed * currentSpeed * (double)this.forward;
            }
        }
        if (!this.isPlayerTryingMoveForward() && !this.isPlayerTryingStrafe()) {
            this.mc.player.motionX = 0.0;
            this.mc.player.motionZ = 0.0;
        }
    }

    private void Move(float f) {
        if (this.jump.getValue()) {
            this.mc.player.motionY = 0.4;
        }
        this.mc.player.motionX -= (double)(MathHelper.sin((float)f) * 0.2f) * (double)this.forward;
        this.mc.player.motionZ += (double)(MathHelper.cos((float)f) * 0.2f) * (double)this.forward;
    }

    public boolean isPlayerTryingMoveForward() {
        return this.mc.gameSettings.keyBindForward.isKeyDown() || this.mc.gameSettings.keyBindBack.isKeyDown();
    }

    public boolean isPlayerTryingStrafe() {
        return this.mc.gameSettings.keyBindRight.isKeyDown() || this.mc.gameSettings.keyBindLeft.isKeyDown();
    }
}

