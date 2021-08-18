package io.github.biielkts.cmd;

import io.github.biielkts.Main;
import io.github.biielkts.servers.SpawnManager;
import io.github.biielkts.utils.LocationSerialize;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] label) {
        if (sender.hasPermission("hades.setspawn") && sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation();

            String serializedLocation = LocationSerialize.toString(location);

            Main.getInstance().getConfig().set("settings.spawn", serializedLocation);
            Main.getInstance().saveConfig();

            SpawnManager.setSpawn(location);

            player.sendMessage("§aA localização do Spawn foi setada com sucesso.");
            return true;
        } else {
            sender.sendMessage("§cVocê não possui permissão para executar este comando.");
            return true;
        }
    }
}
