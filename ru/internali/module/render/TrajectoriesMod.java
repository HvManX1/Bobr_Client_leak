//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemEgg
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemLingeringPotion
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemSnowball
 *  net.minecraft.item.ItemSplashPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package ru.internali.module.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import ru.internali.module.Category;
import ru.internali.module.Module;

public class TrajectoriesMod
extends Module {
    public static List<String> listA = new ArrayList<String>();
    private transient int BOX = 0;

    public TrajectoriesMod() {
        super("Trajectories", "Show how will fly peral or arrow", Category.RENDER);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static void drawSolidBox(AxisAlignedBB bb) {
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.BOX = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.BOX, (int)4864);
        TrajectoriesMod.drawSolidBox(new AxisAlignedBB(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5));
        GL11.glEndList();
    }

    public static boolean isCollidable(Block block) {
        return block != Blocks.AIR && block != Blocks.BEETROOTS && block != Blocks.CARROTS && block != Blocks.DEADBUSH && block != Blocks.DOUBLE_PLANT && block != Blocks.FLOWING_LAVA && block != Blocks.FLOWING_WATER && block != Blocks.LAVA && block != Blocks.MELON_STEM && block != Blocks.NETHER_WART && block != Blocks.POTATOES && block != Blocks.PUMPKIN_STEM && block != Blocks.RED_FLOWER && block != Blocks.RED_MUSHROOM && block != Blocks.REDSTONE_TORCH && block != Blocks.TALLGRASS && block != Blocks.TORCH && block != Blocks.UNLIT_REDSTONE_TORCH && block != Blocks.YELLOW_FLOWER && block != Blocks.VINE && block != Blocks.WATER && block != Blocks.WEB && block != Blocks.WHEAT;
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        ItemStack stack = player.inventory.getCurrentItem();
        if (stack == null) {
            return;
        }
        Item item = stack.getItem();
        if (!(item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemFishingRod)) {
            return;
        }
        boolean bow = stack.getItem() instanceof ItemBow;
        double arrowPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)mc.getRenderPartialTicks() - Math.cos((float)Math.toRadians(player.rotationYaw)) * (double)0.08f;
        double arrowPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)mc.getRenderPartialTicks() + (double)player.getEyeHeight() - 0.04;
        double arrowPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)mc.getRenderPartialTicks() - Math.sin((float)Math.toRadians(player.rotationYaw)) * (double)0.08f;
        float arrowMotionFactor = bow ? 1.0f : 0.4f;
        float yaw = (float)Math.toRadians(player.rotationYaw);
        float pitch = (float)Math.toRadians(player.rotationPitch);
        double arrowMotionX = -Math.sin(yaw) * Math.cos(pitch) * (double)arrowMotionFactor;
        double arrowMotionY = -Math.sin(pitch) * (double)arrowMotionFactor;
        double arrowMotionZ = Math.cos(yaw) * Math.cos(pitch) * (double)arrowMotionFactor;
        double arrowMotion = Math.sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
        arrowMotionX /= arrowMotion;
        arrowMotionY /= arrowMotion;
        arrowMotionZ /= arrowMotion;
        if (bow) {
            float bowPower = (float)(72000 - player.getItemInUseCount()) / 20.0f;
            if ((bowPower = (bowPower * bowPower + bowPower * 2.0f) / 3.0f) > 1.0f || bowPower <= 0.1f) {
                bowPower = 1.0f;
            }
            arrowMotionX *= (double)(bowPower *= 3.0f);
            arrowMotionY *= (double)bowPower;
            arrowMotionZ *= (double)bowPower;
        } else {
            arrowMotionX *= 1.5;
            arrowMotionY *= 1.5;
            arrowMotionZ *= 1.5;
        }
        GL11.glPushAttrib((int)24837);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double gravity = bow ? 0.005 : (item instanceof ItemPotion ? 0.04 : (item instanceof ItemFishingRod ? 0.015 : 0.003));
        Vec3d eyePos = new Vec3d(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
        boolean hit = false;
        boolean predictedHit = this.predictHit(eyePos, new Vec3d(arrowPosX, arrowPosY, arrowPosZ), new Vec3d(arrowMotionX, arrowMotionY, arrowMotionZ), gravity);
        if (predictedHit) {
            GL11.glColor4f((float)0.9f, (float)0.2f, (float)0.1f, (float)0.5f);
        } else {
            GL11.glColor4f((float)0.0f, (float)0.9f, (float)0.8f, (float)0.5f);
        }
        GL11.glBegin((int)3);
        for (int i = 0; i < 1000; ++i) {
            Block block;
            if (Minecraft.getMinecraft().world.rayTraceBlocks(eyePos, new Vec3d(arrowPosX, arrowPosY, arrowPosZ)) != null) {
                if (predictedHit) {
                    GL11.glColor4f((float)0.3f, (float)0.1f, (float)0.1f, (float)0.5f);
                } else {
                    GL11.glColor4f((float)0.1f, (float)0.3f, (float)0.3f, (float)0.5f);
                }
            } else if (predictedHit) {
                GL11.glColor4f((float)0.9f, (float)0.2f, (float)0.1f, (float)0.5f);
            } else {
                GL11.glColor4f((float)0.0f, (float)0.9f, (float)0.8f, (float)0.5f);
            }
            GL11.glVertex3d((double)(arrowPosX - renderManager.viewerPosX), (double)(arrowPosY - renderManager.viewerPosY), (double)(arrowPosZ - renderManager.viewerPosZ));
            arrowPosX += arrowMotionX * 0.1;
            arrowPosY += arrowMotionY * 0.1;
            arrowPosZ += arrowMotionZ * 0.1;
            arrowMotionX *= 0.999;
            arrowMotionY *= 0.999;
            arrowMotionZ *= 0.999;
            arrowMotionY -= gravity;
            for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
                if (!(entity instanceof EntityLiving) || !entity.getEntityBoundingBox().grow(0.35, 0.35, 0.35).contains(new Vec3d(arrowPosX, arrowPosY, arrowPosZ))) continue;
                hit = true;
                break;
            }
            if (hit) break;
            for (Entity entity : Minecraft.getMinecraft().world.playerEntities) {
                if (entity == Minecraft.getMinecraft().player || !entity.getEntityBoundingBox().grow(0.35, 0.35, 0.35).contains(new Vec3d(arrowPosX, arrowPosY, arrowPosZ))) continue;
                hit = true;
                break;
            }
            if (hit || TrajectoriesMod.isCollidable(block = Minecraft.getMinecraft().world.getBlockState(new BlockPos(new Vec3d(arrowPosX, arrowPosY, arrowPosZ))).getBlock())) break;
        }
        GL11.glEnd();
        GL11.glPushMatrix();
        GL11.glTranslated((double)(arrowPosX - renderManager.viewerPosX), (double)(arrowPosY - renderManager.viewerPosY), (double)(arrowPosZ - renderManager.viewerPosZ));
        GL11.glCallList((int)this.BOX);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }

    boolean predictHit(Vec3d eyePos, Vec3d arrowPos, Vec3d arrowMotion, double gravity) {
        boolean hit = false;
        for (int i = 0; i < 250; ++i) {
            Block block;
            arrowPos = arrowPos.add(arrowMotion.scale(0.4));
            arrowMotion = new Vec3d(arrowMotion.x * 0.996, arrowMotion.y * 0.996 - gravity * 4.0, arrowMotion.z * 0.996);
            for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
                if (!(entity instanceof EntityLiving) || !entity.getEntityBoundingBox().grow(0.35, 0.35, 0.35).contains(arrowPos)) continue;
                hit = true;
                break;
            }
            if (hit) break;
            for (Entity entity : Minecraft.getMinecraft().world.playerEntities) {
                if (entity == Minecraft.getMinecraft().player || !entity.getEntityBoundingBox().grow(0.35, 0.35, 0.35).contains(arrowPos)) continue;
                hit = true;
                break;
            }
            if (hit || TrajectoriesMod.isCollidable(block = Minecraft.getMinecraft().world.getBlockState(new BlockPos(arrowPos)).getBlock())) break;
        }
        return hit;
    }
}

