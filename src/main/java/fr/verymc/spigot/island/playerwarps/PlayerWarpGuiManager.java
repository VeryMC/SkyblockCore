package main.java.fr.verymc.spigot.island.playerwarps;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerWarpGuiManager implements Listener {

    @EventHandler
    public void inventoryClicEvent(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().getType() == null) {
            return;
        }

        ItemStack current = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase("§6Menu de gestion du warp joueur")) {
            e.setCancelled(true);
            PlayerWarp pw = PlayerWarpManager.instance.getPlayerWarpFromUUID(p.getUniqueId());
            if (current.getType() == Material.BOOK) {
                PlayerWarpBrowserGui.instance.openBrowserMenu(p, 1);
                return;
            }
            if (pw == null) {
                if (current.getType() == Material.BARRIER) {
                    PlayerWarpManagingGui.instance.creationMode.put(p, new PlayerWarp(null, null,
                            0.0, 0.0, -1.0, new ArrayList<>()));
                    p.closeInventory();
                    p.sendMessage("§6§lPlayerWarp §8» §fVeuillez entrer le nom du warp puis dites OUI quand vous êtes à la localisation " +
                            "souhaité pour celui ci. Pour annuler, dites CANCEL.");
                    return;
                }
            } else {
                if (current.getType() == Material.BARRIER) {
                    PlayerWarpManager.instance.deleteWarp(p);
                    p.sendMessage("§6§lPlayerWarp §8» §fVotre warp a bien été supprimé.");
                    p.closeInventory();
                    return;
                }
                if (current.getType() == Material.ENDER_PEARL) {
                    if (pw.getLocation() != null) {
                        PlayerUtils.instance.teleportPlayerFromRequest(p, pw.getLocation(), PlayerUtils.instance.getPlayerTeleportingdelay(p), ServerType.SKYBLOCK_ISLAND);
                    } else {
                        p.sendMessage("§6§lPlayerWarp §8» §fCe warp n'a pas de localisation valide.");
                    }
                    return;
                }
                if (current.getType() == Material.COMPASS) {
                    if (PlayerWarpManagingGui.instance.creationMode.containsKey(p)) {
                        PlayerWarpManagingGui.instance.creationMode.remove(p);
                        p.sendMessage("§6§lPlayerWarp §8» §fVous avez annulé la modification de la localisation du warp. " +
                                "(L'ancinne a été restauré)");
                        pw.setLocation(PlayerWarpManagingGui.instance.oldLocation.get(p));
                        PlayerWarpManagingGui.instance.oldLocation.remove(p);
                    } else {
                        p.sendMessage("§6§lPlayerWarp §8» §fVeuillez entrer le nom du warp puis dites OUI quand vous êtes à la localisation " +
                                "souhaité pour celui ci. Pour annuler, dites CANCEL.");
                        PlayerWarpManagingGui.instance.oldLocation.put(p, pw.getLocation().clone());
                        pw.setLocation(null);
                        PlayerWarpManagingGui.instance.creationMode.put(p, pw);
                        p.closeInventory();
                    }
                    return;
                }
                if (current.getType() == Material.NAME_TAG) {
                    if (PlayerWarpManagingGui.instance.creationMode.containsKey(p)) {
                        PlayerWarpManagingGui.instance.creationMode.remove(p);
                        pw.setName(PlayerWarpManagingGui.instance.oldName.get(p));
                        p.sendMessage("§6§lPlayerWarp §8» §fVous avez annulé la modification du nom du warp. (L'ancient a été restauré)");
                        PlayerWarpManagingGui.instance.oldName.remove(p);
                    } else {
                        p.sendMessage("§6§lPlayerWarp §8» §fVeuillez entrer le nouveau nom du warp. Pour annuler, dites CANCEL.");
                        PlayerWarpManagingGui.instance.oldName.put(p, pw.getName());
                        pw.setName(null);
                        PlayerWarpManagingGui.instance.creationMode.put(p, pw);
                        p.closeInventory();
                    }
                    return;
                }
            }
        }
        if (e.getView().getTitle().contains("§6Notation du warp de ")) {
            e.setCancelled(true);
            PlayerWarp pw = PlayerWarpManager.instance.getPlayerWarpFromPlayerName(e.getView().getTitle().replace("§6Notation du warp de ", ""));
            if (pw == null) {
                p.sendMessage("§6§lPlayerWarp §8» §fCe warp n'existe pas.");
            } else {
                if (e.getCurrentItem().getType() == Material.GREEN_STAINED_GLASS_PANE) {
                    if (pw.getNote() < 0) {
                        pw.setNote(5);
                    }
                    double notea = (pw.getNote() + e.getCurrentItem().getAmount()) / 2;
                    pw.setNote(notea);
                    p.closeInventory();
                    p.sendMessage("§6§lPlayerWarp §8» §fVous avez noté le warp §e" + pw.getName() + " §favec une note de " +
                            e.getCurrentItem().getAmount() + ", la nouvelle note moyenne est " + NumberFormat.getInstance().format(notea) + "§f/5");
                    pw.addAlreadyVoted(p.getUniqueId());
                }
            }
            return;
        }
        if (e.getView().getTitle().contains("§6Warp browser #")) {
            e.setCancelled(true);
            int page = 1;
            try {
                page = Integer.parseInt(e.getView().getTitle().replace("§6Warp browser #", ""));
            } catch (NumberFormatException ex) {
                return;
            }
            HashMap<Integer, PlayerWarp> warps = PlayerWarpBrowserGui.instance.getWarpsFromPageNum(page);

            if (warps.containsKey(e.getSlot())) {
                PlayerWarp pw = warps.get(e.getSlot());
                if (e.getClick() == ClickType.LEFT) {
                    Island island = IslandManager.instance.getIslandByLoc(pw.getLocation());
                    if (island != null) {
                        if (island.getMembers().containsKey(PlayerWarpManager.instance.getOwnerUUIDFromPlayerWarp(pw))) {
                            if (island.isPublic()) {
                                if (!island.getBanneds().contains(p.getUniqueId())) {
                                    PlayerUtils.instance.teleportPlayerFromRequest(p, pw.getLocation(), 0, ServerType.SKYBLOCK_ISLAND);
                                    pw.addVue();
                                } else {
                                    p.sendMessage("§6§lPlayerWarp §8» §fVous êtes banni de cette île.");
                                }
                            } else {
                                if (island.getMembers().containsKey(p.getUniqueId())) {
                                    PlayerUtils.instance.teleportPlayerFromRequest(p, pw.getLocation(), 0, ServerType.SKYBLOCK_ISLAND);
                                    pw.addVue();
                                } else {
                                    p.sendMessage("§6§lPlayerWarp §8» §fVous n'avez pas accès à ce warp car l'île est privée.");
                                }
                            }
                        } else {
                            p.sendMessage("§6§lPlayerWarp §8» §fCe warp est invalide.");
                        }
                        return;
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    if (pw.alreadyVoted(p.getUniqueId())) {
                        p.sendMessage("§6§lPlayerWarp §8» §fVous avez déjà voté pour ce warp.");
                        return;
                    }
                    PlayerWarpNotationGui.instance.openNotationMenu(p, pw);
                }
            }

        }
    }


}
