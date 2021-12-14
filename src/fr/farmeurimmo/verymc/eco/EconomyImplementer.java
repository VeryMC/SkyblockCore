package fr.farmeurimmo.verymc.eco;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class EconomyImplementer implements Economy {
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasBankSupport() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int fractionalDigits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String format(double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String currencyNamePlural() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String currencyNameSingular() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAccount(String playerName) {
		EcoAccountsManager.CheckForAccount(Bukkit.getPlayer(playerName));
		return true;
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		EcoAccountsManager.CheckForAccount(Bukkit.getPlayer(player.getName()));
		return true;
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		EcoAccountsManager.CheckForAccount(Bukkit.getPlayer(playerName));
		return true;
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		EcoAccountsManager.CheckForAccount(Bukkit.getPlayer(player.getName()));
		return true;
	}

	@Override
	public double getBalance(String playerName) {
		return EcoAccountsManager.GetMoney(playerName);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		return EcoAccountsManager.GetMoney(player.getName());
	}

	@Override
	public double getBalance(String playerName, String world) {
		return EcoAccountsManager.GetMoney(playerName);
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return EcoAccountsManager.GetMoney(player.getName());
	}

	@Override
	public boolean has(String playerName, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		if(EcoAccountsManager.CheckForFounds(Bukkit.getPlayer(playerName), amount)) {
			EcoAccountsManager.RemoveFounds(playerName, amount);
		}
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		if(EcoAccountsManager.CheckForFounds(Bukkit.getPlayer(player.getName()), amount)) {
			EcoAccountsManager.RemoveFounds(player.getName(), amount);
		}
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		if(EcoAccountsManager.CheckForFounds(Bukkit.getPlayer(playerName), amount)) {
			EcoAccountsManager.RemoveFounds(playerName, amount);
		}
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		if(EcoAccountsManager.CheckForFounds(Bukkit.getPlayer(player.getName()), amount)) {
			EcoAccountsManager.RemoveFounds(player.getName(), amount);
		}
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		EcoAccountsManager.AddFounds(playerName, amount, true);
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		EcoAccountsManager.AddFounds(player.getName(), amount, true);
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		EcoAccountsManager.AddFounds(playerName, amount, true);
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		EcoAccountsManager.AddFounds(player.getName(), amount, true);
		return null;
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getBanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createPlayerAccount(String playerName) {
		EcoAccountsManager.CheckForAccount(Bukkit.getPlayer(playerName));
		return true;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		EcoAccountsManager.CheckForAccount(Bukkit.getPlayer(player.getName()));
		return true;
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		EcoAccountsManager.CheckForAccount(Bukkit.getPlayer(playerName));
		return true;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		EcoAccountsManager.CheckForAccount(Bukkit.getPlayer(player.getName()));
		return true;
	}

}