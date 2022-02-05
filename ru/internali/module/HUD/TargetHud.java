//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package ru.internali.module.HUD;

import java.awt.Color;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.module.combat.AntiBot;
import ru.internali.settings.Setting;
import ru.internali.utils.FriendManager;
import ru.internali.utils.RenderUtil;

public class TargetHud
extends Module {
    private static RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
    private Entity target;

    public TargetHud() {
        super("TargetHud", "TargetHud", Category.HUD);
        CatClient.instance.settingsManager.rSetting(new Setting("PosX", this, 0.0, -200.0, 200.0, true));
        CatClient.instance.settingsManager.rSetting(new Setting("PosY", this, 0.0, -200.0, 200.0, true));
    }

    public void targethud(Entity target) {
        ScaledResolution sr = new ScaledResolution(mc);
        float posX = (float)CatClient.instance.settingsManager.getSettingByName(this, "PosX").getValDouble();
        float posY = (float)CatClient.instance.settingsManager.getSettingByName(this, "PosY").getValDouble();
        GL11.glPushMatrix();
        GL11.glTranslated((double)((float)(sr.getScaledWidth() / 2 + 10) + posX), (double)((float)(sr.getScaledHeight() / 2) + posY), (double)(sr.getScaledWidth() / 2 + 10));
        Gui.drawRect((int)0, (int)0, (int)145, (int)45, (int)new Color(35, 35, 40, 230).getRGB());
        if (FriendManager.isFriend(target.getName())) {
            TargetHud.mc.fontRenderer.drawStringWithShadow(target.getName(), 28.0f, 4.0f, Color.GREEN.getRGB());
        } else {
            TargetHud.mc.fontRenderer.drawStringWithShadow(target.getName(), 28.0f, 4.0f, Color.white.getRGB());
        }
        try {
            mc.getTextureManager().bindTexture(Objects.requireNonNull(mc.getConnection()).getPlayerInfo(target.getUniqueID()).getLocationSkin());
            Gui.drawScaledCustomSizeModalRect((int)2, (int)2, (float)8.0f, (float)8.0f, (int)8, (int)8, (int)22, (int)22, (float)64.0f, (float)64.0f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        int i = 8;
        RenderUtil.drawFilledCircle(i, 35, 10.0f, Color.LIGHT_GRAY);
        RenderUtil.drawFilledCircle(i, 35, 9.0f, new Color(35, 35, 40, 230));
        ItemStack item = ((EntityPlayer)target).getHeldItemMainhand();
        this.drawitem(item, i - 8, 27);
        RenderUtil.drawFilledCircle(i += 24, 35, 10.0f, Color.LIGHT_GRAY);
        RenderUtil.drawFilledCircle(i, 35, 9.0f, new Color(35, 35, 40, 230));
        item = ((EntityPlayer)target).getHeldItemOffhand();
        this.drawitem(item, i - 8, 27);
        i += 24;
        for (ItemStack is : target.getArmorInventoryList()) {
            RenderUtil.drawFilledCircle(i, 35, 10.0f, Color.LIGHT_GRAY);
            RenderUtil.drawFilledCircle(i, 35, 9.0f, new Color(35, 35, 40, 230));
            this.drawitem(is, i - 8, 27);
            i += 24;
        }
        RenderUtil.drawFilledCircle(130, 13, 11.0f, Color.LIGHT_GRAY);
        RenderUtil.drawFilledCircle(130, 13, ((EntityPlayer)target).getHealth() / 2.0f, Color.green);
        if (((EntityPlayer)target).hurtTime > 0) {
            RenderUtil.drawFilledCircle(130, 13, ((EntityPlayer)target).getHealth() / 2.0f, Color.red);
        }
        int[] counter = new int[]{1};
        counter[0] = counter[0] + 1;
        int healph = (int)((EntityPlayer)target).getHealth();
        if (((EntityPlayer)target).hurtTime > 0) {
            TargetHud.mc.fontRenderer.drawStringWithShadow(healph + "" + (Object)TextFormatting.RED + " -" + ((EntityPlayer)target).hurtTime, 28.0f, (float)(4 + TargetHud.mc.fontRenderer.FONT_HEIGHT), Color.white.getRGB());
        } else {
            TargetHud.mc.fontRenderer.drawStringWithShadow(healph + "", 28.0f, (float)(4 + TargetHud.mc.fontRenderer.FONT_HEIGHT), Color.white.getRGB());
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
    }

    public void drawitem(ItemStack item, int x, int y) {
        GlStateManager.enableDepth();
        TargetHud.itemRender.zLevel = 200.0f;
        itemRender.renderItemAndEffectIntoGUI(item, x, y);
        itemRender.renderItemOverlayIntoGUI(TargetHud.mc.fontRenderer, item, x, y, "");
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        if (item.getCount() == 0 || item.getCount() == 1) {
            TargetHud.mc.fontRenderer.drawString("", x + 13, y + 10, Color.white.getRGB());
        } else {
            TargetHud.mc.fontRenderer.drawString("" + item.getCount(), x + 13, y + 10, Color.white.getRGB());
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT || Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            return;
        }
        try {
            GlStateManager.enableTexture2D();
            for (Entity e : TargetHud.mc.world.loadedEntityList) {
                if (!(e instanceof EntityPlayer) || !(TargetHud.mc.player.getDistance(e) <= 250.0f) || e == TargetHud.mc.player || AntiBot.isBot(e.getName())) continue;
                this.target = e;
            }
            if (this.target == null) {
                try {
                    Entity entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (this.target != null && this.target instanceof EntityPlayer) {
                this.targethud(this.target);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

