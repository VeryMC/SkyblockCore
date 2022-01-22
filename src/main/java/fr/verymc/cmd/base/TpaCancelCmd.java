package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TpaCancelCmd implements CommandExecutor, TabCompleter {

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Main.instance1.pending.contains(player)) {
                Main.instance1.pending.remove(player);
                player.sendMessage("§§6§lTéléportation §8» §fVous avez §cannulé §fvotre demande de Téléportation à "
                        + Main.instance1.getTarget(player.getName()) + ".");
                Main.instance1.haverequest.remove(Main.instance1.getTarget(player.getName()));
                Main.instance1.tpatarget.remove(player);
            } else {
                player.sendMessage("§§6§lTéléportation §8» §fVous ne possèdez aucune demande de Téléportaiton de votre part.");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("tpacancel")) {
            if (args.length >= 1) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}