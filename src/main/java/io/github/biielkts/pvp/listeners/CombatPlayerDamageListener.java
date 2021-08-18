package io.github.biielkts.pvp.listeners;

import io.github.biielkts.pvp.manager.CombatPlayerManager;
import io.github.biielkts.servers.SpawnManager;
import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CombatPlayerDamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!this.isAllowed(entity)) event.setCancelled(true);

        if (entity.getType() == EntityType.PLAYER && damager.getType() == EntityType.PLAYER) {

            if (entity.getType() != EntityType.PLAYER) return;

            Player player = (Player) entity;
            Player player1 = (Player) damager;

            if (!CombatPlayerManager.isBattling(player) || !CombatPlayerManager.isBattling(player1)) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity.getType() != EntityType.PLAYER) return;

        Player player = (Player) entity;

        Location location = player.getLocation();
        Location location1 = SpawnManager.getSpawn();

        if (location.getY() <= 0) player.teleport(location1);

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setCancelled(true);

        if (!CombatPlayerManager.isBattling(player)) event.setCancelled(true);
    }

    private Boolean isAllowed(Entity entity) { return entity instanceof Player || entity instanceof Animals; }
}
