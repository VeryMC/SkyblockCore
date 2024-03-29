package main.java.fr.verymc.spigot.core.cmd.base;

import main.java.fr.verymc.spigot.core.gui.MenuGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            MenuGui.OpenMainMenu(player);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("menu")) {
            subcmd.add("");
            Collections.sort(subcmd);
        }
        return subcmd;
    }
}
