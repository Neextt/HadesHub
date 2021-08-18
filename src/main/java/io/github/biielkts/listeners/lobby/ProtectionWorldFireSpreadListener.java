package io.github.biielkts.listeners.lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

public class ProtectionWorldFireSpreadListener implements Listener {
    @EventHandler
    public void onFireSpread(BlockSpreadEvent event) { event.setCancelled(true);  }
}
