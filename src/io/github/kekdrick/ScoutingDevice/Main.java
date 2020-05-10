package io.github.kekdrick.ScoutingDevice;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        this.getCommand("scoutdev").setExecutor(new ScoutingDeviceCommand());
    }

    @Override
    public void onDisable() {

    }

    @EventHandler()
    public void onClick(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.COMPASS))
            if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                Player player = (Player)event.getPlayer();
                //Right click
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    int playerCount = 0;
                    for (Entity e : player.getNearbyEntities(250,250,250)) {
                        if (e instanceof Player) {
                            playerCount++;
                            Location eLoc = e.getLocation();
                            Location playerLoc = player.getLocation();
                            String xLoc;
                            String yLoc;

                            if (playerLoc.getX() < eLoc.getX())
                                xLoc = " East";
                            else if (playerLoc.getX() == eLoc.getX())
                                xLoc = "";
                            else
                                xLoc = " West";

                            if (playerLoc.getY() < eLoc.getY())
                                yLoc = "North";
                            else
                                yLoc = "South";
                            e.sendMessage("You feel as though your presence is detected by an unfamiliar device");
                            player.sendMessage(ChatColor.RED + "Heathen detected " + yLoc + xLoc + " from here.");
                        }
                    }
                    player.sendMessage(ChatColor.GREEN + "" + playerCount + " people detected.");
                    RemoveDevice(player);
                }
            }

    }

    public void RemoveDevice(Player player) {
        for (ItemStack item : player.getInventory()) {
            if (item.getType().equals(Material.COMPASS)) {
                if (item.getItemMeta().hasLore()) {
                    player.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
                }
            }
        }
    }
}
