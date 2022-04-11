package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IslandTopGui {

    public static IslandTopGui instance;
    public HashMap<Island, Integer> islandClassementPosition = new HashMap<>();

    public IslandTopGui() {
        instance = this;
        autoUpdate();
    }

    public void autoUpdate() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                int currentNum = 1;
                ArrayList<Island> islands = IslandManager.instance.islands;
                ArrayList<Island> islandsAlreadySorted = new ArrayList<>();
                HashMap<Island, Integer> islandClassementPos = new HashMap<>();
                while (true) {
                    double bestValue = -1;
                    Island tmpIsland = null;
                    for (Island island : islands) {
                        if (!islandsAlreadySorted.contains(island)) {
                            if (island.getValue() > bestValue) {
                                if (tmpIsland != null) {
                                    islandsAlreadySorted.remove(tmpIsland);
                                }
                                bestValue = island.getValue();
                                islandsAlreadySorted.add(island);
                                tmpIsland = island;
                            }
                        }
                    }
                    if (tmpIsland == null) {
                        break;
                    }
                    islandClassementPos.put(tmpIsland, currentNum);
                    currentNum += 1;
                    if (islandsAlreadySorted.size() >= islandClassementPos.size()) {
                        break;
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    @Override
                    public void run() {
                        islandClassementPosition = islandClassementPos;
                    }
                }, 0);
            }
        }, 0, 20);
    }

    public HashMap<Island, Integer> getTopIsland() {
        return islandClassementPosition;
    }

    public void openTopIslandMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, "§6Classement des îles");

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(44, custom8);

        for (Map.Entry<Island, Integer> entry : getTopIsland().entrySet()) {
            int slot = entry.getValue().intValue() + 9;
            if (slot == 7) slot += 2;
            if (slot == 13) slot += 2;
            ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta customi = (SkullMeta) custom10.getItemMeta();
            customi.setOwner(entry.getKey().getOwner());
            customi.setDisplayName("§6#" + entry.getValue() + ": " + entry.getKey().getName());
            customi.setLore(Arrays.asList("§7Points: " + entry.getKey().getValue()));
            custom10.setItemMeta(customi);
            inv.setItem(slot, custom10);
        }

        player.openInventory(inv);
    }
}
