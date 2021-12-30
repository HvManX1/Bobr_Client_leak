//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.util.ChatAllowedCharacters
 */
package me.thef1xer.gateclient.gui;

import me.thef1xer.gateclient.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatAllowedCharacters;

public class TextField {
    private final float posX;
    private final float posY;
    private final int width;
    private String text;
    private boolean focused;
    private int tickCounter = 0;
    private final FontRenderer fr;

    public TextField(float posX, float posY, int width, String text) {
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.text = text;
        this.focused = false;
    }

    public TextField(float posX, float posY, int width) {
        this(posX, posY, width, "");
    }

    public void drawField() {
        String trimmedMessage = this.fr.trimStringToWidth(this.text, this.width, true);
        int stringEnd = this.fr.drawString(trimmedMessage, this.posX, this.posY, -1, true);
        if (this.focused && this.tickCounter <= 15) {
            RenderUtil.draw2DRect(stringEnd, this.posY, 1.0, 8.0, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        this.updateTickCounter();
    }

    public void keyTyped(char typedChar, int keyCode) {
        System.out.println(typedChar + " " + keyCode);
        if (keyCode == 14) {
            if (!this.text.isEmpty()) {
                this.text = this.text.substring(0, this.text.length() - 1);
            }
        } else {
            this.writeChar(typedChar);
        }
    }

    public void writeChar(char c) {
        if (ChatAllowedCharacters.isAllowedCharacter((char)c)) {
            this.text = this.text.concat(String.valueOf(c));
        }
    }

    private void updateTickCounter() {
        this.tickCounter = this.tickCounter == 30 ? 0 : ++this.tickCounter;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFocused() {
        return this.focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }
}

