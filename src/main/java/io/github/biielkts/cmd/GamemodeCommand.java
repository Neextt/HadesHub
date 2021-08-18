package io.github.biielkts.cmd;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.hasPermission("redehades.gamemode") && sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                GameMode gameMode = this.matchGameMode(args[0]);

                player.setGameMode(gameMode);
                player.sendMessage("§eModo de jogo atualizado para " + gameMode.name() + ".");
                return true;
            } else if (args.length == 2) {
                String username = args[0];
                GameMode gameMode = this.matchGameMode(args[1]);

                Player target = Bukkit.getPlayer(username);

                if (target == null) {
                    player.sendMessage("§cEste usuário não está online.");
                    return true;
                }

                target.setGameMode(gameMode);
                player.sendMessage("§eModo de jogo para " + target.getName() + " atualizado para " + gameMode.name() + ".");
                return true;
            } else {
                player.sendMessage("§cUtilize /gamemode <modo>.");
                return true;
            }
        } else {
            sender.sendMessage("§cVocê não possui permissão para executar este comando");
            return true;
        }
    }

    private GameMode matchGameMode(String value){
        value = value.toLowerCase();
        if (value.startsWith("0") || value.startsWith("su")){
            return GameMode.SURVIVAL;
        } else if (value.startsWith("1") || value.startsWith("c")){
            return GameMode.CREATIVE;
        } else if (value.startsWith("0") || value.startsWith("a")){
            return GameMode.ADVENTURE;
        } else if (value.startsWith("3") || value.startsWith("sp") || value.startsWith("e")){
            return GameMode.SPECTATOR;
        } else {
            return GameMode.SURVIVAL;
        }
    }
}
