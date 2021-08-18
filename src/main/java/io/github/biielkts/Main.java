package io.github.biielkts;

import io.github.biielkts.servers.StartManager;
import io.github.biielkts.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;
    public Main() {
        Main.instance = this;
    }
    public static Main getInstance() {
        return Main.instance;
    }

    @Override
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage("§6[HadesAPI] [HadesHub] connecting to api for api.redehades.com");
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§a[HadesHub] Inicializando...");
        saveDefaultConfig();
        new StartManager();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        Bukkit.getConsoleSender().sendMessage("§[HadesHub] o plugin foi ativado com sucesso.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("O plugin foi desativado.");
    }

    }
