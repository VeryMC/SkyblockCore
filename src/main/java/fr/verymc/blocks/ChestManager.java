package main.java.fr.verymc.blocks;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.shopgui.BuyShopItem;
import main.java.fr.verymc.storage.AsyncConfig;
import main.java.fr.verymc.storage.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class ChestManager {

    public static ChestManager instance;
    public ArrayList<main.java.fr.verymc.blocks.Chest> chests = new ArrayList<main.java.fr.verymc.blocks.Chest>();

    public ChestManager() {
        instance = this;

        new PlayerShopGuis();
        new StackerGui();
    }

    public void autoSellForVeryChest() {
        HashMap<main.java.fr.verymc.blocks.Chest, Double> reward = new HashMap<>();
        for (main.java.fr.verymc.blocks.Chest chest : chests) {
            try {
                BlockState e = chest.getBlock().getBlock().getState();
                if (e.getType() != Material.CHEST) {
                    continue;
                }
                Inventory ed = ((Chest) e).getBlockInventory();
                if (((Chest) e).getCustomName() == null) {
                    continue;
                }
                if (!((Chest) e).getCustomName().contains("§6SellChest")) {
                    continue;
                }
                double total = 0;
                for (ItemStack sd : ed) {
                    if (sd == null) continue;
                    if (sd.getType() == null) continue;
                    ItemStack searched = new ItemStack(sd.getType());
                    searched.setAmount(sd.getAmount());
                    if (BuyShopItem.pricessell.get(new ItemStack(sd.getType())) != null && BuyShopItem.pricessell.get(new ItemStack(sd.getType())) > 0) {
                        int amount = BuyShopItem.GetAmountInInvNo(searched, ed);
                        Double price = BuyShopItem.pricessell.get(new ItemStack(sd.getType()));
                        price = amount * price;
                        BuyShopItem.removeItems(ed, searched.getType(), amount);
                        total += price;
                    }
                }
                ((Chest) e).getBlockInventory().setContents(ed.getContents());
                main.java.fr.verymc.blocks.Chest playername = chest;
                if (reward.containsKey(playername)) {
                    reward.put(playername, reward.get(playername) + total);
                } else {
                    reward.put(playername, total);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        for (Entry<main.java.fr.verymc.blocks.Chest, Double> tosend : reward.entrySet()) {
            Island island = IslandManager.instance.getIslandByLoc(tosend.getKey().getBlock());
            if (island == null) continue;
            island.getBank().addMoney(tosend.getValue());
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                autoSellForVeryChest();
            }
        }, 20 * 20);
    }

    public void giveChest(Player player, int type) {
        ItemStack aa = new ItemStack(Material.AIR);
        if (type == 1) {
            aa = new ItemStack(Material.CHEST);
            ItemMeta ameta = aa.getItemMeta();

            ameta.setDisplayName("§6SellChest");
            aa.setUnbreakable(true);
            aa.setItemMeta(ameta);
        } else if (type == 0) {
            aa = new ItemStack(Material.HOPPER);
            ItemMeta ameta = aa.getItemMeta();

            ameta.setDisplayName("§6Chunk Hoppeur");
            aa.setUnbreakable(true);
            aa.setItemMeta(ameta);
        } else if (type == 2) {
            aa = new ItemStack(Material.CHEST);
            ItemMeta ameta = aa.getItemMeta();

            ameta.setDisplayName("§6Player shop");
            aa.setUnbreakable(true);
            aa.setItemMeta(ameta);
        }

        if (aa.getType() == Material.AIR) {
            return;
        }

        player.getInventory().addItem(aa);
    }

    public void placeChest(Player player, Location block, int type, ItemStack item, double price, Material stacked) {
        chests.add(new main.java.fr.verymc.blocks.Chest(type, block, player.getUniqueId(), block.getChunk().getChunkKey(),
                item, price, false, false, System.currentTimeMillis(), 1, stacked));
    }

    public void removeChestFromLoc(Location block) {
        for (main.java.fr.verymc.blocks.Chest chest : chests) {
            if (chest.getBlock().equals(block.getBlock().getLocation())) {
                chests.remove(chest);
                HashMap<String, Object> toEdit = new HashMap<>();
                toEdit.put(chest.getId() + "", null);
                AsyncConfig.instance.setAndSaveAsync(toEdit, ConfigManager.instance.getDataChests(),
                        ConfigManager.instance.chestsFile);
                return;
            }
        }
    }

    public main.java.fr.verymc.blocks.Chest getChestFromLoc(Location block) {
        for (main.java.fr.verymc.blocks.Chest chest : chests) {
            if (chest.getBlock().equals(block.getBlock().getLocation())) {
                return chest;
            }
        }
        return null;
    }

    public UUID getOwner(Location loc) {
        for (main.java.fr.verymc.blocks.Chest chest : chests) {
            if (chest.getBlock().equals(loc)) {
                return chest.getOwner();
            }
        }
        return null;
    }
}