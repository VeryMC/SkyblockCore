package fr.farmeurimmo.criptmania.cmd.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.Main;
import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class TpaCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
					if(args.length == 0 || args.length >= 2) {
						SendActionBar.SendActionBarMsg(player, "�c/tpa <Joueur>");
					} else if (args.length == 1){
						if(!Main.pending.contains(player)) {
						if(Bukkit.getPlayer(args[0]) != null) {
							if(Bukkit.getPlayer(args[0]).isOnline()) {
							Player p = Bukkit.getPlayer(args[0]);
							Main.haverequest.add(p);
							Main.pending.add(player);
							Main.instance1.setTarget(player.getName(), p.getName());
							p.sendMessage("�6�lT�l�portation �8� �f" + player.getName() + " souhaite ce t�l�porter � vous. \n \nVous avez 60 secondes"
									+ " pour accepter ou refuser avec les commandes: �a/tpyes �fou �c/tpno \n�f");
							
							player.sendMessage("�6�lT�l�portation �8� �fVous avez envoy� une demande de t�l�portation au joueur " + p.getName() + ".\n \n�fSi vous "
									+ "souhaitez �cannuler �fvotre demande de t�l�portation, merci d'effectuer la commande �c/tpacancel \n�f");
							return true;
							} else {
								SendActionBar.SendActionBarMsg(player, "�cCe joueur n'est pas en ligne !");
							}
						} else {
							SendActionBar.SendActionBarMsg(player, "�cCe joueur n'existe pas !");
						}
					} else {
						player.sendMessage("�6�lT�l�portation �8� �fVous avez d�j� une demande en cours, �cannulez �fla avec �c/tpacancel"
								+ " �fpour pouvoir en relancer une.");
					}
				}
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("tpa")) {
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