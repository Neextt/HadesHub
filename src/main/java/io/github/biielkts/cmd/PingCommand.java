package io.github.biielkts.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] label) {
        if (sender.hasPermission("redehades.ping") && sender instanceof Player) {
            Player player = (Player) sender;
            CraftPlayer craftPlayer = (CraftPlayer) player;

            sender.sendMessage("§aSeu ping atual é de §f" + craftPlayer.getHandle().ping + "ms§a.");
            return true;
        } else {
            sender.sendMessage("§cVocê não possui permissão para executar este comando");
            return true;
        }
    }
}
