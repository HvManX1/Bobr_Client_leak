//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package me.thef1xer.gateclient.modules.combat;

import me.thef1xer.gateclient.managers.FriendManager;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.modules.combat.AntiBot;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HitBoxMod
extends Module {
    private transient long time;
    public static final HitBoxMod INSTANCE = new HitBoxMod();
    public final FloatSetting horizontal = new FloatSetting("Horizontal", "horizontal", 0.5f, 0.0f, 3.0f);
    public final FloatSetting vertical = new FloatSetting("Vertical", "vertical", 0.0f, 0.0f, 3.0f);
    private Entity target;
    private Entity focusTarget;

    public HitBoxMod() {
        super("HitBox", "hitbox", Module.ModuleCategory.COMBAT);
        this.addSettings(this.horizontal, this.vertical);
        this.setEnabled(false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.time = System.currentTimeMillis();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    public HitBox createHitBox(Entity entity) {
        return new HitBox(0.6f, 1.8f);
    }

    public void changeEntityHitBox(Entity entity, float width, float height) {
        entity.width = width;
        entity.height = height;
        double d = (double)width / 2.0;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d, entity.posY, entity.posZ - d, entity.posX + d, entity.posY + (double)entity.height, d + entity.posZ));
    }

    @SubscribeEvent
    @SideOnly(value=Side.CLIENT)
    public void onTick(TickEvent.ClientTickEvent event) {
        this.target = null;
        if (Minecraft.getMinecraft().world == null || !Minecraft.getMinecraft().world.isRemote) {
            return;
        }
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
                if (entity == null || entity == Minecraft.getMinecraft().player || !(entity instanceof EntityPlayer) || AntiBot.isBot(entity.getName()) || FriendManager.isFriend(entity.getName())) continue;
                HitBox hitbox = this.createHitBox(entity);
                this.changeEntityHitBox(entity, hitbox.width + this.horizontal.getValue(), hitbox.height + this.vertical.getValue());
            }
        }
    }

    private static class HitBox {
        public float width;
        public float height;

        public HitBox(float width, float height) {
            this.width = width;
            this.height = height;
        }
    }
}

