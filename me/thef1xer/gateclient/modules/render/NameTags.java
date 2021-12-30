//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.ItemRenderer
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.IAnimals
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Enchantments
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package me.thef1xer.gateclient.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Map;
import java.util.Objects;
import me.thef1xer.gateclient.managers.FriendManager;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import me.thef1xer.gateclient.settings.impl.EnumSetting;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import me.thef1xer.gateclient.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class NameTags
extends Module {
    public static final NameTags INSTANCE = new NameTags();
    private Minecraft mc = Minecraft.getMinecraft();
    public final EnumSetting Healthmode = new EnumSetting("Healthmode", "Healthmode", Modes.values(), Modes.Bar);
    public final FloatSetting Scale = new FloatSetting("Scale", "Scale", 2.0f, 0.0f, 4.0f);
    public final BooleanSetting armor = new BooleanSetting("armor", "armor", true);
    public final BooleanSetting Player = new BooleanSetting("Player", "Player", true);
    public final BooleanSetting Mob = new BooleanSetting("Mob", "Mob", false);
    public final BooleanSetting Hostile = new BooleanSetting("Hostile", "Hostile", false);
    public final BooleanSetting Ping = new BooleanSetting("Ping", "Ping", true);
    public final BooleanSetting Gamemode = new BooleanSetting("Gamemode", "Gamemode", false);
    private static RenderItem kappita;
    private static final RenderItem itemRender;

    public NameTags() {
        super("NameTags", "NameTags", Module.ModuleCategory.RENDER);
        this.addSettings(this.Scale, this.armor, this.Player, this.Mob, this.Hostile, this.Ping, this.Gamemode, this.Healthmode);
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

    private void Processtext(String s, int stringWidth, Integer getcolor, Entity entity, double rofl, double scale) {
        try {
            NameTags.glSetup(entity.posX, entity.posY + rofl, entity.posZ);
            GlStateManager.scale((double)(-0.025 * scale), (double)(-0.025 * scale), (double)(0.025 * scale));
            GlStateManager.disableTexture2D();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos((double)(-stringWidth - 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            bufferbuilder.pos((double)(-stringWidth - 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            bufferbuilder.pos((double)(stringWidth + 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            bufferbuilder.pos((double)(stringWidth + 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            this.mc.fontRenderer.drawStringWithShadow(s, (float)(-stringWidth), 0.0f, getcolor.intValue());
            NameTags.glCleanup();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void drawItem(double x, double y, double z, double offX, double offY, double scale, ItemStack item) {
        NameTags.glSetup(x, y, z);
        GlStateManager.scale((double)(0.4 * scale), (double)(0.4 * scale), (double)0.0);
        GlStateManager.translate((double)offX, (double)offY, (double)0.0);
        ItemRenderer renderer = new ItemRenderer(Minecraft.getMinecraft());
        renderer.renderItemSide((EntityLivingBase)Minecraft.getMinecraft().player, item, ItemCameraTransforms.TransformType.NONE, false);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.scale((float)-0.05f, (float)-0.05f, (float)0.0f);
        try {
            if (item.getCount() > 0) {
                int w = Minecraft.getMinecraft().fontRenderer.getStringWidth("x" + item.getCount()) / 2;
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("x" + item.getCount(), (float)(7 - w), 5.0f, 0xFFFFFF);
            }
            GlStateManager.scale((float)0.85f, (float)0.85f, (float)0.85f);
            int c = 0;
            for (Map.Entry m : EnchantmentHelper.getEnchantments((ItemStack)item).entrySet()) {
                int w1 = Minecraft.getMinecraft().fontRenderer.getStringWidth(I18n.format((String)((Enchantment)m.getKey()).getName().substring(0, 2), (Object[])new Object[0]) + (Integer)m.getValue() / 2);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(I18n.format((String)((Enchantment)m.getKey()).getName(), (Object[])new Object[0]).substring(0, 2) + m.getValue(), (float)(-4 - w1 + 3), (float)(c * 10 - 1), m.getKey() == Enchantments.VANISHING_CURSE || m.getKey() == Enchantments.BINDING_CURSE ? 0xFF5050 : 16756960);
                --c;
            }
            GlStateManager.scale((float)0.6f, (float)0.6f, (float)0.6f);
            String dur = item.getMaxDamage() - item.getItemDamage() + "";
            int color = MathHelper.hsvToRGB((float)((float)(item.getMaxDamage() - item.getItemDamage()) / (float)item.getMaxDamage() / 3.0f), (float)1.0f, (float)1.0f);
            if (item.isItemStackDamageable()) {
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(dur, (float)(-8 - dur.length() * 3), 15.0f, new Color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF).getRGB());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        NameTags.glCleanup();
    }

    public static void glSetup(double x, double y, double z) {
        GlStateManager.pushMatrix();
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        GlStateManager.translate((double)(x - Minecraft.getMinecraft().getRenderManager().viewerPosX), (double)(y - Minecraft.getMinecraft().getRenderManager().viewerPosY), (double)(z - Minecraft.getMinecraft().getRenderManager().viewerPosZ));
        GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -renderManager.playerViewX : renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.disableLighting();
        GL11.glDisable((int)2929);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
    }

    public static void glCleanup() {
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GlStateManager.enableDepth();
        GL11.glTranslatef((float)-0.5f, (float)0.0f, (float)0.0f);
        GlStateManager.popMatrix();
    }

    private void Runnametag(EntityLivingBase e) {
        double scale = Math.max(this.Scale.getValue() * (this.mc.player.getDistance((Entity)e) / 20.0f), 2.0f);
        StringBuilder healthBuilder = new StringBuilder();
        int i = 0;
        while ((float)i < e.getHealth()) {
            healthBuilder.append((Object)ChatFormatting.GREEN + "|");
            ++i;
        }
        StringBuilder health = new StringBuilder(healthBuilder.toString());
        int i2 = 0;
        while ((float)i2 < MathHelper.clamp((float)e.getAbsorptionAmount(), (float)0.0f, (float)(e.getMaxHealth() - e.getHealth()))) {
            health.append((Object)ChatFormatting.RED + "|");
            ++i2;
        }
        i2 = 0;
        while ((float)i2 < e.getMaxHealth() - (e.getHealth() + e.getAbsorptionAmount())) {
            health.append((Object)ChatFormatting.YELLOW + "|");
            ++i2;
        }
        if (e.getAbsorptionAmount() - (e.getMaxHealth() - e.getHealth()) > 0.0f) {
            health.append((Object)ChatFormatting.BLUE + " +").append((int)(e.getAbsorptionAmount() - (e.getMaxHealth() - e.getHealth())));
        }
        String tag = "";
        if (this.Ping.getValue() && e instanceof EntityPlayer) {
            try {
                tag = tag + " " + (int)MathHelper.clamp((float)Objects.requireNonNull(this.mc.getConnection()).getPlayerInfo(e.getUniqueID()).getResponseTime(), (float)1.0f, (float)300.0f) + " P ";
            }
            catch (NullPointerException nullPointerException) {
                // empty catch block
            }
        }
        if (this.Gamemode.getValue() && e instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer)e;
            if (entityPlayer.isCreative()) {
                tag = tag + "[C]";
            }
            if (entityPlayer.isSpectator()) {
                tag = tag + " [I]";
            }
            if (!entityPlayer.isAllowEdit() && !entityPlayer.isSpectator()) {
                tag = tag + " [A]";
            }
            if (!entityPlayer.isCreative() && !entityPlayer.isSpectator() && entityPlayer.isAllowEdit()) {
                tag = tag + " [S]";
            }
        }
        int[] rainbow = ColorUtil.getRainbow(5, 1.0f);
        int hexColor = ColorUtil.RGBtoHex(rainbow[0], rainbow[1], rainbow[2]);
        if (FriendManager.isFriend(e.getName())) {
            if (this.Healthmode.getCurrentValue() == Modes.Number) {
                this.Processtext(e.getName() + " [" + (int)(e.getHealth() + e.getAbsorptionAmount()) + "/" + (int)e.getMaxHealth() + "]" + tag, this.mc.fontRenderer.getStringWidth(e.getName() + tag + "[]") / 2, hexColor, (Entity)e, (double)e.height + 0.5 * scale, scale);
            } else {
                this.Processtext(e.getName() + tag, this.mc.fontRenderer.getStringWidth(e.getName() + tag) / 2, hexColor, (Entity)e, (double)e.height + 0.5 * scale, scale);
                this.Processtext(health.toString(), this.mc.fontRenderer.getStringWidth(health.toString()) / 2, hexColor, (Entity)e, (double)e.height + 0.75 * scale, scale);
            }
        } else if (this.Healthmode.getCurrentValue() == Modes.Number) {
            this.Processtext(e.getName() + " [" + (int)(e.getHealth() + e.getAbsorptionAmount()) + "/" + (int)e.getMaxHealth() + "]" + tag, this.mc.fontRenderer.getStringWidth(e.getName() + tag + "[]") / 2, Color.white.getRGB(), (Entity)e, (double)e.height + 0.5 * scale, scale);
        } else {
            this.Processtext(e.getName() + tag, this.mc.fontRenderer.getStringWidth(e.getName() + tag) / 2, hexColor, (Entity)e, (double)e.height + 0.5 * scale, scale);
            this.Processtext(health.toString(), this.mc.fontRenderer.getStringWidth(health.toString()) / 2, Color.white.getRGB(), (Entity)e, (double)e.height + 0.75 * scale, scale);
        }
        if (this.armor.getValue()) {
            double c = 0.0;
            double higher = this.Healthmode.getCurrentValue() == Modes.Number ? 0.0 : 0.25;
            NameTags.drawItem(e.posX, e.posY + (double)e.height + (0.75 + higher) * scale, e.posZ, -2.5, 0.0, scale, e.getHeldItemMainhand());
            NameTags.drawItem(e.posX, e.posY + (double)e.height + (0.75 + higher) * scale, e.posZ, 2.5, 0.0, scale, e.getHeldItemOffhand());
            for (ItemStack i3 : e.getArmorInventoryList()) {
                NameTags.drawItem(e.posX, e.posY + (double)e.height + (0.75 + higher) * scale, e.posZ, c + 1.5, 0.0, scale, i3);
                c -= 1.0;
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        for (Entity object : this.mc.world.loadedEntityList) {
            if (!(this.Player.getValue() && object instanceof EntityPlayer || this.Mob.getValue() && object instanceof IAnimals) && (!this.Hostile.getValue() || !(object instanceof IMob))) continue;
            assert (object instanceof EntityLivingBase);
            if (object == this.mc.player) continue;
            this.Runnametag((EntityLivingBase)object);
        }
    }

    static {
        itemRender = Minecraft.getMinecraft().getRenderItem();
    }

    public static enum Modes {
        Number("Number"),
        Bar("Bar");

        private final String name;

        private Modes(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

