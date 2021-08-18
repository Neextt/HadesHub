package io.github.biielkts.pvp.manager;

import com.google.common.collect.Maps;
import io.github.biielkts.pvp.PVP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class CombatPlayerManager {
    private static HashMap<UUID, PVP> battling = Maps.newHashMap();

    public static PVP remove(UUID uuid) {
        return CombatPlayerManager.battling.remove(uuid);
    }

    public static PVP add(UUID uuid) {
        return CombatPlayerManager.battling.put(uuid, new PVP());
    }

    public static PVP get(UUID uuid) {
        return CombatPlayerManager.battling.get(uuid);
    }

    public static Boolean isBattling(Player player) {
        return CombatPlayerManager.battling.containsKey(player.getUniqueId());
    }

    public static Player getPlayer(PVP combatPlayer) {
        for (Map.Entry<UUID, PVP> entry : CombatPlayerManager.battling.entrySet()) {
            UUID uuid = entry.getKey();
            PVP combatPlayer1 = entry.getValue();

            Player player = Bukkit.getPlayer(uuid);

            if (combatPlayer.equals(combatPlayer1)) return player;
        }

        return null;
    }

    public static Collection<Player> getPlayers() {
        Collection<Player> players = Collections.emptyList();

        CombatPlayerManager.battling.keySet().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            players.add(player);
        });

        return players;
    }
}
