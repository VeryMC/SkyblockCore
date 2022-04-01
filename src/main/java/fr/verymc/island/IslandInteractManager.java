package main.java.fr.verymc.island;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class IslandInteractManager implements Listener {

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getItem() == null) return;
        if (e.getItem().getType() == null) return;
        if (e.getItem().getItemMeta() == null) return;
        if (e.getItem().getItemMeta().getDisplayName() == null) return;
        if (e.getItem().getLore() == null) return;

        ItemStack currentItem = e.getItem();
        if (currentItem.getType() != Material.NETHER_STAR) return;
        if (!currentItem.isUnbreakable()) return;

        Island island = IslandManager.instance.getPlayerIsland(player);
        if (island != null) {
            if (player.isSneaking()) {
                island.getBank().addCrystaux(currentItem.getAmount() * 5);
                currentItem.setAmount(0);
            } else {
                island.getBank().addCrystaux(1 * 5);
                currentItem.setAmount(currentItem.getAmount() - 1);
            }
        }
    }
}