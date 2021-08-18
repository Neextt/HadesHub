package io.github.biielkts.listeners.player;

import io.github.biielkts.servers.StartManager;
import io.github.biielkts.tagger.managers.TagsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitHideMessageListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();

        TagsManager tagsManager = StartManager.getTagsManager();

        tagsManager.reset(player.getName());
    }
}
