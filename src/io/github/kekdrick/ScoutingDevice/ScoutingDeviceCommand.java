package io.github.kekdrick.ScoutingDevice;

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
import java.util.Objects;

public class ScoutingDeviceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("scoutdev")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.DARK_RED + "You cannot run this command");
                return true;
            }
            args[0] = (args[0] == "" || args[0] == null) ? "1" : args[0];
            Player player = (Player)sender;
            if (!(player.isOp()))
                return false;
            if (player.getInventory().firstEmpty() == -1) {
                Location loc = player.getLocation();
                World world = player.getWorld();

                if (args[0] == "1") {
                    world.dropItemNaturally(loc, getItem());
                } else {
                    try {
                        for (int i = 0; i < Integer.parseInt(Objects.requireNonNull(args[0])); i++)
                            world.dropItemNaturally(loc, getItem());
                    } catch (NumberFormatException ex) {
                        //nothing
                    }
                }
                player.sendMessage(ChatColor.GOLD + "The scouting device has been given to you");
                return true;
            }
            if (args[0] == "1") {
                player.getInventory().addItem(getItem());
            }
            else {
                try {
                    for (int i = 0; i < Integer.parseInt(args[0]); i++)
                        player.getInventory().addItem(getItem());
                } catch (NumberFormatException ex) {
                    args[0] = "0";
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
