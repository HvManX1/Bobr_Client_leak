//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.passive.EntityVillager
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package ru.internali.module.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;
import ru.internali.settings.Setting;

public class AutoGApple
extends Module {
    private int oldSlot = -1;
    private boolean eating = false;

    boolean isFood(ItemStack itemStack) {
        return !AutoGApple.isNullOrEmptyStack(itemStack) && itemStack.getItem() instanceof ItemAppleGold;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent playerTickEvent) {
        int onHealth = (int)CatClient.instance.settingsManager.getSettingByName(this, "OnHealth").getValDouble();
        if ((double)(AutoGApple.mc.player.getHealth() + AutoGApple.mc.player.getAbsorptionAmount()) > (double)onHealth && this.eating) {
            this.eating = false;
            this.stop();
            return;
        }
        if (!this.canEat()) {
            return;
        }
        if (this.isFood(AutoGApple.mc.player.getHeldItemOffhand()) && (double)AutoGApple.mc.player.getHealth() <= (double)onHealth && this.canEat()) {
            KeyBinding.setKeyBindState((int)AutoGApple.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
            this.eating = true;
        }
        if (!this.canEat()) {
            this.stop();
        }
    }

    public static boolean isNullOrEmptyStack(ItemStack itemStack) {
        return itemStack == null || itemStack.isEmpty();
    }

    public boolean canEat() {
        Entity entity;
        return AutoGApple.mc.objectMouseOver == null || !((entity = AutoGApple.mc.objectMouseOver.entityHit) instanceof EntityVillager) && !(entity instanceof EntityTameable);
    }

    void stop() {
        KeyBinding.setKeyBindState((int)AutoGApple.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
    }

    public AutoGApple() {
        super("AutoApple", "eat golden apples automatically", Category.COMBAT);
        CatClient.instance.settingsManager.rSetting(new Setting("OnHealth", this, 12.0, 1.0, 20.0, true));
    }
}

