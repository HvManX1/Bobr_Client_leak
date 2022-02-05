//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  org.lwjgl.input.Mouse
 */
package de.vinii.management.ui.clickgui.components;

import de.vinii.management.ui.clickgui.Panel;
import de.vinii.management.ui.clickgui.components.GuiComponent;
import de.vinii.management.ui.clickgui.components.listeners.ValueListener;
import de.vinii.management.ui.clickgui.util.MathUtil;
import de.vinii.management.ui.clickgui.util.RenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class GuiSlider
implements GuiComponent {
    private static int dragId = -1;
    private int round;
    private int id;
    private int width;
    private int posX;
    private int posY;
    private float min;
    private float max;
    private float current;
    private double c;
    private boolean wasSliding;
    private boolean hovered;
    private String text;
    private ArrayList<ValueListener> valueListeners = new ArrayList();

    public GuiSlider(String text, float min, float max, float current, int round) {
        this.text = text;
        this.min = min;
        this.max = max;
        this.current = current;
        this.c = current / max;
        this.round = round;
        this.id = ++Panel.compID;
    }

    public void addValueListener(ValueListener vallistener) {
        this.valueListeners.add(vallistener);
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    @Override
    public void render(int posX, int posY, int width, int mouseX, int mouseY, int wheelY) {
        double diff;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.hovered = RenderUtil.isHovered(posX, posY, width, this.getHeight(), mouseX, mouseY);
        if (this.hovered && wheelY != 0) {
            diff = this.min < 0.0f ? (double)Math.abs(this.min - this.max) : (double)(this.max - this.min);
            double w = wheelY / 15;
            this.current = this.round == 0 ? (float)((int)GuiSlider.clamp((float)((double)this.current + w), this.min, this.max)) : GuiSlider.clamp((float)((double)this.current + (w *= diff / 100.0)), this.min, this.max);
        }
        if (Mouse.isButtonDown((int)0) && (dragId == this.id || dragId == -1) && this.hovered) {
            if (mouseX < posX + 2) {
                this.current = this.min;
            } else if (mouseX > posX + width) {
                this.current = this.max;
            } else {
                diff = this.max - this.min;
                double val = (double)this.min + (double)GuiSlider.clamp((float)((double)(mouseX - posX + 3) / (double)width), 0.0f, 1.0f) * diff;
                this.current = this.round == 0 ? (float)((int)val) : (float)val;
            }
            dragId = this.id;
            for (ValueListener listener : this.valueListeners) {
                listener.valueUpdated(this.current);
            }
            this.wasSliding = true;
        } else if (!Mouse.isButtonDown((int)0) && this.wasSliding) {
            for (ValueListener listener : this.valueListeners) {
                listener.valueChanged(this.current);
            }
            dragId = -1;
            this.wasSliding = false;
        }
        double percent = (this.current - this.min) / (this.max - this.min);
        switch (Panel.theme) {
            case "Caesium": {
                this.renderCaesium(percent);
            }
        }
    }

    private void renderCaesium(double percent) {
        String value = this.round == 0 ? "" + Math.round(this.current) : "" + MathUtil.round(this.current, this.round);
        Color color = new Color(Panel.color);
        Panel.fR.drawStringWithShadow(this.text + ":", (float)(this.posX + 3), (float)(this.posY + 3), Panel.fontColor);
        Panel.fR.drawStringWithShadow(value, (float)(this.posX + this.width - Panel.fR.getStringWidth(value) - 3), (float)(this.posY + 3), Panel.fontColor);
        Gui.drawRect((int)this.posX, (int)(this.posY + Panel.fR.FONT_HEIGHT + 3), (int)(this.posX + this.width - 2), (int)(this.posY + Panel.fR.FONT_HEIGHT + 5), (int)Color.black.getRGB());
        ru.internali.utils.RenderUtil.drawVGradientRect(this.posX, this.posY + Panel.fR.FONT_HEIGHT + 3, (int)(percent * (double)this.width - 2.0) + this.posX, this.posY + Panel.fR.FONT_HEIGHT + 5, color.darker().getRGB(), color.brighter().getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(int keyCode, char typedChar) {
    }

    @Override
    public int getWidth() {
        return Panel.fR.getStringWidth(this.text + (this.round == 0 ? (float)Math.round(this.current) : MathUtil.round(this.current, this.round))) + 68;
    }

    @Override
    public int getHeight() {
        return Panel.fR.FONT_HEIGHT + 6;
    }

    @Override
    public boolean allowScroll() {
        return !this.hovered;
    }
}

