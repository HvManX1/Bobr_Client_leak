/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.reflect.ClassPath
 *  com.google.common.reflect.ClassPath$ClassInfo
 */
package ru.internali.module;

import com.google.common.reflect.ClassPath;
import java.util.ArrayList;
import ru.internali.CatClient;
import ru.internali.module.Category;
import ru.internali.module.HUD.ArmorHUD;
import ru.internali.module.HUD.ClickGUI;
import ru.internali.module.HUD.HUD;
import ru.internali.module.HUD.Notifications;
import ru.internali.module.HUD.TargetHud;
import ru.internali.module.HUD.cartridgesHUD;
import ru.internali.module.Module;
import ru.internali.module.combat.AimPistol;
import ru.internali.module.combat.AntiBot;
import ru.internali.module.combat.TriggerBot;
import ru.internali.module.combat.Velocity;
import ru.internali.module.combat.aimAssist;
import ru.internali.module.misc.MCF;
import ru.internali.module.misc.SelfDestruct;
import ru.internali.module.movement.GuiWalk;
import ru.internali.module.movement.Speed;
import ru.internali.module.movement.Sprint;
import ru.internali.module.movement.strafe;
import ru.internali.module.player.FreeCum;
import ru.internali.module.render.Chams;
import ru.internali.module.render.ESP;
import ru.internali.module.render.FullBright;
import ru.internali.module.render.NameTags;
import ru.internali.module.render.NoHurtCum;
import ru.internali.module.render.ShkafRender;
import ru.internali.module.render.Tracers;
import ru.internali.module.render.ViewModel;
import ru.internali.module.render.WallHack;

public class ModuleManager {
    public ArrayList<Module> modules = new ArrayList();

    public ModuleManager() {
        this.modules.clear();
        this.modules.add(new ClickGUI());
        this.modules.add(new HUD());
        this.modules.add(new Sprint());
        this.modules.add(new Velocity());
        this.modules.add(new AntiBot());
        this.modules.add(new ESP());
        this.modules.add(new Tracers());
        this.modules.add(new GuiWalk());
        this.modules.add(new FullBright());
        this.modules.add(new NameTags());
        this.modules.add(new TriggerBot());
        this.modules.add(new aimAssist());
        this.modules.add(new SelfDestruct());
        this.modules.add(new strafe());
        this.modules.add(new Speed());
        this.modules.add(new WallHack());
        this.modules.add(new NoHurtCum());
        this.modules.add(new ArmorHUD());
        this.modules.add(new cartridgesHUD());
        this.modules.add(new MCF());
        this.modules.add(new ShkafRender());
        this.modules.add(new AimPistol());
        this.modules.add(new ViewModel());
        this.modules.add(new Chams());
        this.modules.add(new TargetHud());
        this.modules.add(new Notifications());
        this.modules.add(new FreeCum());
        for (Module m : this.modules) {
            CatClient.EVENT_MANAGER.register(m);
        }
    }

    public static ArrayList<Class<?>> getClasses(String packageName) {
        ArrayList classes = new ArrayList();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            for (ClassPath.ClassInfo info : ClassPath.from((ClassLoader)loader).getAllClasses()) {
                if (!info.getName().startsWith(packageName)) continue;
                classes.add(info.load());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    public Module getModule(String name) {
        for (Module m : this.modules) {
            if (!m.getName().equalsIgnoreCase(name)) continue;
            return m;
        }
        return null;
    }

    public ArrayList<Module> getModuleList() {
        return this.modules;
    }

    public ArrayList<Module> getModulesInCategory(Category c) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : this.modules) {
            if (m.getCategory() != c) continue;
            mods.add(m);
        }
        return mods;
    }
}

