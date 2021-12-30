//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 */
package ru.terrar.bobr.gui.clickgui.components;

import java.io.File;
import net.minecraft.client.renderer.GlStateManager;
import ru.terrar.bobr.bobr;
import ru.terrar.bobr.gui.TextField;
import ru.terrar.bobr.gui.clickgui.ClickComponent;
import ru.terrar.bobr.managers.PresetManager;
import ru.terrar.bobr.util.DirectoryUtil;
import ru.terrar.bobr.util.RenderUtil;

public class PresetComponent
extends ClickComponent {
    private final float fontX;
    private final PresetManager presetManager;
    private File hoveredPreset;
    private final TextField textField;
    private boolean presetListExpanded;
    private boolean createButtonExpanded;

    public PresetComponent(float posX, float posY) {
        super(posX, posY);
        this.fontX = (float)(this.width - this.fontRenderer.getStringWidth("Presets")) / 2.0f;
        this.presetManager = bobr.getGate().presetManager;
        this.presetListExpanded = false;
        this.createButtonExpanded = false;
        this.padding = 5;
        this.height = 19;
        this.expanded = true;
        this.textField = new TextField(posX + (float)this.padding + 4.0f, posY + (float)this.height + (float)(7 * this.padding) + 58.0f, this.width - 2 * this.padding - 5);
    }

    @Override
    public void drawComponent(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.draw2DRect(this.posX, this.posY, this.width, this.height, 0.18f, 0.38f, 0.9f, 1.0f);
        this.fontRenderer.drawString("Presets", this.posX + this.fontX, this.posY + (float)this.padding, -1, true);
        if (!this.expanded) {
            return;
        }
        if (this.createButtonExpanded) {
            RenderUtil.draw2DRect(this.posX, this.posY + (float)this.height, this.width, 9 * this.padding + 80, 0.1f, 0.1f, 0.1f, 1.0f);
        } else {
            RenderUtil.draw2DRect(this.posX, this.posY + (float)this.height, this.width, 7 * this.padding + 56, 0.1f, 0.1f, 0.1f, 1.0f);
        }
        this.renderAutoSave();
        GlStateManager.glLineWidth((float)2.0f);
        this.renderLoadButton(mouseX, mouseY);
        this.renderSaveButton(mouseX, mouseY);
        this.renderRemoveButton(mouseX, mouseY);
        this.renderCreate(mouseX, mouseY);
        this.renderPresetList(mouseX, mouseY);
        GlStateManager.glLineWidth((float)1.0f);
    }

    private void renderAutoSave() {
        if (this.presetManager.isAutoSave()) {
            RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(2 * this.padding) + 12.0f, 8.0, 8.0, 0.85f, 0.43f, 0.0f, 1.0f);
        }
        RenderUtil.draw2DRectLines(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(2 * this.padding) + 12.0f, 8.0, 8.0, 0.8f, 0.8f, 0.8f, 0.8f);
        this.fontRenderer.drawString("Auto-Save", this.posX + (float)(2 * this.padding) + 8.0f, this.posY + (float)this.height + (float)(2 * this.padding) + 12.0f, -1, true);
    }

    private void renderLoadButton(int mouseX, int mouseY) {
        if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)(3 * this.padding) + 20.0f, this.posX + (float)(this.width - this.padding) / 2.0f, this.posY + (float)this.height + (float)(3 * this.padding) + 32.0f)) {
            RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(3 * this.padding) + 20.0f, (float)(this.width - 3 * this.padding) / 2.0f, 12.0, 0.2f, 0.2f, 0.2f, 1.0f);
        }
        RenderUtil.draw2DRectLines(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(3 * this.padding) + 20.0f, (float)(this.width - 3 * this.padding) / 2.0f, 12.0, 0.85f, 0.43f, 0.0f, 1.0f);
        this.fontRenderer.drawString("Load", this.posX + (float)this.padding + 11.0f, this.posY + (float)this.height + (float)(3 * this.padding) + 22.0f, -1, true);
    }

    private void renderSaveButton(int mouseX, int mouseY) {
        if (this.isHovering(mouseX, mouseY, this.posX + (float)(this.width + this.padding) / 2.0f, this.posY + (float)this.height + (float)(3 * this.padding) + 20.0f, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)(3 * this.padding) + 32.0f)) {
            RenderUtil.draw2DRect(this.posX + (float)(this.width + this.padding) / 2.0f, this.posY + (float)this.height + (float)(3 * this.padding) + 20.0f, (float)(this.width - 3 * this.padding) / 2.0f, 12.0, 0.2f, 0.2f, 0.2f, 1.0f);
        }
        RenderUtil.draw2DRectLines(this.posX + (float)(this.width + this.padding) / 2.0f, this.posY + (float)this.height + (float)(3 * this.padding) + 20.0f, (float)(this.width - 3 * this.padding) / 2.0f, 12.0, 0.85f, 0.43f, 0.0f, 1.0f);
        this.fontRenderer.drawString("Save", this.posX + (float)this.width - (float)this.padding - 35.0f, this.posY + (float)this.height + (float)(3 * this.padding) + 22.0f, -1, true);
    }

    private void renderRemoveButton(int mouseX, int mouseY) {
        if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)(4 * this.padding) + 32.0f, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)(4 * this.padding) + 44.0f)) {
            RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(4 * this.padding) + 32.0f, this.width - 2 * this.padding, 12.0, 0.2f, 0.2f, 0.2f, 1.0f);
        }
        RenderUtil.draw2DRectLines(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(4 * this.padding) + 32.0f, this.width - 2 * this.padding, 12.0, 0.85f, 0.43f, 0.0f, 1.0f);
        this.fontRenderer.drawString("Remove", this.posX + (float)(this.width - this.fontRenderer.getStringWidth("Remove")) / 2.0f, this.posY + (float)this.height + (float)(4 * this.padding) + 34.0f, -1, true);
    }

    private void renderCreate(int mouseX, int mouseY) {
        if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)(6 * this.padding) + 44.0f, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)(6 * this.padding) + 56.0f)) {
            RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(6 * this.padding) + 44.0f, this.width - 2 * this.padding, 12.0, 0.2f, 0.2f, 0.2f, 1.0f);
        }
        RenderUtil.draw2DRectLines(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(6 * this.padding) + 44.0f, this.width - 2 * this.padding, 12.0, 0.85f, 0.43f, 0.0f, 1.0f);
        if (this.createButtonExpanded) {
            this.fontRenderer.drawString("Cancel", this.posX + (float)(this.width - this.fontRenderer.getStringWidth("Cancel")) / 2.0f, this.posY + (float)this.height + (float)(6 * this.padding) + 46.0f, -1, true);
            RenderUtil.draw2DRectLines(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(7 * this.padding) + 56.0f, this.width - 2 * this.padding, 12.0, 0.8f, 0.8f, 0.8f, 1.0f);
            this.textField.drawField();
            if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)(8 * this.padding) + 68.0f, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)(8 * this.padding) + 80.0f)) {
                RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(8 * this.padding) + 68.0f, this.width - 2 * this.padding, 12.0, 0.2f, 0.2f, 0.2f, 1.0f);
            }
            RenderUtil.draw2DRectLines(this.posX + (float)this.padding, this.posY + (float)this.height + (float)(8 * this.padding) + 68.0f, this.width - 2 * this.padding, 12.0, 0.85f, 0.43f, 0.0f, 1.0f);
            this.fontRenderer.drawString("Confirm", this.posX + (float)(this.width - this.fontRenderer.getStringWidth("Confirm")) / 2.0f, this.posY + (float)this.height + (float)(8 * this.padding) + 70.0f, -1, true);
        } else {
            this.fontRenderer.drawString("Create", this.posX + (float)(this.width - this.fontRenderer.getStringWidth("Create")) / 2.0f, this.posY + (float)this.height + (float)(6 * this.padding) + 46.0f, -1, true);
        }
    }

    private void renderPresetList(int mouseX, int mouseY) {
        RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height + (float)this.padding, this.width - 2 * this.padding, 12.0, 0.3f, 0.3f, 0.3f, 1.0f);
        this.fontRenderer.drawString(DirectoryUtil.removeExtension(this.presetManager.getActivePreset().getName()), this.posX + (float)this.padding + 2.0f, this.posY + (float)this.height + (float)this.padding + 2.0f, -1, true);
        if (this.presetListExpanded) {
            int offset = 12;
            this.hoveredPreset = null;
            for (File preset : this.presetManager.PRESET_LIST) {
                if (preset.equals(this.presetManager.getActivePreset())) continue;
                if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)this.padding + (float)offset, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)this.padding + 12.0f + (float)offset)) {
                    RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height + (float)this.padding + (float)offset, this.width - 2 * this.padding, 12.0, 0.4f, 0.4f, 0.4f, 1.0f);
                    this.hoveredPreset = preset;
                } else {
                    RenderUtil.draw2DRect(this.posX + (float)this.padding, this.posY + (float)this.height + (float)this.padding + (float)offset, this.width - 2 * this.padding, 12.0, 0.3f, 0.3f, 0.3f, 1.0f);
                }
                this.fontRenderer.drawString(DirectoryUtil.removeExtension(preset.getName()), this.posX + (float)this.padding + 2.0f, this.posY + (float)this.height + (float)this.padding + 2.0f + (float)offset, -1, true);
                offset += 12;
            }
            RenderUtil.draw2DTriangleRight(this.posX + (float)this.width - (float)(2 * this.padding) - 6.0f, this.posY + (float)this.height + (float)this.padding + 2.0f, 4.0, 8.0, 1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            RenderUtil.draw2DTriangleDown(this.posX + (float)this.width - (float)(2 * this.padding) - 8.0f, this.posY + (float)this.height + (float)this.padding + 4.0f, 8.0, 4.0, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseHover(mouseX, mouseY)) {
            this.expanded = !this.expanded;
        } else if (this.expanded) {
            if (this.presetListExpanded && this.hoveredPreset != null) {
                this.presetManager.setActivePreset(this.hoveredPreset);
                this.presetManager.loadActivePreset();
                this.presetListExpanded = false;
                return;
            }
            if (this.isHovering(mouseX, mouseY, this.posX, this.posY + (float)this.height + (float)(2 * this.padding) + 12.0f, this.posX + (float)this.width, this.posY + (float)this.height + (float)(2 * this.padding) + 20.0f)) {
                this.presetManager.setAutoSave(!this.presetManager.isAutoSave());
            } else if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)(3 * this.padding) + 20.0f, this.posX + (float)(this.width - this.padding) / 2.0f, this.posY + (float)this.height + (float)(3 * this.padding) + 32.0f)) {
                this.presetManager.loadActivePreset();
            } else if (this.isHovering(mouseX, mouseY, this.posX + (float)(this.width + this.padding) / 2.0f, this.posY + (float)this.height + (float)(3 * this.padding) + 20.0f, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)(3 * this.padding) + 32.0f)) {
                this.presetManager.saveActivePreset();
            } else if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)(4 * this.padding) + 32.0f, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)(4 * this.padding) + 44.0f)) {
                this.presetManager.removeActivePreset();
            } else if (this.createButtonExpanded) {
                String newPreset;
                if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)(6 * this.padding) + 44.0f, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)(6 * this.padding) + 56.0f)) {
                    this.createButtonExpanded = false;
                    this.textField.setText("");
                    this.textField.setFocused(false);
                } else if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)(8 * this.padding) + 68.0f, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)(8 * this.padding) + 80.0f) && !(newPreset = this.textField.getText().trim()).isEmpty() && bobr.getGate().presetManager.createNewPreset(newPreset + ".json")) {
                    this.createButtonExpanded = false;
                    this.textField.setText("");
                }
            } else if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)(6 * this.padding) + 44.0f, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)(6 * this.padding) + 56.0f)) {
                this.createButtonExpanded = true;
                this.textField.setFocused(true);
            } else if (this.isHovering(mouseX, mouseY, this.posX + (float)this.padding, this.posY + (float)this.height + (float)this.padding, this.posX + (float)this.width - (float)this.padding, this.posY + (float)this.height + (float)this.padding + 12.0f)) {
                this.presetManager.updatePresetList();
                this.presetListExpanded = !this.presetListExpanded;
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (this.createButtonExpanded && this.textField.isFocused()) {
            if (keyCode == 1) {
                this.createButtonExpanded = false;
            } else if (keyCode == 28 || keyCode == 156) {
                String newPreset = this.textField.getText().trim();
                if (!newPreset.isEmpty() && bobr.getGate().presetManager.createNewPreset(newPreset + ".json")) {
                    this.createButtonExpanded = false;
                    this.textField.setText("");
                }
            } else {
                this.textField.keyTyped(typedChar, keyCode);
            }
        }
    }

    private boolean isHovering(int mouseX, int mouseY, float minX, float minY, float maxX, float maxY) {
        return (float)mouseX > minX && (float)mouseX < maxX && (float)mouseY > minY && (float)mouseY < maxY;
    }
}

