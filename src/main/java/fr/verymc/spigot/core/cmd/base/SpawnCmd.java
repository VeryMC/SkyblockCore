package main.java.fr.verymc.spigot.core.cmd.base;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnCmd implements CommandExecutor, TabCompleter {
    public static final Location Spawn = new Location(Main.instance.mainWorld, -187.5, 71.5, -63.5, -180, 0);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof final Player player) || !cmd.getName().equalsIgnoreCase("spawn")) {
            return false;
        }
        if (args.length > 1) {
            player.sendActionBar("§c/spawn [Joueur]");
            return false;
        }
        switch (args.length) {
            case 0 -> {
                final int timeLeft = Main.instance.getCooldown(player.getName());
                if (timeLeft == 0) {
                    PlayerUtils.instance.teleportPlayerFromRequest(player, Spawn, PlayerUtils.instance.getPlayerTeleportingdelay(player), ServerType.SKYBLOCK_HUB);
                }
            }
            case 1 -> {
                if (Bukkit.getPlayer(args[0]) == null) {
                    player.sendActionBar("§cCe joueur n'existe pas !");
                    return false;
                }
                if (Bukkit.getPlayer(args[0]).isOnline()) {
                    player.sendActionBar("§cCe joueur n'est pas en ligne !");
                    return false;
                }
                Player p = Bukkit.getPlayer(args[0]);
                p.teleport(Spawn);
                p.sendActionBar("§aVous avez été Téléporté au spawn par un membre du staff !");
                player.sendActionBar("§6" + p.getName() + " §aa été envoyé au spawn avec succès !");
            }
            default -> player.sendActionBar("§c/spawn [Joueur]");
        }
        return false;
    }

    public void get_all_player_for_tab_complete(ArrayList<String> subcmd, CommandSender sender) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.getName().equals(sender.getName())) {
                subcmd.add(p.getName());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (sender.hasPermission("*")) {
                if (args.length == 1) {
                    get_all_player_for_tab_complete(subcmd, sender);
                    Collections.sort(subcmd);
                } else if (args.length >= 2) {
                    subcmd.add("");
                    Collections.sort(subcmd);
                }
            } else {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
