package io.github.wbaryla1;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ScoutingDevicePlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new ScoutingDeviceListener(), this);

        this.getCommand("scoutdevice").setExecutor(new ScoutingDeviceCommand());
    }

    @Override
    public void onDisable() {
        //nothing
    }
}
