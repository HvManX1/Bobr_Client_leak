//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.gui.clickgui.components.settings;

import java.util.ArrayList;
import java.util.List;
import me.thef1xer.gateclient.gui.clickgui.ClickComponent;
import me.thef1xer.gateclient.settings.impl.RGBSetting;
import me.thef1xer.gateclient.util.RenderUtil;

public class RGBComponent
extends ClickComponent {
    private final RGBSetting setting;
    private final List<ColorSlider> sliders = new ArrayList<ColorSlider>();

    public RGBComponent(RGBSetting setting, float posX, float posY) {
        super(posX, posY);
        this.setting = setting;
        this.sliders.add(new ColorSlider(this.posX, 0.0f));
        this.sliders.add(new ColorSlider(this.posX, 0.0f));
        this.sliders.add(new ColorSlider(this.posX, 0.0f));
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, 0.15f, 0.15f, 0.15f, 1.0f);
        this.fontRenderer.drawString(this.setting.getName(), this.posX + (float)this.padding, this.posY + (float)this.padding, -1, true);
        if (this.expanded) {
            RenderUtil.draw2DTriangleRight(this.posX + (float)this.width - (float)(2 * this.padding) - 6.0f, this.posY + (float)this.padding, 4.0, this.height - 2 * this.padding, 1.0f, 1.0f, 1.0f, 1.0f);
            this.setting.setRed(this.sliders.get(0).renderSlider("Red", this.setting.getRed(), this.setting.getRed(), this.setting.getGreen(), this.setting.getBlue(), mouseX));
            this.setting.setGreen(this.sliders.get(1).renderSlider("Green", this.setting.getGreen(), this.setting.getRed(), this.setting.getGreen(), this.setting.getBlue(), mouseX));
            this.setting.setBlue(this.sliders.get(2).renderSlider("Blue", this.setting.getBlue(), this.setting.getRed(), this.setting.getGreen(), this.setting.getBlue(), mouseX));
        } else {
            RenderUtil.draw2DTriangleDown(this.posX + (float)this.width - (float)(2 * this.padding) - 8.0f, this.posY + (float)this.padding + 2.0f, 8.0, this.height - 2 * this.padding - 4, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseHover(mouseX, mouseY)) {
            this.expanded = !this.expanded;
            this.updateHierarchy();
        }
        if (this.expanded) {
            for (ClickComponent clickComponent : this.sliders) {
                clickComponent.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.expanded) {
            for (ClickComponent clickComponent : this.sliders) {
                clickComponent.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    @Override
    public float updatedByParent(float offsetY) {
        this.posY = offsetY;
        return this.expanded ? this.updateChildren() : offsetY + (float)this.height;
    }

    @Override
    public float updateChildren() {
        float offsetY = this.posY + (float)this.height;
        for (ClickComponent clickComponent : this.sliders) {
            offsetY = clickComponent.updatedByParent(offsetY);
        }
        return offsetY;
    }

    public static class ColorSlider
    extends ClickComponent {
        private boolean dragging = false;

        public ColorSlider(float posX, float posY) {
            super(posX, posY);
            this.height += 4;
        }

        public int renderSlider(String color, int slider, int red, int green, int blue, int mouseX) {
            RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, 0.15f, 0.15f, 0.15f, 1.0f);
            this.fontRenderer.drawString(color, this.posX + (float)(2 * this.padding), this.posY + (float)this.padding, red << 16 | green << 8 | blue, true);
            this.fontRenderer.drawString(Integer.valueOf(slider).toString(), this.posX + (float)this.width - (float)this.fontRenderer.getStringWidth(Integer.valueOf(slider).toString()) - (float)(2 * this.padding), this.posY + (float)this.padding, -1, true);
            RenderUtil.draw2DRect(this.posX + (float)(2 * this.padding), this.posY + (float)this.height - (float)this.padding - 3.0f, this.width - 4 * this.padding, 2.0, 0.0f, 0.0f, 0.0f, 1.0f);
            RenderUtil.draw2DRect(this.posX + (float)(2 * this.padding), this.posY + (float)this.height - (float)this.padding - 3.0f, (float)((this.width - 4 * this.padding) * slider) / 255.0f, 2.0, 0.85f, 0.43f, 0.0f, 1.0f);
            if (this.dragging) {
                double wMin = this.posX + (float)(2 * this.padding);
                double wMax = this.posX + (float)this.width - (float)(2 * this.padding);
                if ((double)mouseX > wMax) {
                    return 255;
                }
                if ((double)mouseX < wMin) {
                    return 0;
                }
                return Math.round(255.0f * (float)((double)mouseX - wMin) / (float)(this.width - 4 * this.padding));
            }
            return slider;
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
}

