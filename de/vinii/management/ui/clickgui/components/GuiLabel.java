//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package de.vinii.management.ui.clickgui.components;

import de.vinii.management.ui.clickgui.Panel;
import de.vinii.management.ui.clickgui.components.GuiComponent;

public class GuiLabel
implements GuiComponent {
    private String text;

    public GuiLabel(String text) {
        this.text = text;
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY, int wheelY) {
        Panel.fR.drawStringWithShadow(this.text, (float)(posX + width / 2 - Panel.fR.getStringWidth(this.text) / 2), (float)(posY + 2), Panel.fontColor);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return Panel.fR.getStringWidth(this.text) + 4;
    }

    @Override
    public int getHeight() {
        return Panel.fR.FONT_HEIGHT + 2;
    }

    @Override
    public boolean allowScroll() {
        return true;
    }
}

