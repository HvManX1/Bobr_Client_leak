//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.util.Timer
 */
package me.thef1xer.gateclient.modules.render;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.Timer;

public class ReflectionHelper {
    public static MethodHandles.Lookup lookup;

    public static Field getField(Class class_, String ... arrstring) {
        for (Field field : class_.getDeclaredFields()) {
            field.setAccessible(true);
            for (String string : arrstring) {
                if (!field.getName().equals(string)) continue;
                return field;
            }
        }
        return null;
    }

    public static MethodHandle getLookupMethod(Class class_, MethodType methodType, String ... arrstring) throws Throwable {
        for (String string : arrstring) {
            MethodHandle methodHandle = ReflectionHelper.lookup().findVirtual(class_, string, methodType);
            if (methodHandle == null) continue;
            return methodHandle;
        }
        return null;
    }

    public static Method getMethod(Class class_, String ... arrstring) {
        for (Method method : class_.getDeclaredMethods()) {
            method.setAccessible(true);
            for (String string : arrstring) {
                if (!method.getName().equals(string)) continue;
                return method;
            }
        }
        return null;
    }

    public static float getPartialTicks() throws IllegalAccessException, Throwable {
        Field field = ReflectionHelper.getField(Minecraft.class, "timer", "timer", "Y");
        Timer timer = ReflectionHelper.lookup().unreflectGetter(field).invoke(Minecraft.getMinecraft());
        return timer.renderPartialTicks;
    }

    public static double getRenderPosX() throws Throwable, IllegalAccessException {
        return ReflectionHelper.lookup().unreflectGetter(ReflectionHelper.getField(RenderManager.class, "renderPosX", "renderPosX", "o")).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosY() throws IllegalAccessException, Throwable {
        return ReflectionHelper.lookup().unreflectGetter(ReflectionHelper.getField(RenderManager.class, "renderPosY", "renderPosY", "p")).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static double getRenderPosZ() throws IllegalAccessException, Throwable {
        return ReflectionHelper.lookup().unreflectGetter(ReflectionHelper.getField(RenderManager.class, "renderPosZ", "renderPosZ", "q")).invoke(Minecraft.getMinecraft().getRenderManager());
    }

    public static void hookField(Field field, Object object, Object object2) throws IllegalAccessException, Throwable {
        ReflectionHelper.lookup().unreflectSetter(field).invoke(object, object2);
    }

    public static Object invoke(Method method, Object ... arrobject) throws Throwable {
        return ReflectionHelper.lookup().unreflect(method).invoke(arrobject);
    }

    public static MethodHandles.Lookup lookup() {
        return lookup;
    }

    public static MethodHandle unreflect(Method method) throws IllegalAccessException {
        return ReflectionHelper.lookup().unreflect(method);
    }
}

