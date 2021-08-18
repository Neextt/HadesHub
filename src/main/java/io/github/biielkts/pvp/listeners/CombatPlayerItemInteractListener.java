package io.github.biielkts.pvp.listeners;

import io.github.biielkts.pvp.PVP;
import io.github.biielkts.pvp.manager.CombatPlayerManager;
import io.github.biielkts.servers.HotbarItens;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CombatPlayerItemInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack itemStack = player.getItemInHand();

        if (itemStack == null) return;

        Action action = event.getAction();

        if (action.name().contains("_RIGHT")) return;

        HotbarItens lobbyItem = HotbarItens.COMBAT;
        ItemStack icon = lobbyItem.getIcon();

        HotbarItens lobbyItem1 = HotbarItens.NO_COMBAT;
        ItemStack icon1 = lobbyItem1.getIcon();

        if (icon1.isSimilar(itemStack) && CombatPlayerManager.isBattling(player)) {
            PVP combatPlayer = CombatPlayerManager.get(player.getUniqueId());

            combatPlayer.end();
            return;
        }

        if (icon.isSimilar(itemStack)) {
            CombatPlayerManager.add(player.getUniqueId());

            PVP combatPlayer = CombatPlayerManager.get(player.getUniqueId());

            combatPlayer.start();

            PlayerInventory playerInventory = player.getInventory();

            playerInventory.setItem(
                    lobbyItem1.getSlot(),
                    lobbyItem1.getIcon()
            );
        }
    }
}
