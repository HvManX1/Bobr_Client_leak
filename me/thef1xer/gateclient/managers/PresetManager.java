/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 */
package me.thef1xer.gateclient.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.thef1xer.gateclient.GateClient;
import me.thef1xer.gateclient.modules.Module;
import me.thef1xer.gateclient.settings.Setting;
import me.thef1xer.gateclient.settings.impl.BooleanSetting;
import me.thef1xer.gateclient.settings.impl.EnumSetting;
import me.thef1xer.gateclient.settings.impl.FloatSetting;
import me.thef1xer.gateclient.settings.impl.RGBSetting;
import me.thef1xer.gateclient.util.DirectoryUtil;

public class PresetManager {
    public final List<File> PRESET_LIST = new ArrayList<File>();
    private File activePreset;
    private boolean autoSave;

    public void init() {
        this.updatePresetList();
        this.loadActivePreset();
    }

    public void setActivePreset(File activePreset) {
        this.activePreset = activePreset;
    }

    public File getActivePreset() {
        return this.activePreset;
    }

    public void setAutoSave(boolean autoSave) {
        if (this.autoSave != autoSave) {
            this.autoSave = autoSave;
            this.saveActivePreset();
        }
    }

    public boolean isAutoSave() {
        return this.autoSave;
    }

    public void updatePresetList() {
        this.PRESET_LIST.clear();
        if (DirectoryUtil.PRESET_FOLDER.listFiles() != null) {
            for (File file : DirectoryUtil.PRESET_FOLDER.listFiles()) {
                if (file.isDirectory() || !DirectoryUtil.isJson(file)) continue;
                this.PRESET_LIST.add(file);
            }
        }
    }

