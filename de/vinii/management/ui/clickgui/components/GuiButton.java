//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package de.vinii.management.ui.clickgui.components;

import de.vinii.management.ui.clickgui.ClickGui;
import de.vinii.management.ui.clickgui.Panel;
import de.vinii.management.ui.clickgui.components.GuiComponent;
import de.vinii.management.ui.clickgui.components.GuiFrame;
import de.vinii.management.ui.clickgui.components.listeners.ComponentListener;
import de.vinii.management.ui.clickgui.util.RenderUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import ru.internali.CatClient;

public class GuiButton
implements GuiComponent {
    public static int expandedID = -1;
    private int id;
    private String text;
    private ArrayList<ActionListener> clickListeners = new ArrayList();
    private ArrayList<GuiComponent> guiComponents = new ArrayList();
    private int width;
    private int textWidth;
    private int posX;
    private int posY;

    public GuiButton(String text) {
        this.text = text;
        this.textWidth = Panel.fR.getStringWidth(text);
        this.id = ++ClickGui.compID;
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY, int wheelY) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        int height = this.getHeight();
        this.renderCaesium(posX, posY, width, height);
    }

    private void renderCaesium(int posX, int posY, int width, int height) {
        RenderUtil.drawRect(posX, posY, posX + width - 1, posY + height, Panel.black100);
        int color = Panel.fontColor;
        if (CatClient.instance.moduleManager.getModule(this.getText()).isToggled()) {
            color = Panel.color;
        }
        Panel.fR.drawStringWithShadow(this.getText(), (float)(posX + width / 2 - Panel.fR.getStringWidth(this.getText()) / 2), (float)(posY + 2), color);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (GuiFrame.dragID == -1 && RenderUtil.isHovered(this.posX, this.posY, this.width, this.getHeight(), mouseX, mouseY)) {
            if (mouseButton == 1) {
                expandedID = expandedID != this.id ? this.id : -1;
            } else if (mouseButton == 0) {
                for (ActionListener listener : this.clickListeners) {
                    listener.actionPerformed(new ActionEvent(this, this.id, "click", System.currentTimeMillis(), 0));
                }
            }
        }
        if (expandedID == this.id) {
            for (GuiComponent component : this.guiComponents) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
        if (expandedID == this.id) {
            for (GuiComponent component : this.guiComponents) {
                component.keyTyped(keyCode, typedChar);
            }
        }
    }

    @Override
    public int getWidth() {
        return 5 + this.textWidth;
    }

    @Override
    public int getHeight() {
        return Panel.fR.FONT_HEIGHT + 3;
    }

    @Override
    public boolean allowScroll() {
        return true;
    }

    public String getText() {
        return this.text;
    }

    public int getButtonID() {
        return this.id;
    }

    public ArrayList<GuiComponent> getComponents() {
        return this.guiComponents;
    }

    public void addClickListener(ActionListener actionlistener) {
        this.clickListeners.add(actionlistener);
    }

    public void addExtendListener(ComponentListener listener) {
        listener.addComponents();
        this.guiComponents.addAll(listener.getComponents());
    }
}

