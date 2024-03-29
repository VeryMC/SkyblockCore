package main.java.fr.verymc.spigot.core.cmd.moderation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BuildCmd implements CommandExecutor, TabCompleter {

    public static ArrayList<UUID> Build = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("build")) {
            if (!(sender instanceof Player player)) {
                return false;
            }
            if (!player.hasPermission("build")) {
                player.sendActionBar("§cPermissions insuffisantes !");
                return false;
            }
            if (Build.contains(player.getUniqueId())) {
                Build.remove(player.getUniqueId());
                player.sendActionBar("§6Mode buildeur désactivé !");
            } else {
                Build.add(player.getUniqueId());
                player.sendActionBar("§6Mode buildeur activé !");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("build")) {
            subcmd.add("");
            Collections.sort(subcmd);
        }
        return subcmd;
    }
}
