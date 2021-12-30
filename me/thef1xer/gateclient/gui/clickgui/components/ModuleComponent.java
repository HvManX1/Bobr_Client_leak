//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.gui.clickgui.components;

import me.thef1xer.gateclient.gui.clickgui.ClickComponent;
import me.thef1xer.gateclient.gui.clickgui.components.settings.BooleanComponent;
import me.thef1xer.gateclient.gui.clickgui.components.settings.EnumComponent;
import me.thef1xer.gateclient.gui.clickgui.components.settings.KeybindComponent;
import me.thef1xer.gateclient.gui.clickgui.components.settings.RGBComponent;
import me.thef1xer.gateclient.gui.clickgui.components.settings.SliderComponent;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.Setting;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import me.thef1xer.gateclient.settings.impl.EnumSetting;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import me.thef1xer.gateclient.settings.impl.RGBSetting;
import me.thef1xer.gateclient.util.RenderUtil;

public class ModuleComponent
extends ClickComponent {
    private final Module module;
    private boolean expanded = false;

    public ModuleComponent(Module module, float posX, float posY) {
        super(posX, posY);
        this.module = module;
        this.children.add(new KeybindComponent(module, posX, 0.0f));
        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                this.children.add(new BooleanComponent((BooleanSetting)setting, posX, 0.0f));
                continue;
            }
            if (setting instanceof EnumSetting) {
                this.children.add(new EnumComponent((EnumSetting)setting, posX, 0.0f));
                continue;
            }
            if (setting instanceof FloatSetting) {
                this.children.add(new SliderComponent((FloatSetting)setting, posX, 0.0f));
                continue;
            }
            if (!(setting instanceof RGBSetting)) continue;
            this.children.add(new RGBComponent((RGBSetting)setting, posX, 0.0f));
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
        float b;
        float g;
        float r;
        int textColor = -1;
        if (this.module.isEnabled()) {
            r = 0.85f;
            g = 0.43f;
            b = 0.0f;
        } else if (this.isMouseHover(mouseX, mouseY)) {
            r = 0.42f;
            g = 0.21f;
            b = 0.0f;
        } else {
            r = 0.1f;
            g = 0.1f;
            b = 0.1f;
            textColor = -5592406;
        }
        RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, r, g, b, 1.0f);
        this.fontRenderer.drawString(this.module.getName(), this.posX + (float)this.padding, this.posY + (float)this.padding, textColor, true);
        if (this.expanded) {
            for (ClickComponent setting : this.children) {
                setting.drawComponent(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseHover(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.module.toggle();
            } else if (mouseButton == 1) {
                this.expanded = !this.expanded;
                this.updateHierarchy();
            }
        }
        if (this.expanded) {
            for (ClickComponent setting : this.children) {
                setting.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.expanded) {
            for (ClickComponent setting : this.children) {
                setting.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        for (ClickComponent component : this.children) {
            component.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public float updatedByParent(float offsetY) {
        this.posY = offsetY;
        return this.expanded ? this.updateChildren() : offsetY + (float)this.height;
    }
}

