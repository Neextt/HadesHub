package io.github.biielkts.listeners.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ProtectionPlayerInventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();

        if (player.hasPermission("hades.builder")) return;

        if (clickType == ClickType.NUMBER_KEY || event.isShiftClick()) event.setCancelled(true);

        event.setCancelled(true);
    }
}
