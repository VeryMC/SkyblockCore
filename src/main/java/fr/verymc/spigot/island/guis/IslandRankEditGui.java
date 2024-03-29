package main.java.fr.verymc.spigot.island.guis;

import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.perms.IslandPerms;
import main.java.fr.verymc.spigot.island.perms.IslandRanks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class IslandRankEditGui {

    public static IslandRankEditGui instance;

    public IslandRankEditGui() {
        instance = this;
    }

    public void openEditRankIslandMenu(Player player) {

        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
        Inventory inv = Bukkit.createInventory(null, 54, "§6Permissions des grades de l'île");

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(53, custom8);

        ItemStack custom9 = new ItemStack(Material.KNOWLEDGE_BOOK, 1);
        ItemMeta customi = custom9.getItemMeta();
        customi.setDisplayName("§6Informations complémentaires");
        customi.setLore(Arrays.asList("§7Les permissions (oranges & blanches) s'appliquent à", "§7tous les membres de l'île.", "",
                "§7Les COOP(s) et les visiteurs bénéficient uniquement", "§7des permissions de couleur blanche."));
        custom9.setItemMeta(customi);
        inv.setItem(45, custom9);

        int currentSlot = 10;

        for (IslandPerms perm : IslandPerms.getAllPerms()) {
            ItemStack custom = IslandPerms.getItemStackForPerm(perm);
            if (custom == null) continue;
            if (custom.getType() == Material.AIR) continue;
            ItemMeta customh1 = custom.getItemMeta();
            customh1.setDisplayName(perm.getDescription());
            ArrayList<String> lore = new ArrayList<>();
            lore.addAll(Arrays.asList("§7Clic droit pour augmenter", "§7Clic gauche pour diminuer", "", "§7Grades :", ""));
            boolean isMaxRange = false;
            if (!perm.getDescription().startsWith("§f")) {
                isMaxRange = true;
            }
            for (IslandRanks rank : IslandRanks.values()) {
                if (rank == IslandRanks.CHEF) continue;
                if (isMaxRange && rank == IslandRanks.COOP) continue;
                if (isMaxRange && rank == IslandRanks.VISITEUR) continue;
                if (playerIsland.hasPerms(rank, perm, null)) {
                    lore.add("§a" + rank.name());
                } else {
                    lore.add("§c" + rank.name());
                }
            }
            customh1.setLore(lore);
            custom.setItemMeta(customh1);

            inv.setItem(currentSlot, custom);
            currentSlot++;
            if (17 == currentSlot) {
                currentSlot += 2;
            }
            if (26 == currentSlot) {
                currentSlot += 2;
            }
            if (35 == currentSlot) {
                currentSlot += 2;
            }
        }
        player.openInventory(inv);
    }
}
