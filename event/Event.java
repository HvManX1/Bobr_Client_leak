//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Basic\Desktop\projects\java\deof\Minecraft-Deobfuscator3000-1.2.3\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package event;

import event.ArrayHelper;
import event.Data;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.client.Minecraft;
import ru.internali.CatClient;

public abstract class Event {
    private State state = State.PRE;
    private boolean cancelled;
    private final float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public State getEventState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Event call() {
        this.cancelled = false;
        Event.call(this);
        return this;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    private static void call(Event event) {
        ArrayHelper<Data> dataList = CatClient.EVENT_MANAGER.get(event.getClass());
        if (dataList != null) {
            for (Data data : dataList) {
                try {
                    data.target.invoke(data.source, event);
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static enum State {
        PRE,
        POST;

    }
}

