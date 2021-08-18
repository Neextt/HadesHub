package io.github.biielkts.listeners.lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ProtectionWorldWeatherChangeListener implements Listener {
    @EventHandler
    public void onChange(WeatherChangeEvent event) {
        Boolean toWeatherState = event.toWeatherState();

        if (toWeatherState) event.setCancelled(true);
    }
}
