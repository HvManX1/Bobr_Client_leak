//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.GlStateManager
 */
package ru.terrar.bobr.gui.clickgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import ru.terrar.bobr.bobr;
import ru.terrar.bobr.gui.clickgui.ClickComponent;
import ru.terrar.bobr.gui.clickgui.components.CategoryComponent;
import ru.terrar.bobr.gui.clickgui.components.PresetComponent;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.modules.hud.ClickGuiModule;

public class ClickGui
extends GuiScreen {
    private final List<ClickComponent> components = new ArrayList<ClickComponent>();

    public void init() {
        this.components.add(new CategoryComponent(Module.ModuleCategory.COMBAT, 20.0f, 20.0f));
        this.components.add(new CategoryComponent(Module.ModuleCategory.HUD, 146.0f, 20.0f));
        this.components.add(new CategoryComponent(Module.ModuleCategory.MOVEMENT, 272.0f, 20.0f));
        this.components.add(new CategoryComponent(Module.ModuleCategory.PLAYER, 398.0f, 20.0f));
        this.components.add(new CategoryComponent(Module.ModuleCategory.RENDER, 524.0f, 20.0f));
        this.components.add(new PresetComponent(650.0f, 20.0f));
        this.onUpdate();
    }

    public void onUpdate() {
        for (ClickComponent component : this.components) {
            if (!component.isExpanded()) continue;
            component.updateChildren();
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.glLineWidth((float)1.0f);
        for (ClickComponent category : this.components) {
            category.drawComponent(mouseX, mouseY, partialTicks);
        }
        GlStateManager.disableTexture2D();
        GlStateManager.popMatrix();
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == ClickGuiModule.INSTANCE.getKeyBind()) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
        for (ClickComponent component : this.components) {
            component.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (ClickComponent component : this.components) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (ClickComponent component : this.components) {
            component.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void onGuiClosed() {
        ClickGuiModule.INSTANCE.setEnabled(false);
        if (bobr.getGate().presetManager.isAutoSave()) {
            bobr.getGate().presetManager.saveActivePreset();
        }
        super.onGuiClosed();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}

