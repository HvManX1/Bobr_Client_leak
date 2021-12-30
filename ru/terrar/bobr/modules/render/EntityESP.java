//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.vecmath.Vector3d
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GLAllocation
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemAir
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.GLU
 */
package ru.terrar.bobr.modules.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.vecmath.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import ru.terrar.bobr.managers.FriendManager;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;

public class EntityESP
extends Module {
    public static final EntityESP INSTANCE = new EntityESP();
    private final IntBuffer viewport = GLAllocation.createDirectIntBuffer((int)16);
    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer((int)16);
    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer((int)16);
    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer((int)4);
    private final int color;
    private final int backgroundColor;
    private final int black;
    private ScaledResolution resolution;
    private float partialticks;
    public final BooleanSetting hunger = new BooleanSetting("Hunger", "Hunger", false);
    public final BooleanSetting health = new BooleanSetting("Health", "Health", true);
    public final BooleanSetting healthValue = new BooleanSetting("healthValue", "healthValue", true);
    public final BooleanSetting box = new BooleanSetting("Box", "Box", true);
    public final BooleanSetting tag = new BooleanSetting("Tag", "Tag", true);
    public final BooleanSetting currentItem = new BooleanSetting("Show current item", "Showcurrentitem", false);
    public final BooleanSetting local = new BooleanSetting("Local", "Local", false);
    private Minecraft mc = Minecraft.getMinecraft();

    public EntityESP() {
        super("ESP", "esp", Module.ModuleCategory.RENDER);
        this.resolution = this.resolution;
        this.partialticks = this.partialticks;
        this.addSettings(this.hunger, this.health, this.healthValue, this.box, this.tag, this.currentItem, this.local);
        this.color = 0;
        this.backgroundColor = 0;
        this.black = 0;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    public ScaledResolution getResolution() {
        return this.resolution;
    }

    public float getPartialTicks() {
        return this.partialticks;
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        for (Entity e : this.mc.world.loadedEntityList) {
            if (!(e instanceof EntityPlayer) || e == this.mc.player && (!this.local.getValue() || this.mc.gameSettings.thirdPersonView != 1)) continue;
            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)event.getPartialTicks() - this.mc.getRenderManager().viewerPosX;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)event.getPartialTicks() - this.mc.getRenderManager().viewerPosY;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)event.getPartialTicks() - this.mc.getRenderManager().viewerPosZ;
            GL11.glPushMatrix();
            GL11.glLineWidth((float)1.3f);
            GL11.glTranslated((double)x, (double)y, (double)z);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glRotated((double)(-this.mc.getRenderManager().playerViewY), (double)0.0, (double)1.0, (double)0.0);
            if (this.box.getValue()) {
                if (FriendManager.isFriend(e.getName())) {
                    GL11.glColor4d((double)30.0, (double)225.0, (double)47.0, (double)255.0);
                } else {
                    GL11.glColor4d((double)0.8, (double)0.8, (double)0.8, (double)255.0);
                }
                GL11.glBegin((int)3);
                GL11.glVertex3d((double)0.55, (double)-0.2, (double)0.0);
                GL11.glVertex3d((double)0.55, (double)((double)e.height + 0.2), (double)0.0);
                GL11.glVertex3d((double)((double)e.width - 1.15), (double)((double)e.height + 0.2), (double)0.0);
                GL11.glVertex3d((double)((double)e.width - 1.15), (double)-0.2, (double)0.0);
                GL11.glVertex3d((double)0.55, (double)-0.2, (double)0.0);
                GL11.glEnd();
            }
            if (this.health.getValue()) {
                Color health = Color.GREEN.darker();
                if (((EntityPlayer)e).getHealth() >= 16.0f) {
                    health = Color.GREEN.darker();
                } else if (((EntityPlayer)e).getHealth() >= 8.0f && ((EntityPlayer)e).getHealth() <= 16.0f) {
                    health = Color.YELLOW;
                } else if (((EntityPlayer)e).getHealth() > 0.0f && ((EntityPlayer)e).getHealth() <= 8.0f) {
                    health = Color.RED;
                }
                GL11.glLineWidth((float)4.0f);
                GL11.glBegin((int)3);
                GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
                GL11.glVertex3d((double)0.6, (double)-0.2, (double)0.0);
                GL11.glVertex3d((double)0.6, (double)((double)e.height + 0.2), (double)0.0);
                GL11.glEnd();
                GL11.glBegin((int)3);
                GL11.glColor4d((double)((float)health.getRed() / 255.0f), (double)((float)health.getGreen() / 255.0f), (double)((float)health.getBlue() / 255.0f), (double)((float)health.getAlpha() / 255.0f));
                GL11.glVertex3d((double)0.6, (double)-0.2, (double)0.0);
                GL11.glVertex3d((double)0.6, (double)((double)(((EntityLivingBase)e).getHealth() / ((EntityLivingBase)e).getMaxHealth()) * ((double)e.height + 0.2)), (double)0.0);
                GL11.glVertex3d((double)0.6, (double)-0.2, (double)0.0);
                GL11.glLineWidth((float)1.0f);
                GL11.glEnd();
            }
            if (this.hunger.getValue()) {
                GL11.glBegin((int)3);
                GL11.glVertex3d((double)((double)e.width - 1.2), (double)((double)e.height + 0.2), (double)0.0);
                GL11.glVertex3d((double)((double)e.width - 1.2), (double)-0.2, (double)0.0);
                GL11.glColor4d((double)Color.ORANGE.getRed(), (double)Color.ORANGE.getGreen(), (double)Color.ORANGE.getBlue(), (double)255.0);
                GL11.glVertex3d((double)((double)e.width - 1.2), (double)((double)e.height + 0.2), (double)0.0);
                GL11.glVertex3d((double)((double)e.width - 1.2), (double)-0.2, (double)0.0);
                GL11.glColor4d((double)255.0, (double)255.0, (double)255.0, (double)255.0);
                GL11.glEnd();
            }
            float size = 0.013f;
            GL11.glScaled((double)-0.013f, (double)-0.013f, (double)-0.013f);
            if (this.tag.getValue()) {
                GL11.glEnable((int)3553);
                this.mc.fontRenderer.drawStringWithShadow(e.getName(), (float)(1 - this.mc.fontRenderer.getStringWidth(e.getName()) / 2), -170.0f, -1);
                GL11.glDisable((int)3553);
            }
            if (this.healthValue.getValue() && this.health.getValue()) {
                GL11.glEnable((int)3553);
                this.mc.fontRenderer.drawStringWithShadow(String.valueOf((int)(((EntityPlayer)e).getHealth() / ((EntityPlayer)e).getMaxHealth() * 100.0f)), (float)(-50 - this.mc.fontRenderer.getStringWidth(String.valueOf((int)(((EntityPlayer)e).getHealth() / ((EntityPlayer)e).getMaxHealth() * 100.0f)))), (float)((int)((double)(((EntityLivingBase)e).getHealth() / ((EntityLivingBase)e).getMaxHealth()) * ((double)e.height + 0.2))), -1);
                GL11.glDisable((int)3553);
            }
            if (this.currentItem.getValue() && !(((EntityPlayer)e).getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) && !(((EntityPlayer)e).getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemAir)) {
                GL11.glEnable((int)3553);
                this.mc.fontRenderer.drawStringWithShadow(((EntityPlayer)e).getHeldItem(EnumHand.MAIN_HAND).getDisplayName(), (float)(1 - this.mc.fontRenderer.getStringWidth(((EntityPlayer)e).getHeldItem(EnumHand.MAIN_HAND).getDisplayName()) / 2), 20.0f, -1);
                GL11.glDisable((int)3553);
            }
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glPopMatrix();
        }
    }

    private Vector3d project2D(int scaleFactor, double x, double y, double z) {
        GL11.glGetFloat((int)2982, (FloatBuffer)this.modelview);
        GL11.glGetFloat((int)2983, (FloatBuffer)this.projection);
        GL11.glGetInteger((int)2978, (IntBuffer)this.viewport);
        return GLU.gluProject((float)((float)x), (float)((float)y), (float)((float)z), (FloatBuffer)this.modelview, (FloatBuffer)this.projection, (IntBuffer)this.viewport, (FloatBuffer)this.vector) ? new Vector3d((double)(this.vector.get(0) / (float)scaleFactor), (double)(((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor), (double)this.vector.get(2)) : null;
    }

    public static enum Modes {
        threeD("3D"),
        twoD("2D");

        private final String name;

        private Modes(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

