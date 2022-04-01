package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.gui.MenuGui;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class IslandGuiManager implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();

        if (current == null) {
            return;
        }
        if (current.getType() == null) {
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Menu d'île")) {
            e.setCancelled(true);
            if (!IslandManager.instance.asAnIsland(player)) {
                player.closeInventory();
                return;
            }
            if (current.getType() == Material.PLAYER_HEAD) {
                IslandMemberGui.instance.openMemberIslandMenu(player);
                return;
            }
            if (current.getType() == Material.ENDER_EYE) {
                IslandManager.instance.teleportPlayerToIslandSafe(player);
                return;
            }
            if (current.getType() == Material.ARROW) {
                MenuGui.OpenMainMenu(player);
                return;
            }
            if (current.getType() == Material.BLAST_FURNACE) {
                IslandUpgradeGui.instance.openUpgradeIslandMenu(player);
                return;
            }
            if (current.getType() == Material.CHEST) {
                IslandBankGui.instance.openBankIslandMenu(player);
                return;
            }
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Membres de l'île")) {
            e.setCancelled(true);
            if (!IslandManager.instance.asAnIsland(player)) {
                player.closeInventory();
                return;
            }
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
            if (current.getType() == Material.PLAYER_HEAD) {
                Island currentIsland = IslandManager.instance.getPlayerIsland(player);
                String playerName = current.getItemMeta().getDisplayName().replace("§6", "");
                UUID targetUUID;
                if (Bukkit.getPlayer(playerName) != null) {
                    targetUUID = Bukkit.getPlayer(playerName).getUniqueId();
                } else {
                    targetUUID = Bukkit.getOfflinePlayer(playerName).getUniqueId();
                }
                if (IslandManager.instance.promoteAndDemoteAction(player, targetUUID, playerName, e.getClick(),
                        currentIsland)) {
                    IslandMemberGui.instance.openMemberIslandMenu(player);
                    return;
                } else {
                    player.sendMessage("§6§6§lIles §8» §fTu ne peux pas faire cette action.");
                }
            }
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Améliorations de l'île")) {
            e.setCancelled(true);
            if (!IslandManager.instance.asAnIsland(player)) {
                return;
            }
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
            if (current.getType() == Material.GRASS_BLOCK) {
                if (IslandManager.instance.getPlayerIsland(player).getSizeUpgrade().upOfOneLevel(player)) {
                    IslandManager.instance.setWorldBorder(player);
                    IslandUpgradeGui.instance.openUpgradeIslandMenu(player);
                    return;
                }
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Banque de l'île")) {
            e.setCancelled(true);
            if (!IslandManager.instance.asAnIsland(player)) {
                return;
            }
            if (current.getType() == Material.SUNFLOWER) {
                if (e.getClick() == ClickType.RIGHT) {
                    if (EcoAccountsManager.instance.checkForFounds(player, 1000.0)) {
                        EcoAccountsManager.instance.removeFounds(player, 1000.0, true);
                        IslandManager.instance.getPlayerIsland(player).getBank().addMoney(1000.0);
                    } else {
                        player.sendMessage("§6§6§lIles §8» §fTu n'as pas asser d'argent en banque.");
                    }
                    IslandBankGui.instance.openBankIslandMenu(player);
                    return;
                } else if (e.getClick() == ClickType.LEFT) {
                    if (IslandManager.instance.getPlayerIsland(player).getBank().getMoney() >= 1000.0) {
                        EcoAccountsManager.instance.addFounds(player, 1000.0, true);
                        IslandManager.instance.getPlayerIsland(player).getBank().removeMoney(1000.0);
                    } else {
                        player.sendMessage("§6§6§lIles §8» §fTu n'as pas asser d'argent en banque.");
                    }
                    IslandBankGui.instance.openBankIslandMenu(player);
                    return;
                }
                return;
            }
            if (current.getType() == Material.NETHER_STAR) {
                if (IslandManager.instance.getPlayerIsland(player).getBank().getCrystaux() < 5.0) {
                    player.sendMessage("§6§6§lIles §8» §fTu n'as pas asser de crystaux en banque.");
                    return;
                }
                if (e.getClick() == ClickType.LEFT) {
                    ItemStack item = new ItemStack(Material.NETHER_STAR);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("§65 crystaux");
                    meta.setLore(Arrays.asList("§7Clic pour récupérer 5 crystaux", "§7Shit clic pour tout récupérer",
                            "§7Récupérés par §6" + player.getName()));
                    meta.setUnbreakable(true);
                    item.setItemMeta(meta);
                    player.getInventory().addItem(item);
                    IslandManager.instance.getPlayerIsland(player).getBank().removeCrystaux(5.0);
                    IslandBankGui.instance.openBankIslandMenu(player);
                    return;
                }
            }
            if (current.getType() == Material.EXPERIENCE_BOTTLE) {
                if (e.getClick() == ClickType.LEFT) {
                    Integer exp = IslandManager.instance.getPlayerIsland(player).getBank().getXp();
                    if (exp <= 0) {
                        return;
                    }
                    PlayerUtils.setTotalExperience(player, exp + PlayerUtils.getTotalExperience(player));
                    IslandManager.instance.getPlayerIsland(player).getBank().removeXp(exp);
                    IslandBankGui.instance.openBankIslandMenu(player);
                    return;
                }
                if (e.getClick() == ClickType.RIGHT) {
                    Integer exp = PlayerUtils.getTotalExperience(player);
                    if (exp <= 0) {
                        return;
                    }
                    IslandManager.instance.getPlayerIsland(player).getBank().addXp(exp);
                    PlayerUtils.setTotalExperience(player, 0);
                    IslandBankGui.instance.openBankIslandMenu(player);
                    return;
                }
            }
            if (current.getType() == Material.ARROW) {
                IslandMainGui.instance.openMainIslandMenu(player);
                return;
            }
        }
    }
}