package io.github.biielkts.pvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CombatPlayerDeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        event.getDrops().clear();

        event.setDroppedExp(0);

        event.setDeathMessage(null);

        if (killer == null) return;

        event.setDeathMessage("§3" + player.getName() + " §cfoi morto por §3" + killer.getName());
    }
}
