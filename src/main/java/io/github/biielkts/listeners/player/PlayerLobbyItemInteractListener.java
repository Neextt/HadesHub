package io.github.biielkts.listeners.player;

import io.github.biielkts.Main;
import io.github.biielkts.servers.HotbarItens;
import io.github.biielkts.servers.ServersInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerLobbyItemInteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack itemStack = player.getItemInHand();

        if (itemStack != null) {
            HotbarItens lobbyItem = HotbarItens.getLobbyItem(itemStack);

            if (lobbyItem == null ) return;

            String url = Main.getInstance().getConfig().getString("settings.shop_url");

            if (lobbyItem == HotbarItens.SHOP) player.sendMessage("\n§bObtenha vantagens acessando nosso site: §n" + url + "\n");

            if (lobbyItem == HotbarItens.SERVERS) ServersInventory.open(player);
        }
    }
}
