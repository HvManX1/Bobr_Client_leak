//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package event;

import net.minecraft.client.Minecraft;

public class EclientEvent {
    private Era era = Era.PRE;
    private final float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();

    public Era getEra() {
        return this.era;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public static enum Era {
        PRE,
        PERI,
        POST;

    }
}

