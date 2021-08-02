package fr.farmeurimmo.criptmania.gui;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

public class BuyCommand {
	
	public static void BuyCmd(String cmd, Player player) {
	    TNEAPI ecoAPI = TNE.instance().api();
	    int money = ecoAPI.getAccount(player.getName()).getHoldings().intValue();
	    
		if(cmd.contains("hat")) {
			if(money >= Farm2WinGui.hatprix);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(Farm2WinGui.hatprix));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set hat");
			player.sendMessage("�6Vous avez re�u l'acc�s � la commande /hat !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 player.closeInventory();
			    	 MakeCommandsGui.MakeCommandGui(player);
			     }
			}, 2);
		}
		if(cmd.contains("fly")) {
			if(money >= Farm2WinGui.flyprix);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(Farm2WinGui.flyprix));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set fly");
			player.sendMessage("�6Vous avez re�u l'acc�s � la commande /fly !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 player.closeInventory();
					 MakeCommandsGui.MakeCommandGui(player);
			     }
			}, 2);
		}
		if(cmd.contains("feed")) {
			if(money >= Farm2WinGui.feedprix);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(Farm2WinGui.feedprix));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set feed");
			player.sendMessage("�6Vous avez re�u l'acc�s � la commande /feed !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 player.closeInventory();
					 MakeCommandsGui.MakeCommandGui(player);
			     }
			}, 2);
		}
		if(cmd.contains("craft")) {
			if(money >= Farm2WinGui.craftprix);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(Farm2WinGui.craftprix));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set craft");
			player.sendMessage("�6Vous avez re�u l'acc�s � la commande /craft !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 player.closeInventory();
					 MakeCommandsGui.MakeCommandGui(player);
			     }
			}, 2);
		}
		if(cmd.contains("isrename")) {
			if(money >= Farm2WinGui.isrenameprix);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(Farm2WinGui.isrenameprix));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set iridiumskyblock.rename");
			player.sendMessage("�6Vous avez re�u l'acc�s � la commande /is rename !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 player.closeInventory();
					 MakeCommandsGui.MakeCommandGui(player);
			     }
			}, 2);
		}
		if(cmd.contains("enchantement")) {
			if(money >= Farm2WinGui.enchantementprix);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(Farm2WinGui.enchantementprix));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set enchantement");
			player.sendMessage("�6Vous avez re�u l'acc�s � la commande /enchantement !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 player.closeInventory();
					 MakeCommandsGui.MakeCommandGui(player);
			     }
			}, 2);
		}
	}

}