    public void loadActivePreset() {
        if (!this.presetExists(this.getActivePreset())) {
            System.out.println("Preset not found");
            this.setActivePreset(new File(DirectoryUtil.PRESET_FOLDER, "default.json"));
            GateClient.getGate().configManager.save();
            if (!this.presetExists(this.getActivePreset())) {
                System.out.println("Default preset created");
                this.setAutoSave(true);
                this.saveActivePreset();
            }
        }
        JsonParser parser = new JsonParser();
        try {
            JsonObject object = parser.parse((Reader)new FileReader(this.getActivePreset())).getAsJsonObject();
            JsonElement autoSave = object.get("auto save");
            this.setAutoSave(autoSave != null ? autoSave.getAsBoolean() : true);
            JsonArray moduleArray = object.getAsJsonArray("modules");
            for (JsonElement element : moduleArray) {
                if (!(element instanceof JsonObject)) continue;
                JsonObject moduleObject = (JsonObject)element;
                Set moduleSet = moduleObject.entrySet();
                for (Module module : GateClient.getGate().moduleManager.MODULE_LIST) {
                    if (!this.contains(moduleSet, "name", (JsonElement)new JsonPrimitive(module.getName()))) continue;
                    for (Map.Entry value : moduleSet) {
                        String key = (String)value.getKey();
                        JsonElement val = (JsonElement)value.getValue();
                        if (key.equals("enabled")) {
                            module.setEnabled(val.getAsBoolean());
                            continue;
                        }
                        if (key.equals("keybind")) {
                            module.setKeyBind(val.getAsInt());
                            continue;
                        }
                        if (!key.equals("settings")) continue;
                        for (JsonElement element1 : val.getAsJsonArray()) {
                            if (!(element1 instanceof JsonObject)) continue;
                            JsonObject settingObject = (JsonObject)element1;
                            Set settingSet = settingObject.entrySet();
                            for (Setting setting : module.getSettings()) {
                                if (!this.contains(settingSet, "id", (JsonElement)new JsonPrimitive(setting.getId()))) continue;
                                for (Map.Entry value1 : settingSet) {
                                    String settingKey = (String)value1.getKey();
                                    JsonElement settingVal = (JsonElement)value1.getValue();
                                    if (setting instanceof BooleanSetting) {
                                        if (!settingKey.equals("value")) continue;
                                        ((BooleanSetting)setting).setValue(settingVal.getAsBoolean());
                                        continue;
                                    }
                                    if (setting instanceof EnumSetting) {
                                        if (!settingKey.equals("value")) continue;
                                        ((EnumSetting)setting).setValueFromName(settingVal.getAsString());
                                        continue;
                                    }
                                    if (setting instanceof FloatSetting) {
                                        if (!settingKey.equals("value")) continue;
                                        ((FloatSetting)setting).setValue(settingVal.getAsFloat());
                                        continue;
                                    }
                                    if (!(setting instanceof RGBSetting)) continue;
                                    if (settingKey.equals("red")) {
                                        ((RGBSetting)setting).setRed(settingVal.getAsInt());
                                        continue;
                                    }
                                    if (settingKey.equals("green")) {
                                        ((RGBSetting)setting).setGreen(settingVal.getAsInt());
                                        continue;
                                    }
                                    if (!settingKey.equals("blue")) continue;
                                    ((RGBSetting)setting).setBlue(settingVal.getAsInt());
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Preset loaded");
        this.saveActivePreset();
    }

    public void saveActivePreset() {
        JsonObject presetJson = new JsonObject();
        presetJson.addProperty("auto save", Boolean.valueOf(this.isAutoSave()));
        JsonArray moduleArray = new JsonArray();
        for (Module module : GateClient.getGate().moduleManager.MODULE_LIST) {
            JsonObject moduleObject = new JsonObject();
            moduleObject.addProperty("name", module.getName());
            moduleObject.addProperty("enabled", Boolean.valueOf(module.isEnabled()));
            moduleObject.addProperty("keybind", (Number)module.getKeyBind());
            JsonArray settingsArray = new JsonArray();
            for (Setting setting : module.getSettings()) {
                JsonObject settingObject = new JsonObject();
                settingObject.addProperty("id", setting.getId());
                if (setting instanceof BooleanSetting) {
                    settingObject.addProperty("value", Boolean.valueOf(((BooleanSetting)setting).getValue()));
                } else if (setting instanceof RGBSetting) {
                    settingObject.addProperty("red", (Number)((RGBSetting)setting).getRed());
                    settingObject.addProperty("green", (Number)((RGBSetting)setting).getGreen());
                    settingObject.addProperty("blue", (Number)((RGBSetting)setting).getBlue());
                } else if (setting instanceof EnumSetting) {
                    settingObject.addProperty("value", ((EnumSetting)setting).getCurrentValueName());
                } else if (setting instanceof FloatSetting) {
                    settingObject.addProperty("value", (Number)Float.valueOf(((FloatSetting)setting).getValue()));
                }
                settingsArray.add((JsonElement)settingObject);
            }
            moduleObject.add("settings", (JsonElement)settingsArray);
            moduleArray.add((JsonElement)moduleObject);
        }
        presetJson.add("modules", (JsonElement)moduleArray);
        try {
            FileWriter writer = new FileWriter(this.getActivePreset());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson((JsonElement)presetJson));
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Preset saved");
    }

    public boolean createNewPreset(String path) {
        this.updatePresetList();
        for (File file : this.PRESET_LIST) {
            if (!file.getName().equalsIgnoreCase(path)) continue;
            return false;
        }
        this.setActivePreset(new File(DirectoryUtil.PRESET_FOLDER, path));
        this.setAutoSave(true);
        this.saveActivePreset();
        GateClient.getGate().configManager.save();
        return true;
    }

    public boolean removePreset(String presetName) {
        this.updatePresetList();
        for (File file : this.PRESET_LIST) {
            if (!file.getName().equalsIgnoreCase(presetName)) continue;
            return file.delete();
        }
        return false;
    }

    public void removeActivePreset() {
        this.getActivePreset().delete();
        this.updatePresetList();
        if (this.PRESET_LIST.size() != 0) {
            this.setActivePreset(this.PRESET_LIST.get(0));
            this.loadActivePreset();
        } else {
            this.setActivePreset(new File(DirectoryUtil.PRESET_FOLDER, "default.json"));
            this.setAutoSave(true);
            this.saveActivePreset();
            GateClient.getGate().configManager.save();
        }
    }

    private boolean presetExists(File preset) {
        for (File file : this.PRESET_LIST) {
            if (!file.getPath().equals(preset.getPath())) continue;
            return true;
        }
        return false;
    }

    private boolean contains(Set<Map.Entry<String, JsonElement>> set, String key, JsonElement value) {
        for (Map.Entry<String, JsonElement> entry : set) {
            if (!entry.getKey().equals(key) || !entry.getValue().equals((Object)value)) continue;
            return true;
        }
        return false;
    }
}

