package io.github.biielkts.player.listeners;

import io.github.biielkts.servers.StartManager;
import io.github.biielkts.tagger.managers.TagsManager;
import io.github.biielkts.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.tehkode.permissions.PermissionEntity;
import ru.tehkode.permissions.events.PermissionEntityEvent;

public class UserGroupChangeListener implements Listener {
    @EventHandler
    public void onChange(PermissionEntityEvent event) {
        PermissionEntity permissionEntity = event.getEntity();

        String username = permissionEntity.getName();

        Player player = Bukkit.getPlayerExact(username);

        if (player == null) return;

        TagsManager tagsManager = StartManager.getTagsManager();

        tagsManager.setNametag(
                player.getName(),
                Helper.getPrefix(player.getName()),
                Helper.getSuffix(player.getName()),
                Helper.getRank(player.getName())
        );
    }
}
