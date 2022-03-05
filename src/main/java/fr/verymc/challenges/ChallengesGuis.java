package main.java.fr.verymc.challenges;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import main.java.fr.verymc.core.Main;
import main.java.fr.verymc.crates.CratesKeyManager;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.gui.MenuGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ChallengesGuis implements Listener {

    public static void CompleteChallenge(Player player, int nombre) {
        if (IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
            Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily." + nombre + ".Progression", 0);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "is bank give " + player.getName() + " crystaux 2");
            EcoAccountsManager.instance.AddFounds(player, 5000.0, false);
            CratesKeyManager.GiveCrateKey(player, 1, "Challenge");

            if (Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily." + nombre + ".Palier") >= 5) {
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily." + nombre + ".Active", false);
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily." + nombre + ".Palier", 5);
            } else {
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily." + nombre + ".Active", true);
            }
            Main.instance.saveData();

            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
            player.sendMessage("§6§lChallenges §8» §fVous venez de compléter le challenge journalier n°§6" + nombre +
                    " §7au palier " + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily." + nombre + ".Palier")
                    + "/5.");
            player.sendMessage("§6§lChallenges §8» §fVous avez reçu 2 crystaux, 5000$ et x1 Clée Challenge.");

        } else {
            player.sendMessage("§6§lChallenges §8» §fVous pouvez uniquement compléter les challenges en possédant ou en"
                    + " faisant partie d'une ile.");
        }
    }

    public static void MakeMainGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Challenges");

        ItemStack custom1 = new ItemStack(Material.CLOCK, 1);
        ItemMeta customa = custom1.getItemMeta();
        customa.setDisplayName("§6Challenges journaliers");
        custom1.setItemMeta(customa);
        inv.setItem(11, custom1);

        ItemStack custom2 = new ItemStack(Material.BUCKET, 1);
        ItemMeta customb = custom2.getItemMeta();
        customb.setDisplayName("§6Challenges normaux");
        custom2.setItemMeta(customb);
        inv.setItem(15, custom2);


        ItemStack custom9 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom9.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom9.setItemMeta(customh);
        inv.setItem(26, custom9);

        player.openInventory(inv);
    }

    public static void MakeDailyGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, "§6Challenges journaliers");

        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.COBBLESTONE, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Palier");
            customa.setDisplayName("§6Miner " + ChallengesBlockBreak.cobble * palier + " de pierre");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression") + "/"
                    + ChallengesBlockBreak.cobble * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(10, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.COBBLESTONE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Minage de pierre");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(10, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.COAL_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Palier");
            customa.setDisplayName("§6Miner " + ChallengesBlockBreak.coal * palier + " minerais de charbon");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression") + "/"
                    + ChallengesBlockBreak.coal * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(11, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.COAL_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Minage de charbon");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(11, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.IRON_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Palier");
            customa.setDisplayName("§6Miner " + ChallengesBlockBreak.iron * palier + " minerais de fer");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression") + "/"
                    + ChallengesBlockBreak.iron * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(12, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.IRON_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Minage de fer");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(12, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.GOLD_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Palier");
            customa.setDisplayName("§6Miner " + ChallengesBlockBreak.gold * palier + " minerais d'or");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression") + "/"
                    + ChallengesBlockBreak.gold * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(13, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.GOLD_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Minage d'or");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(13, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.DIAMOND_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Palier");
            customa.setDisplayName("§6Miner " + ChallengesBlockBreak.diamond * palier + " minerais de diamant");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression") + "/"
                    + ChallengesBlockBreak.diamond * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(14, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.DIAMOND_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Minage de diamant");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(14, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.EMERALD_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Palier");
            customa.setDisplayName("§6Miner " + ChallengesBlockBreak.emerald * palier + " émeraudes");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression") + "/"
                    + ChallengesBlockBreak.emerald * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(15, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.EMERALD_ORE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Minage d'émeraude");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(15, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.ANCIENT_DEBRIS, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Palier");
            customa.setDisplayName("§6Miner " + ChallengesBlockBreak.debris * palier + " ancients débris");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression") + "/"
                    + ChallengesBlockBreak.debris * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(16, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.ANCIENT_DEBRIS, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Minage d'ancients débris");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(16, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.OAK_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Palier");
            customa.setDisplayName("§6Casser " + ChallengesBlockBreak.oak_log * palier + " bûches d'oak");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Progression") + "/"
                    + ChallengesBlockBreak.oak_log * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(19, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.OAK_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Cassage d'oak");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(19, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.BIRCH_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Palier");
            customa.setDisplayName("§6Casser " + ChallengesBlockBreak.birch_log * palier + " bûches de birch");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Progression") + "/"
                    + ChallengesBlockBreak.birch_log * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(20, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.BIRCH_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Cassage de birch");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(20, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.SPRUCE_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Palier");
            customa.setDisplayName("§6Casser " + ChallengesBlockBreak.spruce_log * palier + " bûches de spruce");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Progression") + "/"
                    + ChallengesBlockBreak.spruce_log * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(21, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.SPRUCE_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Cassage de spruce");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(21, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.DARK_OAK_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Palier");
            customa.setDisplayName("§6Casser " + ChallengesBlockBreak.dark_oak_log * palier + " bûches de dark oak");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Progression") + "/"
                    + ChallengesBlockBreak.dark_oak_log * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(22, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.DARK_OAK_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Cassage de dark oak");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(22, custom1);
        }

        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.ACACIA_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Palier");
            customa.setDisplayName("§6Casser " + ChallengesBlockBreak.acacia_log * palier + " bûches d'acacia");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Progression") + "/"
                    + ChallengesBlockBreak.acacia_log * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(23, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.ACACIA_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Cassage d'acacia");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(23, custom1);
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Active") == true) {
            ItemStack custom1 = new ItemStack(Material.JUNGLE_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Palier");
            customa.setDisplayName("§6Casser " + ChallengesBlockBreak.jungle_log * palier + " bûches de jungle");
            customa.setLore(Arrays.asList("§7" + Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Progression") + "/"
                    + ChallengesBlockBreak.jungle_log * palier, "§7Palier: " + palier + "/5"));
            custom1.setItemMeta(customa);
            inv.setItem(24, custom1);
        } else {
            ItemStack custom1 = new ItemStack(Material.JUNGLE_LOG, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§6Cassage de jungle");
            customa.setLore(Arrays.asList("§7Terminé"));
            customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            custom1.setItemMeta(customa);
            inv.setItem(24, custom1);
        }


        ItemStack custom9 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom9.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom9.setItemMeta(customh);
        inv.setItem(35, custom9);


        player.openInventory(inv);
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        if (current == null) {
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Challenges")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                MenuGui.OpenMainMenu((Player) e.getWhoClicked());
                return;
            }
            if (current.getType() == Material.CLOCK) {
                ChallengesGuis.MakeDailyGui((Player) e.getWhoClicked());
                return;
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Challenges journaliers")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                ChallengesGuis.MakeMainGui((Player) e.getWhoClicked());
                return;
            }
        }
    }

}
