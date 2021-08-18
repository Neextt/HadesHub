package io.github.biielkts.scoreboard.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.biielkts.Main;
import io.github.biielkts.scoreboard.Scoreboard;
import io.github.biielkts.servers.data.Server;
import io.github.biielkts.servers.manager.ServerManager;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

public class ScoreboardManager {
    private static HashMap<UUID, Scoreboard> scoreboards = Maps.newHashMap();
    private static Queue<Player> players = Lists.newLinkedList();

    public static Scoreboard createScoreboard(Player player) {
        ConfigurationSection configurationSection = Main.getInstance().getConfig().getConfigurationSection("settings.scoreboard");

        String displayName = configurationSection.getString("titles");

        Scoreboard scoreboard = new Scoreboard(player);

        scoreboard.setTitle(displayName);

        List<String> lines = ScoreboardManager.getLines();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            scoreboard.addLine(
                    (i+1),
                    line
            );
        }

        String footer = configurationSection.getString("type");

        scoreboard.addLine(
                0,
                footer
        );

        scoreboard.apply();

        ScoreboardManager.scoreboards.put(
                player.getUniqueId(),
                scoreboard
        );

        return scoreboard;
    }

    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = ScoreboardManager.scoreboards.getOrDefault(
                player.getUniqueId(),
                ScoreboardManager.createScoreboard(player)
        );

        ScoreboardManager.setLines(scoreboard);
    }

    private static void setLines(Scoreboard scoreboard) {
        ConfigurationSection configurationSection = Main.getInstance().getConfig().getConfigurationSection("settings.scoreboard");

        List<String> lines = ScoreboardManager.getLines();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            scoreboard.updateLine(
                    (i+1),
                    line
            );
        }

        String footer = configurationSection.getString("footer");

        scoreboard.updateLine(
                0,
                footer
        );
    }

    public static void refresh() {
        if (ScoreboardManager.players.isEmpty()) players.addAll(Bukkit.getOnlinePlayers());

        Player player = ScoreboardManager.players.poll();

        if (player != null && player.isOnline()) ScoreboardManager.updateScoreboard(player);
    }

    private static List<String> getLines() {
        List<String> lines = Lists.newArrayList();

        ConfigurationSection configurationSection = Main.getInstance().getConfig().getConfigurationSection("settings.scoreboard");

        List<String> lines1 = configurationSection.getStringList("lines");

        lines1.forEach(line -> {
            if (line.contains("::")) {
                String[] parts = line.split("\\$\\{");

                String serverName = line.split("\\$\\{")[1].split("}")[0].split("::")[0];

                Server server = ServerManager.getServer(serverName);

                if (server != null) {
                    line = StringUtils.replaceEach(
                            line,
                            new String[]{
                                    "${" + serverName + "::online}"
                            },
                            new String[]{
                                    (
                                            server.isOnline()
                                            ?
                                                    server.getPlayerCount().toString()
                                            :
                                                    "§cOffline"
                                    )
                            }
                    );
                }
            }

            lines.add(line);
        });

        return lines;
    }
}
