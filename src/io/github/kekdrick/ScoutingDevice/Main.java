package io.github.kekdrick.ScoutingDevice;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new ScoutingDeviceListener(), this);

        this.getCommand("scoutdev").setExecutor(new ScoutingDeviceCommand());
    }

    @Override
    public void onDisable() {

    }


}
