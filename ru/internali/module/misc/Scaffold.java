//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 *  net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package ru.internali.module.misc;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.utils.BlockData;
import ru.internali.utils.TimerUtils;

public class Scaffold
extends Module {
    public TimerUtils timer = new TimerUtils();
    public BlockData blockData;
    boolean isBridging = false;
    BlockPos blockDown = null;
    public static float[] facingCam = null;
    float startYaw = 0.0f;
    float startPitch = 0.0f;

    public Scaffold() {
        super("Scaffold", "plase blocks", Category.OUTHER);
    }

    @Override
    public void onDisable() {
        facingCam = null;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.blockDown = null;
        facingCam = null;
        this.isBridging = false;
        this.startYaw = 0.0f;
        this.startPitch = 0.0f;
        if (Scaffold.mc.gameSettings.keyBindBack.isKeyDown()) {
            KeyBinding.setKeyBindState((int)Scaffold.mc.gameSettings.keyBindBack.getKeyCode(), (boolean)false);
        }
        super.onEnable();
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        int newSlot;
        EntityPlayerSP player = Scaffold.mc.player;
        int oldSlot = -1;
        if (!this.check()) {
            if (this.isBridging) {
                KeyBinding.setKeyBindState((int)Scaffold.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)Scaffold.isBlockMaterial(new BlockPos((Entity)player).down(), Blocks.AIR));
                this.isBridging = false;
                if (oldSlot != -1) {
                    player.inventory.currentItem = oldSlot;
                }
            }
            this.startYaw = 0.0f;
            this.startPitch = 0.0f;
            facingCam = null;
            this.blockDown = null;
            return;
        }
        this.startYaw = Scaffold.mc.player.rotationYaw;
        this.startPitch = Scaffold.mc.player.rotationPitch;
        KeyBinding.setKeyBindState((int)Scaffold.mc.gameSettings.keyBindRight.getKeyCode(), (boolean)false);
        KeyBinding.setKeyBindState((int)Scaffold.mc.gameSettings.keyBindLeft.getKeyCode(), (boolean)false);
        this.blockDown = new BlockPos((Entity)player).down();
        float r1 = new Random().nextFloat();
        if (r1 == 1.0f) {
            r1 -= 1.0f;
        }
        if ((newSlot = this.findSlotWithBlock()) == -1) {
            return;
        }
        oldSlot = player.inventory.currentItem;
        player.inventory.currentItem = newSlot;
        player.rotationPitch = Scaffold.updateRotation(player.rotationPitch, 82.0f - r1, 15.0f);
        int currentCPS = Scaffold.generateRandomIntIntRange(10, 15);
        if (this.timer.isDelay(1000 / currentCPS)) {
            Scaffold.clickMouse(1);
            Scaffold.mc.player.swingArm(EnumHand.MAIN_HAND);
            this.timer.setLastMS();
        }
        this.isBridging = true;
        KeyBinding.setKeyBindState((int)Scaffold.mc.gameSettings.keyBindSneak.getKeyCode(), (boolean)Scaffold.isBlockMaterial(new BlockPos((Entity)player).down(), Blocks.AIR));
    }

    @Override
    @SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        if (event.getEntity() == Scaffold.mc.player) {
            if (this.startYaw == 0.0f || this.startPitch == 0.0f) {
                return;
            }
            event.setYaw(this.startYaw);
            event.setPitch(this.startPitch - 70.0f);
            facingCam = new float[]{this.startYaw - 180.0f, this.startPitch - 70.0f};
        }
        super.onCameraSetup(event);
    }

    public static void clickMouse(int button) {
        block4: {
            try {
                Robot bot = new Robot();
                if (button == 0) {
                    bot.mousePress(16);
                    bot.mouseRelease(16);
                    break block4;
                }
                if (button == 1) {
                    bot.mousePress(4096);
                    bot.mouseRelease(4096);
                    break block4;
                }
                return;
            }
            catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public static boolean isBlockMaterial(BlockPos blockPos, Block block) {
        return Scaffold.getBlock(blockPos) == Blocks.AIR;
    }

    public static float updateRotation(float angle, float targetAngle, float maxIncrease) {
        float var4 = MathHelper.wrapDegrees((float)(targetAngle - angle));
        if (var4 > maxIncrease) {
            var4 = maxIncrease;
        }
        if (var4 < -maxIncrease) {
            var4 = -maxIncrease;
        }
        return angle + var4;
    }

    public static IBlockState getState(BlockPos pos) {
        return Scaffold.mc.world.getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return Scaffold.getState(pos).getBlock();
    }

    public int findSlotWithBlock() {
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = Scaffold.mc.player.inventory.getStackInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemBlock) || !(block = Block.getBlockFromItem((Item)stack.getItem()).getDefaultState().getBlock()).isFullBlock(Scaffold.getBlock(this.blockDown).getDefaultState()) || block == Blocks.SAND || block == Blocks.GRAVEL) continue;
            return i;
        }
        return -1;
    }

    boolean check() {
        RayTraceResult object = Scaffold.mc.objectMouseOver;
        EntityPlayerSP player = Scaffold.mc.player;
        ItemStack stack = player.inventory.getCurrentItem();
        if (object == null || stack == null) {
            return false;
        }
        if (object.typeOfHit != RayTraceResult.Type.BLOCK) {
            return false;
        }
        if (player.rotationPitch <= 70.0f || !player.onGround || player.isOnLadder() || player.isInLava() || player.isInWater()) {
            return false;
        }
        return Scaffold.mc.gameSettings.keyBindBack.isKeyDown();
    }
}

