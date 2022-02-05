//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package ru.internali.module.HUD;

import net.minecraft.client.gui.GuiScreen;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;

public class ClickGUI
extends Module {
    public ClickGUI() {
        super("ClickGUI", "menu.skeet", Category.HUD);
        this.setKey(54);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen((GuiScreen)CatClient.csgui);
        this.setToggled(false);
    }
}

