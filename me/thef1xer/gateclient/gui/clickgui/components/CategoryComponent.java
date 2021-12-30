//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package me.thef1xer.gateclient.gui.clickgui.components;

import me.thef1xer.gateclient.GateClient;
import me.thef1xer.gateclient.gui.clickgui.ClickComponent;
import me.thef1xer.gateclient.gui.clickgui.components.ModuleComponent;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.util.RenderUtil;

public class CategoryComponent
extends ClickComponent {
    private final String displayName;
    private final float fontX;

    public CategoryComponent(Module.ModuleCategory category, float posX, float posY) {
        super(posX, posY);
        this.padding = 5;
        this.height = 18;
        this.expanded = true;
        this.displayName = category.getName();
        for (Module module : GateClient.getGate().moduleManager.MODULE_LIST) {
            if (module.getModuleCategory() != category) continue;
            this.children.add(new ModuleComponent(module, posX, 0.0f));
        }
        this.fontX = (float)(this.width - this.fontRenderer.getStringWidth(this.displayName)) / 2.0f;
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, 0.18f, 0.38f, 0.9f, 1.0f);
        this.fontRenderer.drawString(this.displayName, this.posX + this.fontX, this.posY + (float)this.padding, -1, true);
        if (this.expanded) {
            for (ClickComponent module : this.children) {
                module.drawComponent(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseHover(mouseX, mouseY)) {
            this.expanded = !this.expanded;
            this.updateHierarchy();
        }
        if (this.expanded) {
            for (ClickComponent module : this.children) {
                module.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.expanded) {
            for (ClickComponent module : this.children) {
                module.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        for (ClickComponent module : this.children) {
            module.keyTyped(typedChar, keyCode);
        }
    }
}

