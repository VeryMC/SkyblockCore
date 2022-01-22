package main.java.fr.verymc.atout;

import main.java.fr.verymc.core.Main;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.gui.Farm2WinGui;
import main.java.fr.verymc.utils.PreGenItems;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
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

    static Double haste = (double) 600000;
    static Double speed = (double) 400000;
    static Double jumpboost = (double) 250000;

    public static void MakeBuyAtoutGui(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");


        Inventory inv = Bukkit.createInventory(null, 27, "§6Boutique des atouts");

        inv.setItem(22, PreGenItems.getOwnerHead(player));

        if (Main.instance1.getData().getInt("Joueurs." + player.getName() + ".Atout.1.Level") == 0) {
            ItemStack custom1 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§eHaste 2");
            customa.setLore(Arrays.asList("§7", "§6Prix: " + haste + "$"));
            custom1.setItemMeta(customa);
            inv.setItem(10, custom1);
        } else if (Main.instance1.getData().getInt("Joueurs." + player.getName() + ".Atout.1.Level") == 2) {
            ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customi = custom10.getItemMeta();
            customi.setDisplayName("§eHaste 2 §c(déjà poss§d§)");
            custom10.setItemMeta(customi);
            inv.setItem(10, custom10);
        }


        if (Main.instance1.getData().getInt("Joueurs." + player.getName() + ".Atout.2.Level") == 0) {
            ItemStack custom1 = new ItemStack(Material.SUGAR, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§eSpeed 2");
            customa.setLore(Arrays.asList("§7", "§6Prix: " + speed + "$"));
            custom1.setItemMeta(customa);
            inv.setItem(12, custom1);
        } else if (Main.instance1.getData().getInt("Joueurs." + player.getName() + ".Atout.2.Level") == 2) {
            ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customi = custom10.getItemMeta();
            customi.setDisplayName("§eSpeed 2 §c(déjà poss§d§)");
            custom10.setItemMeta(customi);
            inv.setItem(12, custom10);
        }


        if (Main.instance1.getData().getInt("Joueurs." + player.getName() + ".Atout.3.Level") == 0) {
            ItemStack custom1 = new ItemStack(Material.RABBIT_FOOT, 1);
            ItemMeta customa = custom1.getItemMeta();
            customa.setDisplayName("§eJumpboost 3");
            customa.setLore(Arrays.asList("§7", "§6Prix: " + jumpboost + "$"));
            custom1.setItemMeta(customa);
            inv.setItem(14, custom1);
        } else if (Main.instance1.getData().getInt("Joueurs." + player.getName() + ".Atout.3.Level") == 3) {
            ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
            ItemMeta customi = custom10.getItemMeta();
            customi.setDisplayName("§eJumpboost 3 §c(déjà poss§d§)");
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
        Double money = EcoAccountsManager.instance.Moneys.get(player.getName());

        if (effect == 1) {
            if (money >= haste) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), haste);
            Main.instance1.getData().set("Joueurs." + player.getName() + ".Atout.1.Active", true);
            Main.instance1.getData().set("Joueurs." + player.getName() + ".Atout.1.Level", 2);
            Main.instance1.saveData();
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
            player.sendMessage("§6Vous avez reçu l'accès §l'atout haste 2 !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    MakeBuyAtoutGui(player);
                }
            }, 2);
        }
        if (effect == 2) {
            if (money >= speed) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), speed);
            Main.instance1.getData().set("Joueurs." + player.getName() + ".Atout.2.Active", true);
            Main.instance1.getData().set("Joueurs." + player.getName() + ".Atout.2.Level", 2);
            Main.instance1.saveData();
            player.sendMessage("§6Vous avez reçu l'accès à l'atout speed 2 !");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    MakeBuyAtoutGui(player);
                }
            }, 2);
        }
        if (effect == 3) {
            if (money >= jumpboost) ;
            EcoAccountsManager.instance.RemoveFounds(player.getName(), jumpboost);
            Main.instance1.getData().set("Joueurs." + player.getName() + ".Atout.3.Active", true);
            Main.instance1.getData().set("Joueurs." + player.getName() + ".Atout.3.Level", 3);
            Main.instance1.saveData();
            player.sendMessage("§§6Vous avez reçu l'accès à l'atout Jumpboost 3 !");
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    MakeBuyAtoutGui(player);
                }
            }, 2);
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