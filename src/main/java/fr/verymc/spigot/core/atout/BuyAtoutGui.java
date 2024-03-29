package main.java.fr.verymc.spigot.core.atout;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import main.java.fr.verymc.spigot.core.gui.Farm2WinGui;
import main.java.fr.verymc.spigot.core.storage.SkyblockUser;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import main.java.fr.verymc.spigot.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class BuyAtoutGui implements Listener {

    static Double haste = 600000.0;
    static Double speed = 400000.0;
    static Double jumpboost = 250000.0;

    public static void MakeBuyAtoutGui(Player player) {


        Inventory inv = Bukkit.createInventory(null, 27, "§6Boutique des atouts");

        inv.setItem(22, PreGenItems.instance.getOwnerHead(player));

        SkyblockUserManager.instance.checkForAccount(player);

        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());

        if (!skyblockUser.hasHaste()) {
            ItemStack custom1 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§eHaste 2");
            customa.setLore(Arrays.asList("§7", "§6Prix: " + haste + "$"));
            custom1.setItemMeta(customa);
            inv.setItem(10, custom1);
        } else if (skyblockUser.hasHaste()) {
            ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customi = custom10.getItemMeta();
            customi.setDisplayName("§eHaste 2 §c(déjà possédé)");
            custom10.setItemMeta(customi);
            inv.setItem(10, custom10);
        }


        if (!skyblockUser.hasSpeed()) {
            ItemStack custom1 = new ItemStack(Material.SUGAR, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§eSpeed 2");
            customa.setLore(Arrays.asList("§7", "§6Prix: " + speed + "$"));
            custom1.setItemMeta(customa);
            inv.setItem(12, custom1);
        } else if (skyblockUser.hasSpeed()) {
            ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customi = custom10.getItemMeta();
            customi.setDisplayName("§eSpeed 2 §c(déjà possédé)");
            custom10.setItemMeta(customi);
            inv.setItem(12, custom10);
        }


        if (!skyblockUser.hasJump()) {
            ItemStack custom1 = new ItemStack(Material.RABBIT_FOOT, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§eJumpboost 3");
            customa.setLore(Arrays.asList("§7", "§6Prix: " + jumpboost + "$"));
            custom1.setItemMeta(customa);
            inv.setItem(14, custom1);
        } else if (skyblockUser.hasJump()) {
            ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customi = custom10.getItemMeta();
            customi.setDisplayName("§eJumpboost 3 §c(déjà possédé)");
            custom10.setItemMeta(customi);
            inv.setItem(14, custom10);
        }

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(26, custom8);


        player.openInventory(inv);
    }

    public static void BuyAtout(int effect, Player player) {

        SkyblockUserManager.instance.checkForAccount(player);

        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());

        Double money = EcoAccountsManager.instance.getMoney(player.getUniqueId());

        if (effect == 1) {
            if (money >= haste) {
                EcoAccountsManager.instance.removeFounds(player, haste, true);
                skyblockUser.setHaste(true);
                skyblockUser.setHasteActive(true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
                player.sendMessage("§6Vous avez reçu l'accès à l'atout haste 2 !");
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    public void run() {
                        MakeBuyAtoutGui(player);
                    }
                }, 2);
            }
        }
        if (effect == 2) {
            if (money >= speed) {
                EcoAccountsManager.instance.removeFounds(player, speed, true);
                skyblockUser.setSpeed(true);
                skyblockUser.setSpeedActive(true);
                player.sendMessage("§6Vous avez reçu l'accès à l'atout speed 2 !");
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    public void run() {
                        MakeBuyAtoutGui(player);
                    }
                }, 2);
            }
        }
        if (effect == 3) {
            if (money >= jumpboost) {
                EcoAccountsManager.instance.removeFounds(player, jumpboost, true);
                skyblockUser.setJump(true);
                skyblockUser.setJumpActive(true);
                player.sendMessage("§§6Vous avez reçu l'accès à l'atout Jumpboost 3 !");
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    public void run() {
                        MakeBuyAtoutGui(player);
                    }
                }, 2);
            }
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        if (current == null) {
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Boutique des atouts")) {
            e.setCancelled(true);
            if (current.getType() == Material.ARROW) {
                Farm2WinGui.MainBoutiqueGUI(player);
            }
            if (current.getType() == Material.GOLDEN_PICKAXE) {
                BuyAtout(1, player);
            }
            if (current.getType() == Material.SUGAR) {
                BuyAtout(2, player);
            }
            if (current.getType() == Material.RABBIT_FOOT) {
                BuyAtout(3, player);
            }
        }
    }
}
