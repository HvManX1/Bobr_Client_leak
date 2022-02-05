/*
 * Decompiled with CFR 0.150.
 */
package ru.internali.utils;

import java.util.List;
import ru.internali.utils.BasicTask;

public interface TaskFactory<T extends BasicTask> {
    public void removeTask(String var1);

    public void removeTask(T var1);

    public List<T> getTasks();
}

