//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package me.thef1xer.gateclient.gui.clickgui.components.settings;

import me.thef1xer.gateclient.gui.clickgui.ClickComponent;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.util.RenderUtil;
import org.lwjgl.input.Keyboard;

public class KeybindComponent
extends ClickComponent {
    private final Module module;
    private boolean listening = false;

    public KeybindComponent(Module module, float posX, float posY) {
        super(posX, posY);
        this.module = module;
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, 0.15f, 0.15f, 0.15f, 1.0f);
        this.fontRenderer.drawString("Keybind", this.posX + (float)this.padding, this.posY + (float)this.padding, -1, true);
        if (this.listening) {
            this.fontRenderer.drawString("...", this.posX + (float)this.width - (float)this.padding - (float)this.fontRenderer.getStringWidth("..."), this.posY + (float)this.padding, -1, true);
        } else {
            String key = Keyboard.getKeyName((int)this.module.getKeyBind());
            this.fontRenderer.drawString(key, this.posX + (float)this.width - (float)this.padding - (float)this.fontRenderer.getStringWidth(key), this.posY + (float)this.padding, -1, true);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.listening) {
            if (this.isMouseHover(mouseX, mouseY) && mouseButton == 1) {
                this.module.setKeyBind(0);
            }
            this.listening = false;
        } else if (this.isMouseHover(mouseX, mouseY)) {
            this.listening = true;
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (this.listening) {
            this.module.setKeyBind(keyCode);
            this.listening = false;
        }
    }
}

