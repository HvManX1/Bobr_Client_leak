//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.gui.clickgui.components.settings;

import me.thef1xer.gateclient.gui.clickgui.ClickComponent;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import me.thef1xer.gateclient.util.RenderUtil;

public class BooleanComponent
extends ClickComponent {
    private final BooleanSetting setting;

    public BooleanComponent(BooleanSetting setting, float posX, float posY) {
        super(posX, posY);
        this.setting = setting;
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, 0.15f, 0.15f, 0.15f, 1.0f);
        this.fontRenderer.drawString(this.setting.getName(), this.posX + (float)this.padding, this.posY + (float)this.padding, -1, true);
        if (this.setting.getValue()) {
            RenderUtil.draw2DRect(this.posX + (float)this.width - (float)this.padding - 8.0f, this.posY + (float)this.padding, 8.0, 8.0, 0.85f, 0.43f, 0.0f, 1.0f);
        }
        RenderUtil.draw2DRectLines(this.posX + (float)this.width - (float)this.padding - 8.0f, this.posY + (float)this.padding, 8.0, 8.0, 0.8f, 0.8f, 0.8f, 0.8f);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseHover(mouseX, mouseY)) {
            this.setting.toggle();
        }
    }
}

