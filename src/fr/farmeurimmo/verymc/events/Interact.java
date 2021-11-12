package fr.farmeurimmo.verymc.events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import fr.farmeurimmo.verymc.cmd.moderation.BuildCmd;

public class Interact implements Listener {
	
	public static ArrayList<Player> Build = BuildCmd.Build;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getItemInHand().getType() == Material.FLINT_AND_STEEL && player.getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
		if(e.getClickedBlock() == null) {
			return;
		}
		if(e.getClickedBlock().getType() == Material.ENDER_CHEST || e.getClickedBlock().getType() == Material.ENCHANTING_TABLE
				|| e.getClickedBlock().getType() == Material.CRAFTING_TABLE || e.getClickedBlock().getType() == Material.ANVIL) {
			return;
		}
		if(player.getWorld().getName().equalsIgnoreCase("world")) {
			if(Build.contains(player)) {
				return;
				}
			else {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void BucketEmpty(PlayerBucketEmptyEvent e) {
		if(e.getBlock().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void BucketFill(PlayerBucketFillEvent e) {
		if(e.getBlock().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void BlockBreake(BlockBreakEvent e) {
		if(e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(!Build.contains(e.getPlayer())) {
			e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void playerdrop(PlayerDropItemEvent e) {
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
			return;
		}
	}
	@EventHandler
	public void Bucketevent(PlayerItemConsumeEvent e) {
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void playerlosefood(FoodLevelChangeEvent e) {
		Player player = (Player) e.getEntity();
		if(player.getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
}