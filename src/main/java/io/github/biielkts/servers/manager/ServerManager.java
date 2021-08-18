package io.github.biielkts.servers.manager;

import com.google.common.collect.Lists;
import io.github.biielkts.Main;
import io.github.biielkts.servers.data.Server;
import io.github.biielkts.utils.inventory.Item;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class ServerManager {
    private static List<Server> servers = Lists.newArrayList();
    private static Server network = null;

    public ServerManager() {
        ConfigurationSection configurationSection = Main.getInstance().getConfig().getConfigurationSection("servers");

        String networkInfo = Main.getInstance().getConfig().getString("settings.network");

        String[] networkData = networkInfo.split(":");

        String networkAddress = networkData[0];
        Integer networkPort = Integer.parseInt(networkData[1]);

        ServerManager.network = new Server(
                "network",
                null,
                networkAddress,
                null,
                false,
                0,
                0,
                0,
                networkPort
        );

        configurationSection.getKeys(false).forEach(key -> {
            String name = configurationSection.getString(key + ".name");
            String address = configurationSection.getString(key + ".address");
            String displayName = configurationSection.getString(key + ".icon.display_name");
            Integer slot = configurationSection.getInt(key + ".slot");
            Integer slots = configurationSection.getInt(key + ".slots");
            Integer port = configurationSection.getInt(key + ".port");
            Integer id = configurationSection.getInt(key + ".icon.id");
            Integer data = configurationSection.getInt(key + ".icon.data");
            List<String> lore = configurationSection.getStringList(key + ".icon.lore");

            Server server = new Server(
                    key,
                    name,
                    address,
                    new Item(Material.getMaterial(id))
                            .data(data)
                            .name(displayName)
                            .lore(lore)
                            .build(),
                    false,
                    slots,
                    0,
                    slot,
                    port
            );

            ServerManager.servers.add(server);
        });
    }

    public static List<Server> getServers() {
        return ServerManager.servers;
    }

    public static Server getServer(String name) {
        if (name.equalsIgnoreCase("network")) return ServerManager.network;

        return ServerManager.servers
                .stream()
                .filter(server -> server.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static void refresh() {
        if (ServerManager.network != null) ServerManager.network.refresh();

        ServerManager.servers.forEach(Server::refresh);
    }

    public static Server getNetwork() {
        return ServerManager.network;
    }
}
