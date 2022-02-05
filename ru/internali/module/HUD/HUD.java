//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.text.TextFormatting
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package ru.internali.module.HUD;

import com.google.common.base.Strings;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
import ru.internali.utils.ColorUtils;
import ru.internali.utils.FriendManager;
import ru.internali.utils.RenderUtil;

public class HUD
extends Module {
    private Entity target;
    private List<Module> modulesSorted;
    private static RenderItem kappita;
    private static final RenderItem itemRender;
    private int x;
    private int y;
    private final FontRenderer fr;

    public HUD() {
        super("HUD", "modules on screen", Category.HUD);
        this.fr = Minecraft.getMinecraft().fontRenderer;
    }

    public void targethud(Entity target) {
        ScaledResolution sr = new ScaledResolution(mc);
        float posX = (float)CatClient.instance.settingsManager.getSettingByName(this, "TargetHudX").getValDouble();
        float posY = (float)CatClient.instance.settingsManager.getSettingByName(this, "TargetHudY").getValDouble();
        GL11.glPushMatrix();
        GL11.glTranslated((double)((float)(sr.getScaledWidth() / 2 + 10) + posX), (double)((float)(sr.getScaledHeight() / 2) + posY), (double)(sr.getScaledWidth() / 2 + 10));
        RenderUtil.drawSmoothRect(0.0f, 0.0f, 145.0f, 45.0f, new Color(35, 35, 40, 230).getRGB());
        if (FriendManager.isFriend(target.getName())) {
            HUD.mc.fontRenderer.drawStringWithShadow(target.getName(), 28.0f, 4.0f, Color.GREEN.getRGB());
        } else {
            HUD.mc.fontRenderer.drawStringWithShadow(target.getName(), 28.0f, 4.0f, Color.white.getRGB());
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
            HUD.mc.fontRenderer.drawStringWithShadow(healph + "" + (Object)TextFormatting.RED + " -" + ((EntityPlayer)target).hurtTime, 28.0f, (float)(4 + HUD.mc.fontRenderer.FONT_HEIGHT), Color.white.getRGB());
        } else {
            HUD.mc.fontRenderer.drawStringWithShadow(healph + "", 28.0f, (float)(4 + HUD.mc.fontRenderer.FONT_HEIGHT), Color.white.getRGB());
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
    }

    public static int[] getRainbow(int cycle, float offset) {
        int r = 0;
        int g = 0;
        int b = 0;
        long timeInCycle = (System.currentTimeMillis() - (long)Math.round(offset * 1000.0f)) % (long)(cycle * 1000);
        float portionOfCycle = 6.0f * (float)timeInCycle / (float)(cycle * 1000);
        float timeInPortion = portionOfCycle - (float)Math.floor(portionOfCycle);
        int timeInPortionRGB = Math.round(255.0f * timeInPortion);
        if (portionOfCycle < 1.0f) {
            r = 255;
            g = timeInPortionRGB;
        } else if (portionOfCycle < 2.0f) {
            r = 255 - timeInPortionRGB;
            g = 255;
        } else if (portionOfCycle < 3.0f) {
            g = 255;
            b = timeInPortionRGB;
        } else if (portionOfCycle < 4.0f) {
            g = 255 - timeInPortionRGB;
            b = 255;
        } else if (portionOfCycle < 5.0f) {
            r = timeInPortionRGB;
            b = 255;
        } else if (portionOfCycle < 6.0f) {
            r = 255;
            b = 255 - timeInPortionRGB;
        }
        return new int[]{r, g, b};
    }

    public static int RGBtoHex(int red, int green, int blue) {
        return red << 16 | green << 8 | blue;
    }

    public void drawitem(ItemStack item, int x, int y) {
        GlStateManager.enableDepth();
        HUD.itemRender.zLevel = 200.0f;
        itemRender.renderItemAndEffectIntoGUI(item, x, y);
        itemRender.renderItemOverlayIntoGUI(HUD.mc.fontRenderer, item, x, y, "");
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        if (item.getCount() == 0 || item.getCount() == 1) {
            this.fr.drawString("", x + 13, y + 10, Color.white.getRGB());
        } else {
            this.fr.drawString("" + item.getCount(), x + 13, y + 10, Color.white.getRGB());
        }
    }

    public static void drawRectangleCorrectly(int x, int y, int w, int h, int color) {
        GL11.glLineWidth((float)1.0f);
        Gui.drawRect((int)x, (int)y, (int)(x + w), (int)(y + h), (int)color);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        int i;
        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT || Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            return;
        }
        int[] rainbow = HUD.getRainbow(5, 0.1f);
        int hexColor = HUD.RGBtoHex(rainbow[0], rainbow[1], rainbow[2]);
        int y = 2;
        String name = HUD.mc.player.getName();
        String time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        String text = "Bobr | " + name + " | " + time;
        Gui.drawRect((int)5, (int)5, (int)(this.fr.getStringWidth(text) + 9), (int)21, (int)new Color(37, 37, 37, 255).getRGB());
        Gui.drawRect((int)6, (int)6, (int)(this.fr.getStringWidth(text) + 8), (int)8, (int)CatClient.getClientColor().getRGB());
        Gui.drawRect((int)5, (int)9, (int)(this.fr.getStringWidth(text) + 9), (int)9, (int)new Color(20, 20, 20, 100).getRGB());
        this.fr.drawStringWithShadow(text, 7.0f, 13.0f, -1);
        this.sortList();
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Module mod : this.modulesSorted) {
            if (mod.getName().equalsIgnoreCase("HUD") || !mod.isToggled() || !mod.visible) continue;
            arrayList.add(mod.getName());
        }
        int b = HUD.mc.fontRenderer.FONT_HEIGHT;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        long l = 0L;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int[] counter = new int[]{1};
        int yTotal = 0;
        for (i = 0; i < arrayList.size(); ++i) {
            yTotal += HUD.mc.fontRenderer.FONT_HEIGHT + 5;
        }
        for (i = 0; i < arrayList.size(); ++i) {
            String string5 = (String)arrayList.get(i);
            if (Strings.isNullOrEmpty((String)string5)) continue;
            int n8 = HUD.mc.fontRenderer.FONT_HEIGHT;
            int n9 = HUD.mc.fontRenderer.getStringWidth(string5);
            int n10 = scaledResolution.getScaledWidth() - 1 - n9;
            int n11 = 1 + (n8 + 2) * i;
            int n12 = ColorUtils.astolfoColors(counter[0] * 15, yTotal);
            CatClient.color = new Color(n12);
            if (i == 0) {
                // empty if block
            }
            RenderUtil.drawGlow(n10 - 2, n11 - 6, n10 + HUD.mc.fontRenderer.getStringWidth(string5), n11 + 11 + 6, n12, 100.0);
            RenderUtil.drawRect(n10 - 2, n11, n10 + HUD.mc.fontRenderer.getStringWidth(string5), n11 + 11, 0x44000000);
            n6 = HUD.mc.fontRenderer.getStringWidth(string5);
            n5 = n10 - 2;
            n7 = n11 + 10;
            if (i == this.modulesSorted.size() - 1) {
                RenderUtil.drawGlow(n10 - 2, n11 + 10, n10 + HUD.mc.fontRenderer.getStringWidth(string5), n11 + 11, n12, 230.0);
            }
            HUD.mc.fontRenderer.drawString(string5, n10, n11 + 1, n12);
            ++l;
            ++n4;
            counter[0] = counter[0] + 1;
        }
        this.modulesSorted.clear();
    }

    public static int raindbow(int deley) {
        double raindbowState = Math.ceil((System.currentTimeMillis() + (long)deley) / 20L);
        return Color.getHSBColor((float)((raindbowState %= 360.0) / 360.0), 0.5f, 1.0f).getRGB();
    }

    private int rainbow(long l, float f) {
        float f2 = (float)(System.nanoTime() + l) / 2.0E10f % 1.0f;
        long l2 = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(f2 + 100000.0f, 1.0f, 1.0f)), 16);
        Color color = new Color((int)l2);
        return new Color((float)color.getRed() / 255.0f * f, (float)color.getGreen() / 255.0f * f, (float)color.getBlue() / 255.0f * f, (float)color.getAlpha() / 255.0f).getRGB();
    }

    public void sortList() {
        this.modulesSorted = new ArrayList<Module>(CatClient.instance.moduleManager.getModuleList());
        this.modulesSorted.sort((module1, module2) -> {
            if (HUD.mc.fontRenderer.getStringWidth(module1.getName()) < HUD.mc.fontRenderer.getStringWidth(module2.getName())) {
                return 1;
            }
            if (HUD.mc.fontRenderer.getStringWidth(module1.getName()) > HUD.mc.fontRenderer.getStringWidth(module2.getName())) {
                return -1;
            }
            return 0;
        });
    }

    static {
        itemRender = Minecraft.getMinecraft().getRenderItem();
    }
}

