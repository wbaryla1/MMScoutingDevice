package io.github.wbaryla1;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ScoutingDeviceCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("scoutdevice")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.DARK_RED + "You cannot run this command");
                return true;
            }
            Player player = (Player)sender;
            if (!(player.isOp()))
                return false;
            if (player.getInventory().firstEmpty() == -1) {
                Location loc = player.getLocation();
                World world = player.getWorld();

                if (args.length == 0) {
                    world.dropItemNaturally(loc, getItem());
                } else {
                    try {
                        for (int i = 0; i < Integer.parseInt(args[0]); i++)
                            world.dropItemNaturally(loc, getItem());
                    } catch (NumberFormatException ex) {
                        //nothing
                    }
                }
                player.sendMessage(ChatColor.GOLD + "The scouting device has been given to you");
                return true;
            }
            if (args.length == 0) {
                player.getInventory().addItem(getItem());
            }
            else {
                try {
                    for (int i = 0; i < Integer.parseInt(args[0]); i++)
                        player.getInventory().addItem(getItem());
                } catch (NumberFormatException ex) {
                    //nothing
                }
            }

            player.sendMessage(ChatColor.GOLD + "The scouting device has been given to you");
            return true;
        }
        return false;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GREEN + "Scouting Device");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add("Use this Holy Device from the Gods");
        lore.add("to search for deserters");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Right Click) &a&oDetect Deserters"));
        meta.setLore(lore);



        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;
    }
}
