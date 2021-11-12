package fr.farmeurimmo.verymc.cmd.moderation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.verymc.gui.AfkMineCaptchaGui;
import fr.farmeurimmo.verymc.utils.SendActionBar;

public class AntiAfkMineCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
		if(player.hasPermission("*")) {
			if(args.length == 0) {
				SendActionBar.SendActionBarMsg(player, "�c/afkmine <Joueur>");
				return true;
			}
			if(args.length == 1) {
			if(Bukkit.getPlayer(args[0]) != null) {
				if(Bukkit.getPlayer(args[0]).isOnline()) {
				Player p = Bukkit.getPlayer(args[0]);
				AfkMineCaptchaGui.MakeAfkMineCaptchaGui(p);
				}
				else {
					SendActionBar.SendActionBarMsg(player, "�cCe joueur n'est pas en ligne !");
				}
				}
			else {
				SendActionBar.SendActionBarMsg(player, "�cCe joueur n'existe pas !");
			}
			}
		}
		else {
			SendActionBar.SendActionBarMsg(player, "�cPermissions insuffisantes !");
		}
		}
		
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("afkmine")) {
	            if (args.length == 1) {
	            	for(Player player : Bukkit.getOnlinePlayers()) {
	            		subcmd.add(player.getName());
	            	}
	            	Collections.sort(subcmd);
	            } else if (args.length >= 2){
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}