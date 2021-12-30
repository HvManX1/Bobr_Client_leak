//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.EntityRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.opengl.GL11
 */
package ru.terrar.bobr.modules.render;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import ru.terrar.bobr.managers.FriendManager;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;

public class ItemESP
extends Module {
    public static final ItemESP INSTANCE = new ItemESP();
    public Minecraft mc = Minecraft.getMinecraft();
    private Entity target;
    private String hef;
    private Entity focusTarget;
    private boolean checks = false;
    private boolean old = false;
    private String enter;
    private int x;
    private int y;
    private transient List<Entity> ENTITIES = new ArrayList<Entity>();
    private transient int BOX = 0;
    public final BooleanSetting ShowItemNames = new BooleanSetting("ShowItemNames", "ShowItemNames", true);
    private final FontRenderer fr;

    public ItemESP() {
        super("ItemESP", "ItemESP", Module.ModuleCategory.RENDER);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.addSettings(this.ShowItemNames);
    }

    public static void drawOutlinedBox(AxisAlignedBB bb) {
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glEnd();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.BOX = GL11.glGenLists((int)1);
        GL11.glNewList((int)this.BOX, (int)4864);
        ItemESP.drawOutlinedBox(new AxisAlignedBB(-0.5, 0.0, -0.5, 0.5, 1.0, 0.5));
        GL11.glEndList();
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
        this.ENTITIES.clear();
        for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
            if (!(entity instanceof EntityItem)) continue;
            this.ENTITIES.add(entity);
        }
    }

    public static void drawESPBoxes(List<Entity> entities, int box, float partialTicks) {
        GL11.glLineWidth((float)2.0f);
        for (Entity entity : entities) {
            GL11.glPushMatrix();
            Vec3d interpolated = ItemESP.getInterpolatedPos(entity, partialTicks);
            GL11.glTranslated((double)interpolated.x, (double)interpolated.y, (double)interpolated.z);
            GL11.glScaled((double)((double)entity.width + 0.1), (double)((double)entity.height + 0.1), (double)((double)entity.width + 0.1));
            if (entity instanceof EntityPlayer && FriendManager.isFriend(entity.getName())) {
                GL11.glColor4f((float)0.9f, (float)0.2f, (float)1.0f, (float)0.5f);
            } else if (entity instanceof EntityItem) {
                GL11.glColor4f((float)0.5f, (float)0.5f, (float)1.0f, (float)0.5f);
            } else {
                float intensity = Minecraft.getMinecraft().player.getDistance(entity) / 20.0f;
                GL11.glColor4f((float)(2.0f - intensity), (float)intensity, (float)0.0f, (float)0.5f);
            }
            GL11.glCallList((int)box);
            GL11.glPopMatrix();
        }
    }

    public static void drawESPTracers(List<Entity> entities) {
        Vec3d start = new Vec3d(Minecraft.getMinecraft().getRenderManager().viewerPosX, Minecraft.getMinecraft().getRenderManager().viewerPosY + (double)Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().getRenderManager().viewerPosZ).add(Minecraft.getMinecraft().player.getLookVec());
        GL11.glLineWidth((float)2.0f);
        GL11.glBegin((int)1);
        for (Entity entity : entities) {
            Vec3d target = entity.getEntityBoundingBox().getCenter();
            if (entity instanceof EntityPlayer && FriendManager.isFriend(entity.getName())) {
                GL11.glColor4f((float)0.9f, (float)0.2f, (float)1.0f, (float)0.5f);
            } else if (entity instanceof EntityItem) {
                GL11.glColor4f((float)0.5f, (float)0.5f, (float)1.0f, (float)0.5f);
            } else {
                float intensity = Minecraft.getMinecraft().player.getDistance(entity) / 20.0f;
                GL11.glColor4f((float)(2.0f - intensity), (float)intensity, (float)0.0f, (float)0.5f);
            }
            GL11.glVertex3d((double)start.x, (double)start.y, (double)start.z);
            GL11.glVertex3d((double)target.x, (double)target.y, (double)target.z);
        }
        GL11.glEnd();
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        GL11.glPushAttrib((int)24581);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glPushMatrix();
        GL11.glTranslated((double)(-Minecraft.getMinecraft().getRenderManager().viewerPosX), (double)(-Minecraft.getMinecraft().getRenderManager().viewerPosY), (double)(-Minecraft.getMinecraft().getRenderManager().viewerPosZ));
        ItemESP.drawESPBoxes(this.ENTITIES, this.BOX, 1.0f);
        GL11.glPopAttrib();
        if (this.ShowItemNames.getValue()) {
            this.drawStackNames(1.0f);
        }
        GL11.glPopMatrix();
    }

    public static Vec3d getInterpolatedPos(Entity entity, float partialTicks) {
        Vec3d from = new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);
        return entity.getPositionVector().subtract(from).scale((double)partialTicks).add(from);
    }

    private void drawStackNames(float partialTicks) {
        for (Entity entity : this.ENTITIES) {
            GL11.glPushMatrix();
            Vec3d interpolated = ItemESP.getInterpolatedPos(entity, partialTicks);
            GL11.glTranslated((double)interpolated.x, (double)interpolated.y, (double)interpolated.z);
            ItemStack stack = ((EntityItem)entity).getItem();
            EntityRenderer.drawNameplate((FontRenderer)Minecraft.getMinecraft().fontRenderer, (String)(stack.getCount() + "x " + stack.getDisplayName()), (float)0.0f, (float)1.0f, (float)0.0f, (int)0, (float)Minecraft.getMinecraft().getRenderManager().playerViewY, (float)Minecraft.getMinecraft().getRenderManager().playerViewX, (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? 1 : 0) != 0, (boolean)false);
            GL11.glPopMatrix();
        }
    }
}

