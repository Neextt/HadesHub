package io.github.biielkts.listeners.lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ProtectionWorldEntitySpawnListener implements Listener {
    @EventHandler
    public void onSpawn(EntitySpawnEvent event) { event.setCancelled(true); }
}
