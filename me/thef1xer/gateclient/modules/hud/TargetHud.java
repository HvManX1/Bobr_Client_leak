//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.text.TextFormatting
 *  org.lwjgl.opengl.GL11
 */
package me.thef1xer.gateclient.modules.hud;

import java.awt.Color;
import java.util.Objects;
import me.thef1xer.gateclient.managers.FriendManager;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.modules.combat.AimPistot;
import me.thef1xer.gateclient.modules.combat.KillAura;
import me.thef1xer.gateclient.modules.combat.TriggerBot;
import me.thef1xer.gateclient.modules.combat.aimAssist;
import me.thef1xer.gateclient.settings.Setting;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import me.thef1xer.gateclient.util.ColorUtil;
import me.thef1xer.gateclient.wint.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public class TargetHud
extends Module {
    public static final TargetHud INSTANCE = new TargetHud();
    public Minecraft mc = Minecraft.getMinecraft();
    private Entity target;
    private String hef;
    private Entity focusTarget;
    private boolean checks = false;
    private boolean old = false;
    private String enter;
    private int x;
    private int y;
    private static RenderItem kappita;
    private static final RenderItem itemRender;
    private final FontRenderer fr;
    private final ResourceLocation logo;
    public final BooleanSetting targetPassive;
    public final FloatSetting pos;
    public final FloatSetting reach;

    public TargetHud() {
        super("TargetHud", "targethud", Module.ModuleCategory.HUD);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.logo = new ResourceLocation("bobr", "textures/bobr.png");
        this.targetPassive = new BooleanSetting("TargetHud", "targethud", true);
        this.pos = new FloatSetting("Pos", "pos", 50.0f, 0.0f, 250.0f);
        this.reach = new FloatSetting("Range", "range", 6.0f, 0.0f, 100.0f);
        this.setEnabled(false);
        this.addSettings(new Setting[0]);
    }

    public boolean isTarget(Entity entity) {
        return entity != Minecraft.getMinecraft().player && entity != Minecraft.getMinecraft().getRenderViewEntity() && Minecraft.getMinecraft().player.getDistanceSq(entity) <= Math.pow(this.reach.getValue(), 2.0) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }

    public void drawitem(ItemStack item, int x, int y) {
        GlStateManager.enableDepth();
        TargetHud.itemRender.zLevel = 200.0f;
        itemRender.renderItemAndEffectIntoGUI(item, x, y);
        itemRender.renderItemOverlayIntoGUI(this.mc.fontRenderer, item, x, y, "");
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        this.fr.drawString("" + item.getCount(), x + 13, y + 10, Color.white.getRGB());
    }

    public void drawtartet() {
        block24: {
            if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
                return;
            }
            try {
                GlStateManager.enableTexture2D();
                Entity target = Minecraft.getMinecraft().objectMouseOver.entityHit;
                if (AimPistot.INSTANCE.isEnabled()) {
                    target = AimPistot.INSTANCE.target;
                } else if (aimAssist.INSTANCE.isEnabled()) {
                    target = aimAssist.INSTANCE.target;
                } else if (TriggerBot.INSTANCE.isEnabled()) {
                    target = TriggerBot.INSTANCE.entity;
                } else if (KillAura.INSTANCE.isEnabled()) {
                    target = KillAura.INSTANCE.target;
                }
                if (target == null || !(target instanceof EntityPlayer)) break block24;
                int width = 100;
                ScaledResolution sr = new ScaledResolution(this.mc);
                int[] rainbow = ColorUtil.getRainbow(5, 1.0f);
                int hexColor = ColorUtil.RGBtoHex(rainbow[0], rainbow[1], rainbow[2]);
                GL11.glPushMatrix();
                GL11.glTranslated((double)(width / 100), (double)(sr.getScaledHeight() / 100), (double)0.0);
                GL11.glTranslated((double)(sr.getScaledWidth() / 2 + 10), (double)(sr.getScaledHeight() / 2), (double)(sr.getScaledWidth() / 2 + 10));
                RenderUtil.drawSmoothRect(0.0f, 0.0f, 110.0f, 45.0f, new Color(35, 35, 40, 230).getRGB());
                if (FriendManager.isFriend(target.getName())) {
                    this.fr.drawString(target.getName(), 34, 2, Color.GREEN.getRGB());
                } else {
                    this.fr.drawString(target.getName(), 34, 2, 0xFFFFFF);
                }
                float one_HP = 110.0f / ((EntityPlayer)target).getMaxHealth();
                this.x = 0;
                this.y = 46;
                ItemStack item = ((EntityPlayer)target).getHeldItemMainhand();
                this.drawitem(item, this.x, this.y);
                item = ((EntityPlayer)target).getHeldItemOffhand();
                this.drawitem(item, this.x + 18, this.y);
                int i = 0;
                for (ItemStack is : target.getArmorInventoryList()) {
                    if (is.isEmpty()) continue;
                    if (i == 3) {
                        this.drawitem(is, this.x + 36, this.y);
                    }
                    if (i == 2) {
                        this.drawitem(is, this.x + 54, this.y);
                    }
                    if (i == 1) {
                        this.drawitem(is, this.x + 72, this.y);
                    }
                    if (i == 0) {
                        this.drawitem(is, this.x + 90, this.y);
                    }
                    ++i;
                }
                try {
                    this.mc.getTextureManager().bindTexture(Objects.requireNonNull(this.mc.getConnection()).getPlayerInfo(target.getUniqueID()).getLocationSkin());
                    Gui.drawScaledCustomSizeModalRect((int)0, (int)0, (float)8.0f, (float)8.0f, (int)8, (int)8, (int)32, (int)32, (float)64.0f, (float)64.0f);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                this.fr.drawString((Object)TextFormatting.BLUE + "Dist: " + (Object)TextFormatting.RED + (int)Minecraft.getMinecraft().player.getDistance(target), 34, 14, 0xFFFFFF);
                if (((EntityPlayer)target).hurtTime > 0) {
                    RenderUtil.drawRect(0.0, 32.0, one_HP * ((EntityPlayer)target).getHealth(), 45.0, Color.RED.getRGB());
                } else {
                    RenderUtil.drawRect(0.0, 32.0, one_HP * ((EntityPlayer)target).getHealth(), 45.0, Color.GREEN.getRGB());
                }
                if (AimPistot.INSTANCE.isEnabled()) {
                    if (AimPistot.INSTANCE.can_attack) {
                        RenderUtil.drawSmoothRect(95.0f, 0.0f, 110.0f, 15.0f, Color.red.getRGB());
                    } else {
                        RenderUtil.drawSmoothRect(95.0f, 0.0f, 110.0f, 15.0f, Color.GREEN.getRGB());
                    }
                }
                this.fr.drawString((int)((EntityPlayer)target).getHealth() + " - " + ((EntityPlayer)target).getMaxHealth(), 5, 34, hexColor);
                GlStateManager.enableDepth();
                GlStateManager.disableLighting();
                GL11.glPopMatrix();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static {
        itemRender = Minecraft.getMinecraft().getRenderItem();
    }
}

