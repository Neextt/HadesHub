package io.github.biielkts.servers;

import io.github.biielkts.utils.inventory.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@RequiredArgsConstructor
@Getter
public enum HotbarItens {
    COMBAT(
            new Item(Material.DIAMOND_SWORD)
                    .hideAttributes()
                    .name("§cPVP")
                    .build(),
            7,
            true
    ),
    NO_COMBAT(
            new Item(Material.BARRIER)
                    .hideAttributes()
                    .name("§cSair do modo batalha")
                    .build(),
            8,
            false
    ),
    SERVERS(
            new Item(Material.COMPASS)
                    .name("§bModos de Jogo")
                    .build(),
            4,
            true
    ),
    SHOP(
            new Item(Material.SKULL_ITEM)
                    .data(3)
                    .name("§bAcessar loja")
                    .build(),
            1,
            true
    );

    private final ItemStack icon;
    private final Integer slot;
    private final Boolean playable;

    public static void giveItems(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        for (HotbarItens lobbyItem : HotbarItens.values()) {
            if (!lobbyItem.isPlayable()) continue;

            ItemStack icon = lobbyItem.getIcon().clone();

            Item item = new Item(icon);

            if (lobbyItem == HotbarItens.SHOP) item.owner(player.getName());

            playerInventory.setItem(
                    lobbyItem.getSlot(),
                    item.build()
            );
        }
    }

    public static HotbarItens getLobbyItem(ItemStack itemStack) {
        if (itemStack.getType() == Material.SKULL_ITEM) return HotbarItens.SHOP;

        for (HotbarItens lobbyItem : HotbarItens.values()) {
            if (lobbyItem.getIcon().isSimilar(itemStack)) return lobbyItem;
        }
        return null;
    }

    public Boolean isPlayable() {
        return this.playable;
    }
}
