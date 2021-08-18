package io.github.biielkts.servers;

import io.github.biielkts.Main;
import io.github.biielkts.chat.ChatManager;
import io.github.biielkts.cmd.FlyCommand;
import io.github.biielkts.cmd.GamemodeCommand;
import io.github.biielkts.cmd.PingCommand;
import io.github.biielkts.cmd.SetSpawnCommand;
import io.github.biielkts.pvp.manager.CombatPlayerManager;
import io.github.biielkts.scoreboard.manager.ScoreboardManager;
import io.github.biielkts.scoreboard.runnable.ScoreboardRefreshRunnable;
import io.github.biielkts.servers.manager.ServerManager;
import io.github.biielkts.servers.runnable.ServerRefreshRunnable;
import io.github.biielkts.tablist.TablistManager;
import io.github.biielkts.tagger.managers.TagsManager;
import io.github.biielkts.utils.ClassGetter;
import io.github.biielkts.utils.inventory.InventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class StartManager {
    private static TagsManager tagsManager;

    public StartManager() {
        new ListenerManager();
        new CommandManager();

        new ChatManager();
        new SpawnManager();
        new TablistManager();
        new ServerManager();
        new CombatPlayerManager();
        new ScoreboardManager();
        new RunnableManager();

        StartManager.tagsManager = new TagsManager();
    }

    public static TagsManager getTagsManager() {
        return StartManager.tagsManager;
    }
}
class ListenerManager {
    ListenerManager() {
        ClassGetter.getClassesForPackage(Main.getInstance(), "io.github.biielkts").forEach(clazz -> {
            if (Listener.class.isAssignableFrom(clazz) && !clazz.equals(InventoryBuilder.class)) {
                try {
                    Listener listener = (Listener) clazz.newInstance();
                    Bukkit.getPluginManager().registerEvents(listener, Main.getInstance());
                } catch (InstantiationException | IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}

class CommandManager {
    CommandManager() {
        this.register("gamemode", new GamemodeCommand());
        this.register("fly", new FlyCommand());
        this.register("ping", new PingCommand());
        this.register("setspawn", new SetSpawnCommand());
    }

    private void register(String name, CommandExecutor command) {
        Main.getInstance().getCommand(name).setExecutor(command);
    }
}

class RunnableManager {
    RunnableManager() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(
                Main.getInstance(),
                new ServerRefreshRunnable(),
                0L,
                20L * 5
        );
        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                Main.getInstance(),
                new ScoreboardRefreshRunnable(),
                0L,
                20L*8
        );
    }
}