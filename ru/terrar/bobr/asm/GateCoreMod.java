/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin$MCVersion
 *  net.minecraftforge.fml.relauncher.IFMLLoadingPlugin$TransformerExclusions
 */
package ru.terrar.bobr.asm;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion(value="1.12.2")
@IFMLLoadingPlugin.TransformerExclusions(value={"me.thef1xer.gateclient.asm"})
public class GateCoreMod
implements IFMLLoadingPlugin {
    public String[] getASMTransformerClass() {
        return new String[]{"me.thef1xer.gateclient.asm.GateClassTransformer"};
    }

    public String getModContainerClass() {
        return null;
    }

    @Nullable
    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {
    }

    public String getAccessTransformerClass() {
        return null;
    }
}

