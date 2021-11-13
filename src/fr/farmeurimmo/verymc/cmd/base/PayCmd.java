package fr.farmeurimmo.verymc.cmd.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.verymc.eco.EcoAccountsManager;

public class PayCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0 || args.length == 1 || args.length >= 3) {
				player.sendMessage("�6�lMonnaie �8� �fUtilisation, /pay <joueur> <montant>");
			} else {
				if(EcoAccountsManager.IsExisting(args[0]) == true) {
					if(!args[0].equals(player.getName())) {
					boolean digit = false;
					try {
				        @SuppressWarnings("unused")
						int intValue = Integer.parseInt(args[1]);
				        digit = true;
				    } catch (NumberFormatException e) {
				        digit = false;
				    }
					if(!args[1].contains("-") && digit == true) {
					if(args[1].length() <= 9) {
						int aaa = Integer.parseInt(args[1]);
						if(EcoAccountsManager.CheckForFounds(player, aaa) == true) {
							EcoAccountsManager.AddFounds(args[0], aaa);
						    EcoAccountsManager.RemoveFounds(player.getName(), aaa);
						    player.sendMessage("�6�lMonnaie �8� �fVous avez envoy� avec �asucc�s �6"+aaa+"$�f au joueur "+args[0]);
						    if(Bukkit.getPlayer(args[0]) != null) {
						    	Bukkit.getPlayer(args[0]).sendMessage("�6�lMonnaie �8� �fVous avez re�u avec �asucc�s �6"+aaa+"$�f du joueur "+player.getName());
						    }
						} else {
							player.sendMessage("�6�lMonnaie �8� �fVous n'avez pas les fonds requis.");
						}
						} else {
							player.sendMessage("�6�lMonnaie �8� �fVeuillez choisir un nombre plus petit.");
						}
					} else {
						player.sendMessage("�6�lMonnaie �8� �fMerci d'entrer un nombre valide et positif.");
					}
					} else{
						player.sendMessage("�6�lMonnaie �8� �fVous ne pouvez pas vous envoyer de l'argent � vous m�me.");
					}
				} else {
					player.sendMessage("�6�lMonnaie �8� �fCe compte n'existe pas.");
				}
			}
		}
		
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("pay")) {
	            if (args.length == 1){
	            	for(Player p : Bukkit.getOnlinePlayers()) {
	            		if(!p.getName().equals(sender.getName())) {
	            		  subcmd.add(p.getName());
	            		}
	            	}
	            } else if(args.length == 2){
	            	subcmd.add("");
	            } else {
	            	subcmd.add("");
	            }
	        }
	        Collections.sort(subcmd);
			return subcmd;
	 }
}
