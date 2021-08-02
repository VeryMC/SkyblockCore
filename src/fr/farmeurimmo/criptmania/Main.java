package fr.farmeurimmo.criptmania;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.farmeurimmo.criptmania.WineLottery.WineGui;
import fr.farmeurimmo.criptmania.WineLottery.WineSpawn;
import fr.farmeurimmo.criptmania.cmd.base.BarCmd;
import fr.farmeurimmo.criptmania.cmd.base.BuildCmd;
import fr.farmeurimmo.criptmania.cmd.base.CraftCmd;
import fr.farmeurimmo.criptmania.cmd.base.EnchantementCmd;
import fr.farmeurimmo.criptmania.cmd.base.Farm2WinCmd;
import fr.farmeurimmo.criptmania.cmd.base.FeedCmd;
import fr.farmeurimmo.criptmania.cmd.base.FlyCmd;
import fr.farmeurimmo.criptmania.cmd.base.HatCmd;
import fr.farmeurimmo.criptmania.cmd.base.MenuCmd;
import fr.farmeurimmo.criptmania.cmd.base.SpawnCmd;
import fr.farmeurimmo.criptmania.cmd.base.WarpCmd;
import fr.farmeurimmo.criptmania.cmd.base.WarpsCmd;
import fr.farmeurimmo.criptmania.cmd.base.WikiCmd;
import fr.farmeurimmo.criptmania.events.AntiExplo;
import fr.farmeurimmo.criptmania.events.Interact;
import fr.farmeurimmo.criptmania.events.JoinLeave;
import fr.farmeurimmo.criptmania.events.SwitchWorld;
import fr.farmeurimmo.criptmania.events.Tchat;
import fr.farmeurimmo.criptmania.featherfly.DailyFlyCmd;
import fr.farmeurimmo.criptmania.featherfly.FeatherFlyCmd;
import fr.farmeurimmo.criptmania.featherfly.FeatherFlyInteract;
import fr.farmeurimmo.criptmania.gui.Farm2WinGui;
import fr.farmeurimmo.criptmania.gui.WarpGui;
import fr.farmeurimmo.criptmania.gui.WikiGui;
import fr.farmeurimmo.criptmania.items.ItemLegCmd;
import fr.farmeurimmo.criptmania.items.PermanantItem;
import fr.farmeurimmo.criptmania.scoreboard.ScoreBoard;
import fr.farmeurimmo.criptmania.utils.BossBar;
import net.luckperms.api.LuckPerms;

public class Main extends JavaPlugin implements Listener {
	
	static LuckPerms api;
    private static Main instance;
    
    public static Main instance1;
    
    public static Main getInstance() {
        return instance;
    }
	
   private static HashMap<String, Integer> spawncooldown = new HashMap<>();
	
	public void setCooldown(String uuid, Integer time) {
		if (time == null)
			spawncooldown.remove(uuid);
		else
			spawncooldown.put(uuid, time);
	}
    public int getCooldown(String player) {
        return (spawncooldown.get(player) == null ? 0 : spawncooldown.get(player));
    }
    
    
	@Override
	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		instance1 = this;
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("Plugin d�marr� !");
		System.out.println("-----------------------------------------------------------------------------------------------------");
		Bukkit.getPluginManager().isPluginEnabled("LuckPerms");
		Bukkit.getPluginManager().isPluginEnabled("Citizens");
		Bukkit.getPluginManager().isPluginEnabled("TheNewEconomy");
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
			System.out.println("Le plugin LuckPerms a �t� trouv� !");
		} else {
			getLogger().warning("Le plugin LuckPerms est manquant.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("Initialisation de l'api LuckPerms en cours...");
		if (provider != null) {
			api = provider.getProvider();
			System.out.println("API initialis� !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
		}
		if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
			System.out.println("Le plugin Citizens a �t� trouv� !");
		} else {
			getLogger().warning("Le plugin Citizens est manquant.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		System.out.println("-----------------------------------------------------------------------------------------------------");
		getServer().getPluginManager().registerEvents(new JoinLeave(), this);
		getServer().getPluginManager().registerEvents(new Tchat(), this);
		getServer().getPluginManager().registerEvents(new ScoreBoard(), this);
		getServer().getPluginManager().registerEvents(new Interact(), this);
		getServer().getPluginManager().registerEvents(new PermanantItem(), this);
		getServer().getPluginManager().registerEvents(new AntiExplo(), this);
		getServer().getPluginManager().registerEvents(new WikiGui(), this);
		getServer().getPluginManager().registerEvents(new Farm2WinGui(), this);
		getServer().getPluginManager().registerEvents(new WineGui(), this);
		getServer().getPluginManager().registerEvents(new WarpGui(), this);
		getServer().getPluginManager().registerEvents(new FeatherFlyInteract(), this);
		getServer().getPluginManager().registerEvents(new SwitchWorld(), this);
		this.getCommand("spawn").setExecutor(new SpawnCmd());
		this.getCommand("wiki").setExecutor(new WikiCmd());
		this.getCommand("build").setExecutor(new BuildCmd());
		this.getCommand("farm2win").setExecutor(new Farm2WinCmd());
		this.getCommand("bar").setExecutor(new BarCmd());
		this.getCommand("menu").setExecutor(new MenuCmd());
		this.getCommand("warp").setExecutor(new WarpCmd());
		this.getCommand("warps").setExecutor(new WarpsCmd());
		this.getCommand("featherfly").setExecutor(new FeatherFlyCmd());
		this.getCommand("dailyfly").setExecutor(new DailyFlyCmd());
		this.getCommand("itemleg").setExecutor(new ItemLegCmd());
		this.getCommand("fly").setExecutor(new FlyCmd());
		this.getCommand("feed").setExecutor(new FeedCmd());
		this.getCommand("hat").setExecutor(new HatCmd());
		this.getCommand("craft").setExecutor(new CraftCmd());
		this.getCommand("enchantement").setExecutor(new EnchantementCmd());
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		Main.spawncooldown.clear();
		BossBar.CreateBossBar(1);
		BuildCmd.Build.clear();
		WineSpawn.SpawnPnj(new Location(Bukkit.getServer().getWorld("world"), -194.5, 109, -49.5, -90, 2));
		for(Player player : Bukkit.getOnlinePlayers()) {
			ScoreBoard.setScoreBoard(player);
		}
	}
	@Override
	public void onDisable() {
		WineSpawn.RemovePnj();
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("Plugin stopp� !");
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}
}