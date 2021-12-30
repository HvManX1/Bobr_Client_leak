//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.ScaledResolution
 */
package ru.terrar.bobr.modules.hud;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import ru.terrar.bobr.bobr;
import ru.terrar.bobr.modules.Module;
import ru.terrar.bobr.settings.impl.BooleanSetting;
import ru.terrar.bobr.settings.impl.EnumSetting;
import ru.terrar.bobr.settings.impl.FloatSetting;
import ru.terrar.bobr.settings.impl.RGBSetting;
import ru.terrar.bobr.util.ColorUtil;

public class ModuleList
extends Module {
    public static final ModuleList INSTANCE = new ModuleList();
    private List<Module> modulesSorted;
    private final FontRenderer fr;
    public final EnumSetting color;
    public final RGBSetting staticColor;
    public final FloatSetting speed;
    public final BooleanSetting glicheffect;

    public ModuleList() {
        super("Module List", "modulelist", Module.ModuleCategory.HUD);
        this.fr = Minecraft.getMinecraft().fontRenderer;
        this.color = new EnumSetting("Li\u0420\u00b0st Color", "color", Color.values(), Color.STATIC);
        this.staticColor = new RGBSetting("Static Color", "static", 255, 255, 255);
        this.speed = new FloatSetting("GlichSpeed", "glichspeed", 15.0f, 2.0f, 50.0f);
        this.glicheffect = new BooleanSetting("GlichEffect", "glicheffect", true);
        this.addSettings(this.color, this.staticColor, this.speed, this.glicheffect);
    }

    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public void drawList(ScaledResolution sr) {
        int i = 0;
        for (Module module : this.modulesSorted) {
            int hexColor;
            if (!module.isEnabled() || !module.drawOnHud.getValue()) continue;
            if (this.color.getCurrentValue() == Color.RAINBOW) {
                int[] rainbow = ColorUtil.getRainbow(5, 0.1f * (float)i);
                hexColor = ColorUtil.RGBtoHex(rainbow[0], rainbow[1], rainbow[2]);
            } else {
                hexColor = this.color.getCurrentValue() == Color.CATEGORY ? module.getModuleCategory().getColor() : ColorUtil.RGBtoHex(this.staticColor.getRed(), this.staticColor.getGreen(), this.staticColor.getBlue());
            }
            int s = ModuleList.generateRandomIntIntRange(1, (int)this.speed.getValue());
            if (s == 1 && this.glicheffect.getValue()) {
                int m = module.getName().length();
                int n = ModuleList.generateRandomIntIntRange(0, module.getName().length() - 1);
                String ser = module.getName().substring(0, n);
                int rad = ModuleList.generateRandomIntIntRange(1, 3);
                if (rad == 1) {
                    ser = ser + "&";
                } else if (rad == 2) {
                    ser = ser + "%";
                } else if (rad == 3) {
                    ser = ser + "$";
                }
                ser = ser + module.getName().substring(n + 1, m);
                this.fr.drawStringWithShadow(ser, (float)(sr.getScaledWidth() - this.fr.getStringWidth(module.getName()) - 4), (float)(4 + i * this.fr.FONT_HEIGHT), hexColor);
            } else {
                this.fr.drawStringWithShadow(module.getName(), (float)(sr.getScaledWidth() - this.fr.getStringWidth(module.getName()) - 4), (float)(4 + i * this.fr.FONT_HEIGHT), hexColor);
            }
            ++i;
        }
    }

    public void sortList() {
        this.modulesSorted = new ArrayList<Module>(bobr.getGate().moduleManager.MODULE_LIST);
        this.modulesSorted.sort((module1, module2) -> {
            if (this.fr.getStringWidth(module1.getName()) < this.fr.getStringWidth(module2.getName())) {
                return 1;
            }
            if (this.fr.getStringWidth(module1.getName()) > this.fr.getStringWidth(module2.getName())) {
                return -1;
            }
            return 0;
        });
    }

    public static enum Color {
        STATIC("Static"),
        CATEGORY("Category"),
        RAINBOW("Rainbow");

        private final String name;

        private Color(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}

