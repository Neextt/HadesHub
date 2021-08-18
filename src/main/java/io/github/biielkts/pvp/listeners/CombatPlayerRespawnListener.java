package io.github.biielkts.pvp.listeners;

import io.github.biielkts.pvp.PVP;
import io.github.biielkts.pvp.manager.CombatPlayerManager;
import io.github.biielkts.servers.SpawnManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class CombatPlayerRespawnListener implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        PVP combatPlayer = CombatPlayerManager.get(player.getUniqueId());

        if (combatPlayer != null) combatPlayer.end();

        Location location = SpawnManager.getSpawn();

        player.teleport(location);
    }
}
