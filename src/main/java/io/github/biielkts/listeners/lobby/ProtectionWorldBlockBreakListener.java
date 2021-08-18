package io.github.biielkts.listeners.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProtectionWorldBlockBreakListener implements Listener {
    @EventHandler
    public void onBuild(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!this.canBuildHere(player)) {
            event.setBuild(false);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!this.canBuildHere(player)) event.setCancelled(true);
    }

    @EventHandler
    public void onChange(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().name().contains("BLOCK") && !this.canBuildHere(player)) {
            event.setCancelled(true);
            event.setUseInteractedBlock(Event.Result.DENY);
        }
    }

    private Boolean canBuildHere(Player player) {
        return player.hasPermission("hades.builder");
    }
}
