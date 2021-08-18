package io.github.biielkts.chat;

import com.google.common.collect.Maps;
import io.github.biielkts.utils.TimeFormatter;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ChatManager {
    private static HashMap<UUID, Long> cooldown = Maps.newHashMap();

    public static String getFormatted(UUID uuid) {
        return TimeFormatter.format(ChatManager.cooldown.get(uuid)-System.currentTimeMillis());
    }

    public static Long setCooldown(UUID uuid, Integer cooldown) {
        return ChatManager.cooldown.put(uuid, (TimeUnit.SECONDS.toMillis(cooldown)+System.currentTimeMillis()));
    }

    public static Boolean hasCooldown(UUID uuid) {
        return ChatManager.cooldown.containsKey(uuid) && ChatManager.cooldown.get(uuid) > System.currentTimeMillis();
    }
}
