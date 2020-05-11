package io.github.kekdrick.ScoutingDevice;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScoutingDeviceListener implements Listener {
    Map<String, Long> cooldowns = new HashMap<String, Long>();

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

                    int playerCount, cCount, nCount, neCount, eCount, seCount, sCount, swCount,
                            wCount, nwCount;

                    playerCount = cCount = nCount = neCount = eCount = seCount = sCount = swCount = wCount = nwCount = 0;
                    for (Entity e : player.getNearbyEntities(250,40,250)) {
                        if (e instanceof Player) {
                            if (((Player) e).getGameMode() == GameMode.CREATIVE || ((Player) e).getGameMode() == GameMode.SPECTATOR)
                                continue;
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
                                xLoc = "West";
                            else if ((eLoc.getX() - playerLoc.getX()) > 40)
                                xLoc = "East";
                            else
                                xLoc = "";

                            String direction = (zLoc != "" && xLoc != "") ? zLoc + " " + xLoc :
                                    (zLoc != "") ? zLoc : (xLoc != "") ? xLoc : "close";

                            switch (direction) {
                                case "North":
                                    nCount++;
                                    break;
                                case "North East":
                                    neCount++;
                                    break;
                                case "East":
                                    eCount++;
                                    break;
                                case "South East":
                                    seCount++;
                                    break;
                                case "South":
                                    sCount++;
                                    break;
                                case "South West":
                                    swCount++;
                                    break;
                                case "West":
                                    wCount++;
                                    break;
                                case "North West":
                                    nwCount++;
                                    break;
                                default:
                                    cCount++;
                                    break;
                            }



                            e.sendMessage(ChatColor.RED + "You feel as though your presence is detected by an unfamiliar device...");
                            //player.sendMessage(ChatColor.RED + "Heathen detected " + direction + " from here.");
                        }
                    }

                    HashMap<String, Integer> locations = new HashMap<String, Integer>();
                    locations.put("North", nCount);
                    locations.put("North East", neCount);
                    locations.put("East", eCount);
                    locations.put("South East", seCount);
                    locations.put("South", sCount);
                    locations.put("South West", swCount);
                    locations.put("West", wCount);
                    locations.put("North West", nwCount);
                    locations.put("Close By", cCount);

                    for (Map.Entry<String, Integer> entry : locations.entrySet()) {
                        if (entry.getValue() == 0)
                            continue;
                        player.sendMessage(ChatColor.RED + "" + entry.getValue() + " people located " + entry.getKey());
                    }
                    player.sendMessage(ChatColor.GREEN + "" + playerCount + " people detected.");
                    RemoveDevice(player);
                }
            }

    }

    public void RemoveDevice(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.COMPASS)) {
            if (Objects.requireNonNull(item.getItemMeta()).hasLore())
                if (item.getItemMeta().getDisplayName().contains("Scouting Device"))
                    item.setAmount(item.getAmount() - 1);
        }
    }
}
