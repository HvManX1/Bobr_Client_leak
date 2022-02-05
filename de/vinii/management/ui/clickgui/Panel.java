//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package de.vinii.management.ui.clickgui;

import de.vinii.management.ui.clickgui.ClickGui;
import de.vinii.management.ui.clickgui.components.Frame;
import de.vinii.management.ui.clickgui.components.GuiButton;
import de.vinii.management.ui.clickgui.components.GuiFrame;
import de.vinii.management.ui.clickgui.listeners.ClickListener;
import de.vinii.management.ui.clickgui.listeners.ComponentsListener;
import de.vinii.management.ui.clickgui.util.FramePosition;
import java.awt.Color;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.Module;

public class Panel
extends ClickGui {
    public static HashMap<String, FramePosition> framePositions = new HashMap();
    public static FontRenderer fR = Minecraft.getMinecraft().fontRenderer;
    public static String theme;
    public static int FRAME_WIDTH;
    public static int color;
    public static int fontColor;
    public static int grey40_240;
    public static int black195;
    public static int black100;

    public Panel(String theme, int fontSize) {
        Panel.theme = theme;
    }

    public void ref(Category cat, int x) {
        GuiFrame frame = new GuiFrame(cat.name(), x, 50, true);
        for (Module m : CatClient.instance.moduleManager.modules) {
            if (cat != m.getCategory() || !m.visible) continue;
            GuiButton button = new GuiButton(m.getName());
            button.addClickListener(new ClickListener(button));
            button.addExtendListener(new ComponentsListener(button));
            frame.addButton(button);
        }
        this.addFrame(frame);
    }

    @Override
    public void initGui() {
        int x = 25;
        this.ref(Category.COMBAT, x);
        this.ref(Category.MOVEMENT, x += 140);
        this.ref(Category.PLAYER, x += 140);
        this.ref(Category.RENDER, x += 140);
        this.ref(Category.OUTHER, x += 140);
        this.ref(Category.HUD, x += 140);
        super.initGui();
    }

    public void onGuiClosed() {
        if (!this.getFrames().isEmpty()) {
            for (Frame frame : this.getFrames()) {
                GuiFrame guiFrame = (GuiFrame)frame;
                framePositions.put(guiFrame.getTitle(), new FramePosition(guiFrame.getPosX(), guiFrame.getPosY(), guiFrame.isExpaned()));
            }
        }
    }

    static {
        FRAME_WIDTH = 100;
        color = new Color(116, 255, 0, 220).getRGB();
        fontColor = Color.white.getRGB();
        grey40_240 = new Color(40, 40, 40, 140).getRGB();
        black195 = new Color(0, 0, 0, 195).getRGB();
        black100 = new Color(0, 0, 0, 100).getRGB();
    }
}

