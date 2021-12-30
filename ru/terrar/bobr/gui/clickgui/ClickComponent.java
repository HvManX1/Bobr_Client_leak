//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package ru.terrar.bobr.gui.clickgui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ru.terrar.bobr.bobr;

public class ClickComponent {
    public float posX;
    public float posY;
    public int padding = 3;
    public int width = 106;
    public int height = 14;
    protected List<ClickComponent> children = new ArrayList<ClickComponent>();
    protected boolean expanded = false;
    protected FontRenderer fontRenderer;

    public ClickComponent(float posX, float posY) {
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
        this.posX = posX;
        this.posY = posY;
    }

    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public void keyTyped(char typedChar, int keyCode) {
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    protected boolean isMouseHover(int mouseX, int mouseY) {
        return (float)mouseX > this.posX && (float)mouseX < this.posX + (float)this.width && (float)mouseY > this.posY && (float)mouseY < this.posY + (float)this.height;
    }

    public float updatedByParent(float offsetY) {
        this.posY = offsetY;
        return offsetY + (float)this.height;
    }

    public float updateChildren() {
        float offsetY = this.posY + (float)this.height;
        for (ClickComponent component : this.children) {
            offsetY = component.updatedByParent(offsetY);
        }
        return offsetY;
    }

    protected void updateHierarchy() {
        bobr.getGate().guiManager.CLICK_GUI.onUpdate();
    }
}

