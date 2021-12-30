//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.gui.clickgui.components.settings;

import java.util.ArrayList;
import java.util.List;
import ru.terrar.bobr.gui.clickgui.ClickComponent;
import ru.terrar.bobr.settings.impl.EnumSetting;
import ru.terrar.bobr.util.RenderUtil;

public class EnumComponent
extends ClickComponent {
    private final EnumSetting setting;
    private final List<Option> options = new ArrayList<Option>();

    public EnumComponent(EnumSetting setting, float posX, float posY) {
        super(posX, posY);
        this.setting = setting;
        for (Enum<?> option : setting.getValues()) {
            this.options.add(new Option(option, posX, 0.0f));
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, 0.15f, 0.15f, 0.15f, 1.0f);
        this.fontRenderer.drawString(this.setting.getName(), this.posX + (float)this.padding, this.posY + (float)this.padding, -1, true);
        if (this.expanded) {
            for (Option option : this.options) {
                option.renderCheck(this.setting.getCurrentValue() == option.getOption());
            }
            RenderUtil.draw2DTriangleRight(this.posX + (float)this.width - (float)(2 * this.padding) - 6.0f, this.posY + (float)this.padding, 4.0, this.height - 2 * this.padding, 1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            RenderUtil.draw2DTriangleDown(this.posX + (float)this.width - (float)(2 * this.padding) - 8.0f, this.posY + (float)this.padding + 2.0f, 8.0, this.height - 2 * this.padding - 4, 1.0f, 1.0f, 1.0f, 1.0f);
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
        for (ClickComponent clickComponent : this.options) {
            offsetY = clickComponent.updatedByParent(offsetY);
        }
        return offsetY;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseHover(mouseX, mouseY)) {
            this.expanded = !this.expanded;
            this.updateHierarchy();
        }
        for (Option option : this.options) {
            if (!option.checkBox(mouseX, mouseY)) continue;
            this.setting.setCurrentValue(option.getOption());
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (Option option : this.options) {
            option.mouseReleased(mouseX, mouseY, state);
        }
    }

    public static class Option
    extends ClickComponent {
        private final Enum<?> option;

        public Option(Enum<?> option, float posX, float posY) {
            super(posX, posY);
            this.option = option;
        }

        public void renderCheck(boolean isCurrent) {
            RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, 0.15f, 0.15f, 0.15f, 1.0f);
            if (isCurrent) {
                RenderUtil.draw2DRect(this.posX + (float)(2 * this.padding), this.posY + (float)this.padding, 8.0, 8.0, 0.85f, 0.43f, 0.0f, 1.0f);
            }
            RenderUtil.draw2DRectLines(this.posX + (float)(2 * this.padding), this.posY + (float)this.padding, 8.0, 8.0, 0.8f, 0.8f, 0.8f, 0.8f);
            this.fontRenderer.drawString(this.option.toString(), this.posX + (float)(3 * this.padding) + 9.0f, this.posY + (float)this.padding, -1, true);
        }

        public boolean checkBox(int mouseX, int mouseY) {
            return this.isMouseHover(mouseX, mouseY);
        }

        public Enum<?> getOption() {
            return this.option;
        }
    }
}

