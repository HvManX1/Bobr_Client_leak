/*
 * Decompiled with CFR 0.150.
 */
package ru.terrar.bobr.managers;

import java.util.ArrayList;
import java.util.List;
import ru.terrar.bobr.util.ChatUtil;

public class FriendManager {
    public static List<String> FRIENDS = new ArrayList<String>();

    public static void toggleFriend(String nick) {
        if (FriendManager.isFriend(nick)) {
            FRIENDS.remove(nick);
            ChatUtil.clientMessage(nick + " is removed from Friend list.");
        } else {
            FRIENDS.add(nick);
            ChatUtil.clientMessage(nick + " is add in Friend list.");
        }
    }

    public static boolean isFriend(String nick) {
        FRIENDS.add("zTerrarxd_");
        for (String friend : FRIENDS) {
            if (!friend.equalsIgnoreCase(nick)) continue;
            return true;
        }
        return false;
    }
}

