package io.github.biielkts.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] label) {
        if (sender.hasPermission("redehades.fly") && sender instanceof Player) {
            Player player = (Player) sender;
            Boolean value = player.isFlying();

            player.setAllowFlight(!value);
            player.setFlying(!value);

            player.sendMessage("§aModo voo acaba de ser " + (!value ? "Ativado" : "Desativado") + ".");
            return true;
        } else {
            sender.sendMessage("§cVocê não possui permissão para executar este comando.");
            return true;
        }
    }
}
