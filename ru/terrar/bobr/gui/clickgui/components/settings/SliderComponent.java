//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.gui.clickgui.components.settings;

import ru.terrar.bobr.gui.clickgui.ClickComponent;
import ru.terrar.bobr.settings.impl.FloatSetting;
import ru.terrar.bobr.util.RenderUtil;

public class SliderComponent
extends ClickComponent {
    private final FloatSetting setting;
    private boolean dragging = false;

    public SliderComponent(FloatSetting setting, float posX, float posY) {
        super(posX, posY);
        this.setting = setting;
        this.height += 4;
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, 0.15f, 0.15f, 0.15f, 1.0f);
        this.fontRenderer.drawString(this.setting.getName(), this.posX + (float)this.padding, this.posY + (float)this.padding, -1, true);
        String s = Float.valueOf((float)Math.round(this.setting.getValue() * 10.0f) / 10.0f).toString();
        this.fontRenderer.drawString(s, this.posX + (float)this.width - (float)this.padding - (float)this.fontRenderer.getStringWidth(s), this.posY + (float)this.padding, -1, true);
        RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height - (float)this.padding - 3.0f, this.width - 2 * this.padding, 2.0, 0.1f, 0.1f, 0.1f, 1.0f);
        RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height - (float)this.padding - 3.0f, (float)(this.width - 2 * this.padding) * this.setting.getValue() / this.setting.getMax(), 2.0, 0.85f, 0.43f, 0.0f, 1.0f);
        if (this.dragging) {
            double wMin = this.posX + (float)this.padding;
            double wMax = this.posX + (float)this.width - (float)this.padding;
            if ((double)mouseX > wMax) {
                this.setting.setValue(this.setting.getMax());
            } else if ((double)mouseX < wMin) {
                this.setting.setValue(this.setting.getMin());
            } else {
                float f1 = ((float)mouseX - this.posX - (float)this.padding) / (float)(this.width - 2 * this.padding) * (this.setting.getMax() - this.setting.getMin()) + this.setting.getMin();
                this.setting.setValueWithStep(f1);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseHover(mouseX, mouseY)) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.dragging) {
            this.dragging = false;
        }
    }
}

