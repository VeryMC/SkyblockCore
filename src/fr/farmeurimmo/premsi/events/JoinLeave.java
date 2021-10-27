package fr.farmeurimmo.premsi.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.premsiserv.core.paper.PaperAPI;

import fr.farmeurimmo.premsi.core.Main;
import fr.farmeurimmo.premsi.scoreboard.ScoreBoard;
import fr.farmeurimmo.premsi.utils.BossBar;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class JoinLeave implements Listener {
	
	String Grade = "�7N/A";
	
	@EventHandler
	public void OnJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		
		player.setGameMode(GameMode.SURVIVAL);
		
		BossBar.AddBossBarForPlayer(player);
		
		ScoreBoard.setScoreBoard(player);
		
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if(user.getCachedData().getMetaData().getPrefix() != null) {
		Grade = user.getCachedData().getMetaData().getPrefix();
		}
		String JoinMessage = null;
		if(!PaperAPI.getInstance().GetVanishedList().contains(player.getName())) {
		if(!IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
			JoinMessage = "�7[�a+�7] " + Grade.replace("&", "�").replace("&", "�") + " " + player.getName();
			}
			else {
				int classement = 0;
				classement = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getRank();
				JoinMessage = "�7[�a+�7] [#" + classement + "] " + Grade.replace("&", "�").replace("&", "�") + " " + player.getName();
			}
		}
			event.setJoinMessage(JoinMessage);
			
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.1.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.2.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.3.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.3.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.3.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.4.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.4.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.4.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.5.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.5.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.5.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.6.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.6.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.6.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.7.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.7.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.7.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Atout.1.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.1.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.1.Level", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Atout.2.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.2.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.2.Level", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Atout.3.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.3.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.3.Level", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Atout.4.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.4.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.4.Level", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Atout.1.Active") == true) {
				if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.1.Level") == 2) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
				}
				}
			if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Atout.2.Active") == true) {
				if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.2.Level") == 2) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
				}
				}
			if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Atout.3.Active") == true) {
				if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.3.Level") == 3) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
				}
				}
	}
	@EventHandler
	public void OnLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if(user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix();
			}
		String LeaveMessage = null;
		if(!PaperAPI.getInstance().GetVanishedList().contains(player.getName())) {
		if(!IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
		LeaveMessage = "�7[�c-�7] " + Grade.replace("&", "�").replace("&", "�") + " " + player.getName();
		}
		else {
			int classement = 0;
			classement = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getRank();
			LeaveMessage = "�7[�c-�7] [#" + classement + "] " + Grade.replace("&", "�").replace("&", "�") + " " + player.getName();
		}
		}
		event.setQuitMessage(LeaveMessage);
	}
}