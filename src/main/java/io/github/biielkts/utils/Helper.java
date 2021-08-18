package io.github.biielkts.utils;

import org.bukkit.ChatColor;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Helper {
    public static Boolean isInteger(Object o) {
        return o instanceof Integer;
    }

    public static List<String> fromArray(String... values) {
        List<String> results = new ArrayList<>();
        Collections.addAll(results, values);
        results.remove("");
        return results;
    }

    public static String[] removeFirst(String[] args) {
        List<String> out = fromArray(args);

        if (!out.isEmpty()) {
            out.remove(0);
        }
        return toArray(out);
    }

    public static String[] toArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    public static String toMessage(String[] args) {
        String out = "";

        for (String arg : args) {
            out += arg + " ";
        }

        return out.trim();
    }

    public static String getPrefix(String username) {
        return ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(username).getPrefix());
    }

    public static String getSuffix(String username) {
        return ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(username).getSuffix());
    }

    public static Integer getRank(String username) {
        return PermissionsEx.getUser(username).getRank(Helper.getGroup(username));
    }

    public static String getGroup(String username) {
        return PermissionsEx.getUser(username).getGroupNames()[0];
    }
}
