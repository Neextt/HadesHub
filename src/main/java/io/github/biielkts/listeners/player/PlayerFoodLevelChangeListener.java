package io.github.biielkts.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerFoodLevelChangeListener implements Listener {
    @EventHandler
    public void onChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
