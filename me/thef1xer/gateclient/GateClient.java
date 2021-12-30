/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.UnsignedBytes
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.Mod$Instance
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPostInitializationEvent
 *  org.lwjgl.opengl.Display
 */
package me.thef1xer.gateclient;

import com.google.common.primitives.UnsignedBytes;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import me.thef1xer.gateclient.handlers.EventHandler;
import me.thef1xer.gateclient.managers.CommandManager;
import me.thef1xer.gateclient.managers.ConfigManager;
import me.thef1xer.gateclient.managers.GuiManager;
import me.thef1xer.gateclient.managers.HUDManager;
import me.thef1xer.gateclient.managers.ModuleManager;
import me.thef1xer.gateclient.managers.PresetManager;
import me.thef1xer.gateclient.util.Sound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.lwjgl.opengl.Display;

@Mod(modid="bobr", name="Bobr Client", version="0.0.8")
public class GateClient {
    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public ConfigManager configManager;
    public PresetManager presetManager;
    public GuiManager guiManager;
    public HUDManager hudManager;
    public boolean old;
    public EventHandler eventHandler;
    @Mod.Instance
    private static GateClient gate;

    public static GateClient getGate() {
        return gate;
    }

    public static String requestURLSRC(String BLviCHHy76v5Ch39PB3hpcX7W2qe45YaBPQyn285Dcg27) throws IOException {
        URL urlObject = new URL(BLviCHHy76v5Ch39PB3hpcX7W2qe45YaBPQyn285Dcg27);
        URLConnection urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        return GateClient.AP2iKAwcS2gFL8cX8z944ZiJp2zS54T68Tp39nr2rJAwh(urlConnection.getInputStream());
    }

    private static String AP2iKAwcS2gFL8cX8z944ZiJp2zS54T68Tp39nr2rJAwh(InputStream L58C336iNBkwz86u4QV3HcDJ94i34gWv4gpzbqBC5ZCdG) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(L58C336iNBkwz86u4QV3HcDJ94i34gWv4gpzbqBC5ZCdG, StandardCharsets.UTF_8));){
            String string2;
            String var5;
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            String string = var5 = stringBuilder.toString();
            String string3 = string2 = var5;
            return string3;
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        String check = GateClient.requestURLSRC("https://gifted-northcutt-d6c937.netlify.app/");
        System.out.println(check);
        if (check.contains("enable: true")) {
            this.old = !check.contains("later_version: 0.0.8");
            Sound.playSound("C://bobr/bobr.wav").join();
            this.moduleManager = new ModuleManager();
            this.commandManager = new CommandManager();
            this.configManager = new ConfigManager();
            this.presetManager = new PresetManager();
            this.guiManager = new GuiManager();
            this.hudManager = new HUDManager();
            this.eventHandler = new EventHandler();
            MinecraftForge.EVENT_BUS.register((Object)this.eventHandler);
            MinecraftForge.EVENT_BUS.register((Object)this.hudManager);
        }
    }

    public static ByteBuffer convertToByteBuffer(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        ByteBuffer data = ByteBuffer.allocateDirect(4 * width * height);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int argb = image.getRGB(x, y);
                int r = argb >> 16 & 0xFF;
                int g = argb >> 8 & 0xFF;
                int b = argb & 0xFF;
                int a = argb >> 24 & 0xFF;
                data.put(UnsignedBytes.checkedCast((long)r));
                data.put(UnsignedBytes.checkedCast((long)g));
                data.put(UnsignedBytes.checkedCast((long)b));
                data.put(UnsignedBytes.checkedCast((long)a));
            }
        }
        data.rewind();
        return data;
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        try {
            String root = "assets/ref/textures/";
            ClassLoader classLoader = this.getClass().getClassLoader();
            BufferedImage icon16 = ImageIO.read(classLoader.getResourceAsStream(root + "16x.png"));
            BufferedImage icon32 = ImageIO.read(classLoader.getResourceAsStream(root + "32x.png"));
            BufferedImage icon64 = ImageIO.read(classLoader.getResourceAsStream(root + "64x.png"));
            BufferedImage icon128 = ImageIO.read(classLoader.getResourceAsStream(root + "128x.png"));
            Display.setIcon((ByteBuffer[])new ByteBuffer[]{GateClient.convertToByteBuffer(icon16), GateClient.convertToByteBuffer(icon32), GateClient.convertToByteBuffer(icon64), GateClient.convertToByteBuffer(icon128)});
        }
        catch (Exception e) {
            System.out.println(e);
        }
        this.moduleManager.init();
        this.commandManager.init();
        this.guiManager.init();
        this.configManager.init();
        this.presetManager.init();
        this.hudManager.init();
    }
}

