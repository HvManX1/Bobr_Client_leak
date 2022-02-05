/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 */
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import ru.internali.CatClient;

@Mod(modid="optifune", version="V0.1", name="Bobr")
public class Main {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws Exception {
        CatClient.instance = new CatClient();
        CatClient.instance.init();
    }
}

