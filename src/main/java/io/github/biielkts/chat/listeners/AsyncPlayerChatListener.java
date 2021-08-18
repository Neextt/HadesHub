package io.github.biielkts.chat.listeners;

import io.github.biielkts.Main;
import io.github.biielkts.chat.ChatManager;
import io.github.biielkts.utils.Helper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!message.startsWith("/")) event.setCancelled(true);

        ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("settings.chat");

        String permission = section.getString("permission");
        String permission_cooldown = section.getString("permission_cooldown");
        String format = section.getString("format");
        Integer cooldown = section.getInt("delay");

        if (cooldown != 0 && !player.hasPermission(permission_cooldown) && ChatManager.hasCooldown(player.getUniqueId())) {
            player.sendMessage("§cAguarde " + ChatManager.getFormatted(player.getUniqueId()) + " para falar no chat novamente.");
            return;
        }

        if (!player.hasPermission(permission)) return;

        String message1 = StringUtils.replaceEach(
                format,
                new String[]{
                        "{prefix}",
                        "{display}",
                        "{message}"
                },
                new String[]{
                        Helper.getPrefix(player.getName()),
                        player.getName(),
                        message
                }
        );

        Bukkit.getOnlinePlayers().forEach(player1 -> player1.sendMessage(message1));

        if (!player.hasPermission(permission_cooldown)) ChatManager.setCooldown(player.getUniqueId(), cooldown);
    }
}
