/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.text.TextFormatting
 */
package ru.internali.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.text.TextFormatting;
import ru.internali.notifications.Type;
import ru.internali.notifications.notifications;

public class FriendManager {
    public static List<String> FRIENDS = new ArrayList<String>();

    public static void toggleFriend(String nick) {
        if (FriendManager.isFriend(nick)) {
            FRIENDS.remove(nick);
            notifications.add((Object)TextFormatting.RED + nick, "Remove from Friend list", Type.OK);
        } else {
            FRIENDS.add(nick);
            notifications.add((Object)TextFormatting.GREEN + nick, "add in Friends list", Type.OK);
        }
    }

    public static boolean isFriend(String nick) {
        return FRIENDS.contains(nick);
    }
}

