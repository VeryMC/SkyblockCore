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
import fr.farmeurimmo.verymc.utils.Maths;

public class MoneyCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 0) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage("�6�lMonnaie �8� �fVous avez �6"+Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(player.getName()),2));
		}
		} else if (args.length == 1) {
			if(EcoAccountsManager.Moneys.get(args[0]) != null) {
				sender.sendMessage("�6�lMonnaie �8� �6"+args[0]+"�f poss�de �6"+Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(args[0]), 2)+"$");
			}
		} else if (args.length == 2) {
			sender.sendMessage("�6�lMonnaie �8� �f/money <pseudo> <give/remove> <montant>");
		} else if (args.length == 3) {
			if(EcoAccountsManager.Moneys.get(args[0]) != null) {
			if(args[1].equalsIgnoreCase("give")) {
				
				if(args[2].length() <= 9) {
				Double aaa = Double.parseDouble(args[2]);
				EcoAccountsManager.AddFounds(args[0], aaa, false);
				sender.sendMessage("�6�lMonnaie �8� �f"+args[0]+" a re�u �6"+aaa+"$�f sur son compte avec succ�s.");
				} else {
					sender.sendMessage("�6�lMonnaie �8� �fVeuillez choisir un nombre plus petit.");
				}
				
			} else if (args[1].equalsIgnoreCase("remove")) {
				if(args[2].length() <= 9) {
					Double aaa = Double.parseDouble(args[2]);
					EcoAccountsManager.RemoveFounds(args[0], aaa);
					sender.sendMessage("�6�lMonnaie �8� �f"+args[0]+" a perdu �6"+aaa+"$�f sur son compte avec succ�s.");
					} else {
						sender.sendMessage("�6�lMonnaie �8� �fVeuillez choisir un nombre plus petit.");
					}
			} else if (args[1].equalsIgnoreCase("set")) {
				if(args[2].length() <= 9) {
					Double aaa = Double.parseDouble(args[2]);
					EcoAccountsManager.SetFounds(args[0], aaa);
					sender.sendMessage("�6�lMonnaie �8� �fL'argent de "+args[0]+" a �t� d�finis sur �6"+aaa+"$�f avec succ�s.");
					} else {
						sender.sendMessage("�6�lMonnaie �8� �fVeuillez choisir un nombre plus petit.");
					}
			} else {
				sender.sendMessage("�6�lMonnaie �8� �f/money <pseudo> <give/remove> <montant>");
			}
			} else {
				sender.sendMessage("�6�lMonnaie �8� �fCe compte n'existe pas !");
			}
		}
		
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("money")) {
	            if (args.length == 1){
	            	for(Player p : Bukkit.getOnlinePlayers()) {
	            		subcmd.add(p.getName());
	            	}
	            } else if(args.length == 2){
	            	if(sender.hasPermission("give")) {
	            		subcmd.add("give");
	            		subcmd.add("remove");
	            		subcmd.add("set");
	            	} else {
	            	subcmd.add("");
	            	}
	            } else {
	            	subcmd.add("");
	            }
	        }
	        Collections.sort(subcmd);
			return subcmd;
	 }
}