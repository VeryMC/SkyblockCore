package fr.farmeurimmo.criptmania.atout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class AtoutCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
				AtoutGui.MakeAtoutGui(player);
			} else {
				SendActionBar.SendActionBarMsg(player, "�cTrop d'arguments");
			}
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("atout") || cmd.getName().equalsIgnoreCase("atouts")) {
	            if (args.length >= 0){
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}
