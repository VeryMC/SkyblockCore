package fr.farmeurimmo.criptmania.cmd.moderation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.gui.SanctionSetGui;
import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class SSCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("*")) {
				if(args.length == 0) {
					SendActionBar.SendActionBarMsg(player, "�c/ss <Joueur>");
				} else if(args.length == 1) {
				SanctionSetGui.MakeSSGui(player, Bukkit.getPlayer(args[0]));
				} else {
					SendActionBar.SendActionBarMsg(player, "�c/ss <Joueur>");
				}
			} else {
				SendActionBar.SendActionBarMsg(player, "�cVous n'avez pas la permission !");
			}
		}
		
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("ss") || cmd.getName().equalsIgnoreCase("sanctionset")) {
	        	if(sender.hasPermission("*")) {
	            if (args.length == 1){
	            	for(Player player : Bukkit.getOnlinePlayers()) {
	            		subcmd.add(player.getName());
	            	}
	            	Collections.sort(subcmd);
	        	} else {
	        		subcmd.add("");
	            	Collections.sort(subcmd);
	        	}
	        }
	        }
			return subcmd;
	 }
}
