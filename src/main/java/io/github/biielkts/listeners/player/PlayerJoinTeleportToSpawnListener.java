package io.github.biielkts.listeners.player;

import io.github.biielkts.servers.HotbarItens;
import io.github.biielkts.servers.SpawnManager;
import io.github.biielkts.servers.StartManager;
import io.github.biielkts.scoreboard.manager.ScoreboardManager;
import io.github.biielkts.tagger.managers.TagsManager;
import io.github.biielkts.utils.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;

public class PlayerJoinTeleportToSpawnListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        playerInventory.setHelmet(null);
        playerInventory.setChestplate(null);
        playerInventory.setLeggings(null);
        playerInventory.setBoots(null);

        playerInventory.clear();

        player.teleport(SpawnManager.getSpawn());

        HotbarItens.giveItems(player);

        TagsManager tagsManager = StartManager.getTagsManager();

        tagsManager.sendTeams(player);

        tagsManager.setNametag(
                player.getName(),
                Helper.getPrefix(player.getName()),
                Helper.getSuffix(player.getName()),
                Helper.getRank(player.getName())
        );

        ScoreboardManager.createScoreboard(player);
    }
    }
