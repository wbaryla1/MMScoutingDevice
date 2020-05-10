package io.github.kekdrick.ScoutingDevice;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin implements Listener {
    Map<String, Long> cooldowns = new HashMap<String, Long>();

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

                    //cooldown:
                    if (cooldowns.containsKey(player.getName())) {
                        //player is in the hashmap
                        if (cooldowns.get(player.getName()) > System.currentTimeMillis())
                            //still time left in the cooldown
                            return;
                    }

                    cooldowns.put(player.getName(), System.currentTimeMillis() + (5 * 1000));

                    int playerCount = 0;
                    for (Entity e : player.getNearbyEntities(250,250,250)) {
                        if (e instanceof Player) {
                            playerCount++;
                            Location eLoc = e.getLocation();
                            Location playerLoc = player.getLocation();
                            String xLoc;
                            String zLoc;


                            if ((playerLoc.getZ() - eLoc.getZ()) > 40)
                                zLoc = "North";
                            else if ((eLoc.getZ() - playerLoc.getZ()) > 40)
                                zLoc = "South";
                            else
                                zLoc = "";


                            if ((playerLoc.getX() - eLoc.getX()) > 40)
                                xLoc = "East";
                            else if ((eLoc.getX() - playerLoc.getX()) > 40)
                                xLoc = "West";
                            else
                                xLoc = "";

                            //test

                            String direction = (zLoc != "" && xLoc != "") ? zLoc + " " + xLoc :
                                    (zLoc != "") ? zLoc : (xLoc != "") ? xLoc : "close";

                            e.sendMessage("You feel as though your presence is detected by an unfamiliar device");
                            player.sendMessage(ChatColor.RED + "Heathen detected " + direction + " from here.");
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
                    item.setAmount(item.getAmount() - 1);
                }
            }
        }
    }
}
