//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.vinii.management.ui.clickgui.components;

import de.vinii.management.ui.clickgui.ClickGui;
import de.vinii.management.ui.clickgui.Panel;
import de.vinii.management.ui.clickgui.components.Frame;
import de.vinii.management.ui.clickgui.components.GuiButton;
import de.vinii.management.ui.clickgui.components.GuiComponent;
import de.vinii.management.ui.clickgui.util.RenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;

public class GuiFrame
implements Frame {
    private ArrayList<GuiButton> buttons = new ArrayList();
    private boolean isExpaned;
    private boolean isDragging;
    private int id;
    private int posX;
    private int posY;
    private int prevPosX;
    private int prevPosY;
    private int scrollHeight;
    public static int dragID;
    private String title;

    public GuiFrame(String title, int posX, int posY, boolean expanded) {
        this.title = title;
        this.posX = posX;
        this.posY = posY;
        this.isExpaned = expanded;
        this.id = ++ClickGui.compID;
        this.scrollHeight = 0;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        switch (Panel.theme) {
            case "Caesium": {
                this.renderCaesium(mouseX, mouseY);
            }
        }
    }

    private void renderCaesium(int mouseX, int mouseY) {
        int color = Panel.color;
        int fontColor = Panel.fontColor;
        int width = Math.max(Panel.FRAME_WIDTH, Panel.fR.getStringWidth(this.title) + 15);
        if (this.isDragging && Mouse.isButtonDown((int)0)) {
            this.posX = mouseX - this.prevPosX;
            this.posY = mouseY - this.prevPosY;
            dragID = this.id;
        } else {
            this.isDragging = false;
            dragID = -1;
        }
        for (GuiButton button : this.buttons) {
            width = Math.max(width, button.getWidth() + 15);
        }
        ru.internali.utils.RenderUtil.drawVGradientRect(this.posX + 1, this.posY - 5, this.posX + width, this.posY + 12, new Color(color).brighter().getRGB(), new Color(color).darker().getRGB());
        Panel.fR.drawStringWithShadow(this.title, (float)(this.posX + width / 2 - Panel.fR.getStringWidth(this.title) / 2), (float)this.posY, fontColor);
        Panel.fR.drawStringWithShadow(this.isExpaned ? "-" : "+", (float)(this.posX + width - Panel.fR.getStringWidth(this.isExpaned ? "-" : "+") - 4), (float)this.posY, fontColor);
        if (this.isExpaned) {
            int height = 0;
            int background = Panel.grey40_240;
            for (GuiButton button : this.buttons) {
                ArrayList<GuiComponent> components;
                button.render(this.posX + 1, this.posY + height + 12, width, mouseX, mouseY, 0);
                if (button.getButtonID() == GuiButton.expandedID && !(components = button.getComponents()).isEmpty()) {
                    int xOffset = 10;
                    int yOffset = 0;
                    boolean allowScroll = true;
                    for (GuiComponent component : components) {
                        xOffset = Math.max(xOffset, component.getWidth());
                        yOffset += component.getHeight();
                        if (component.allowScroll()) continue;
                        allowScroll = false;
                    }
                    int left = this.posX + width + 2;
                    int right = left + xOffset;
                    int top = this.posY + height + 12;
                    int bottom = top + yOffset + 1;
                    int wheelY = Mouse.getDWheel() * -1 / 8;
                    if (bottom + this.scrollHeight < 30) {
                        wheelY *= -1;
                        this.scrollHeight += 10;
                    }
                    if (allowScroll) {
                        this.scrollHeight += wheelY;
                    }
                    RenderUtil.drawRect(left + 1, top + 1 + this.scrollHeight, right, bottom + this.scrollHeight, Panel.black100);
                    int height2 = 0;
                    for (GuiComponent component : components) {
                        component.render(left, top + height2 + 2 + this.scrollHeight, xOffset, mouseX, mouseY, wheelY);
                        height2 += component.getHeight();
                    }
                    RenderUtil.drawVerticalLine(left, top + this.scrollHeight, bottom + this.scrollHeight, color);
                    RenderUtil.drawVerticalLine(right, top + this.scrollHeight, bottom + this.scrollHeight, color);
                    RenderUtil.drawHorizontalLine(left, right, top + this.scrollHeight, color);
                    RenderUtil.drawHorizontalLine(left, right, bottom + this.scrollHeight, color);
                }
                height += button.getHeight();
            }
            RenderUtil.drawHorizontalLine(this.posX + 1, this.posX + width - 1, this.posY + height + 12, color);
            RenderUtil.drawVerticalLine(this.posX + width, this.posY - 5, this.posY + height + 14, Panel.black100);
            RenderUtil.drawVerticalLine(this.posX + width, this.posY - 4, this.posY + height + 14, Panel.black100);
            RenderUtil.drawVerticalLine(this.posX + width + 1, this.posY - 4, this.posY + height + 15, Panel.black100);
            RenderUtil.drawHorizontalLine(this.posX + 2, this.posX + width - 1, this.posY + height + 13, Panel.black100);
            RenderUtil.drawHorizontalLine(this.posX + 2, this.posX + width - 1, this.posY + height + 13, Panel.black100);
            RenderUtil.drawHorizontalLine(this.posX + 3, this.posX + width, this.posY + height + 14, Panel.black100);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int width = Panel.FRAME_WIDTH;
        if (this.isExpaned) {
            for (GuiButton button : this.buttons) {
                width = Math.max(width, button.getWidth());
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        if (RenderUtil.isHovered(this.posX, this.posY, width, 13, mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.prevPosX = mouseX - this.posX;
                this.prevPosY = mouseY - this.posY;
                this.isDragging = true;
                dragID = this.id;
            } else if (mouseButton == 1) {
                this.isExpaned = !this.isExpaned;
                this.scrollHeight = 0;
                this.isDragging = false;
                dragID = -1;
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
        if (this.isExpaned) {
            for (GuiButton button : this.buttons) {
                button.keyTyped(keyCode, typedChar);
            }
        }
    }

    @Override
    public void initialize() {
    }

    public void addButton(GuiButton button) {
        if (!this.buttons.contains(button)) {
            this.buttons.add(button);
        }
    }

    public int getButtonID() {
        return this.id;
    }

    public boolean isExpaned() {
        return this.isExpaned;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public String getTitle() {
        return this.title;
    }
}

