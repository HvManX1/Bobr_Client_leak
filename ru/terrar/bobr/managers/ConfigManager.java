/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package ru.terrar.bobr.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import ru.terrar.bobr.bobr;
import ru.terrar.bobr.util.DirectoryUtil;

public class ConfigManager {
    public File configFile = new File(DirectoryUtil.GATE_FOLDER, "config.json");

    public void init() {
        DirectoryUtil.GATE_FOLDER.mkdir();
        DirectoryUtil.PRESET_FOLDER.mkdir();
        this.load();
    }

    public void save() {
        JsonObject config = new JsonObject();
        config.addProperty("Active Config", bobr.getGate().presetManager.getActivePreset() != null ? bobr.getGate().presetManager.getActivePreset().toString() : new File(DirectoryUtil.PRESET_FOLDER, "default.json").toString());
        config.addProperty("Prefix", bobr.getGate().commandManager.getPrefix());
        try {
            FileWriter writer = new FileWriter(this.configFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson((JsonElement)config));
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        bobr.getGate().presetManager.updatePresetList();
        JsonParser parser = new JsonParser();
        if (!this.configFile.exists()) {
            this.save();
        }
        try {
            JsonObject config = parser.parse((Reader)new FileReader(this.configFile)).getAsJsonObject();
            for (Map.Entry entry : config.entrySet()) {
                String key = (String)entry.getKey();
                JsonElement val = (JsonElement)entry.getValue();
                if (key.equals("Active Config")) {
                    bobr.getGate().presetManager.setActivePreset(new File(val.getAsString()));
                    continue;
                }
                if (!key.equals("Prefix")) continue;
                bobr.getGate().commandManager.setPrefix(val.getAsString());
            }
            this.save();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.save();
    }
}

