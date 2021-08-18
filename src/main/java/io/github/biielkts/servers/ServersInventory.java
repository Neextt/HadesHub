package io.github.biielkts.servers;

import io.github.biielkts.servers.manager.ServerManager;
import io.github.biielkts.utils.inventory.InventoryBuilder;
import io.github.biielkts.utils.inventory.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class ServersInventory {
    public static InventoryView open(Player player) {
        InventoryBuilder inventoryBuilder = new InventoryBuilder("Modos de Jogo", 3)
                .setCancelled(true);

        ServerManager.getServers().forEach(server -> {
            inventoryBuilder.setItem(
                    server.getSlot(),
                    new Item(server.getIcon())
                            .onClick(event -> {
                                server.send(player);
                            })
            );
        });

        return inventoryBuilder.open(player);
    }
}
