//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package de.vinii.management.ui.clickgui.components;

import de.vinii.management.ui.clickgui.Panel;
import de.vinii.management.ui.clickgui.components.GuiComponent;
import de.vinii.management.ui.clickgui.components.listeners.ComboListener;
import de.vinii.management.ui.clickgui.util.RenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import ru.internali.settings.Setting;

public class GuiComboBox
implements GuiComponent {
    private Setting setting;
    private boolean extended;
    private int height;
    private int posX;
    private int posY;
    private int width;
    private ArrayList<ComboListener> comboListeners = new ArrayList();

    public GuiComboBox(Setting setting) {
        this.setting = setting;
    }

    public void addComboListener(ComboListener comboListener) {
        this.comboListeners.add(comboListener);
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY, int wheelY) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        switch (Panel.theme) {
            case "Caesium": {
                this.renderCaesium();
            }
        }
    }

    public void renderCaesium() {
        if (this.extended) {
            RenderUtil.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + Panel.fR.FONT_HEIGHT + 2, Panel.grey40_240);
            RenderUtil.drawHorizontalLine(this.posX, this.posX + this.width, this.posY, Panel.black195);
            RenderUtil.drawHorizontalLine(this.posX, this.posX + this.width, this.posY + Panel.fR.FONT_HEIGHT + 2, new Color(0, 0, 0, 150).getRGB());
            Panel.fR.drawStringWithShadow(this.setting.getName() + "  -", (float)(this.posX + this.width / 2 - Panel.fR.getStringWidth(this.setting.getName() + "  -") / 2), (float)(this.posY + 2), Panel.fontColor);
            int innerHeight = Panel.fR.FONT_HEIGHT + 5;
            for (String combo : this.setting.getOptions()) {
                if (this.setting.getValString().equalsIgnoreCase(combo)) {
                    Panel.fR.drawStringWithShadow(combo, (float)(this.posX + 10), (float)(this.posY + innerHeight), Panel.color);
                } else {
                    Panel.fR.drawStringWithShadow(combo, (float)(this.posX + 5), (float)(this.posY + innerHeight), Panel.fontColor);
                }
                innerHeight += Panel.fR.FONT_HEIGHT + 2;
            }
            this.height = innerHeight + 2;
        } else {
            RenderUtil.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + Panel.fR.FONT_HEIGHT + 2, Panel.grey40_240);
            RenderUtil.drawHorizontalLine(this.posX, this.posX + this.width, this.posY, Panel.black195);
            RenderUtil.drawHorizontalLine(this.posX, this.posX + this.width, this.posY + Panel.fR.FONT_HEIGHT + 2, Panel.black195);
            Panel.fR.drawStringWithShadow(this.setting.getName() + "  +", (float)(this.posX + this.width / 2 - Panel.fR.getStringWidth(this.setting.getName() + "  +") / 2), (float)(this.posY + 2), Panel.fontColor);
            this.height = Panel.fR.FONT_HEIGHT + 4;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isHovered(this.posX, this.posY, this.width, Panel.fR.FONT_HEIGHT + 2, mouseX, mouseY)) {
            boolean bl = this.extended = !this.extended;
        }
        if (this.extended && RenderUtil.isHovered(this.posX, this.posY + Panel.fR.FONT_HEIGHT + 2, this.width, (Panel.fR.FONT_HEIGHT + 2) * this.setting.getOptions().size(), mouseX, mouseY) && mouseButton == 0) {
            int h = Panel.fR.FONT_HEIGHT + 2;
            for (int i = 1; i <= this.setting.getOptions().size() + 1; ++i) {
                if (!RenderUtil.isHovered(this.posX, this.posY + h * i, this.width, h * i, mouseX, mouseY)) continue;
                this.setting.setValString(this.setting.getOptions().get(i - 1));
            }
            for (ComboListener comboListener : this.comboListeners) {
                comboListener.comboChanged(this.setting.getValString());
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public boolean allowScroll() {
        return true;
    }

    public Setting getSetting() {
        return this.setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}

