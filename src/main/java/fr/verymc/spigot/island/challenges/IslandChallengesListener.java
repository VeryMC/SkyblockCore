package main.java.fr.verymc.spigot.island.challenges;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.evenement.BlocBreakerContest;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class IslandChallengesListener implements Listener {

    public static int cobble = 96;
    public static int coal = 48;
    public static int iron = 64;
    public static int gold = 32;
    public static int diamond = 64;
    public static int emerald = 16;
    public static int debris = 16;

    public static int oak_log = 32;
    public static int birch_log = 32;
    public static int spruce_log = 32;
    public static int dark_oak_log = 32;
    public static int acacia_log = 32;
    public static int jungle_log = 32;
    public static int wheat = 64;
    public static int pumpkin = 64;
    public static int melon = 64;
    public static int cocoa = 64;
    public static int nether_wart = 64;
    public static int carrot = 64;
    public static int potato = 64;

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        e.getBlock().setMetadata("placed", new FixedMetadataValue(Main.instance, e.getPlayer().getName()));
    }

    @EventHandler
    public void BlockForm(BlockFormEvent e) {
        if (e.getBlock().getMetadata("placed") != null) {
            e.getBlock().removeMetadata("placed", Main.instance);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Material currenttype = e.getBlock().getType();
        Block block = e.getBlock();
        if (block.hasMetadata("placed")) {
            if (!(block instanceof Ageable)) return;
            final Ageable ageable = (Ageable) block.getState().getBlockData();
            int age = ageable.getAge();
            if (age != ageable.getMaximumAge()) {
                return;
            }
        }
        if (!e.isCancelled()) {
            if (BlocBreakerContest.instance.isActive) {
                if (e.getBlock().getType().equals(BlocBreakerContest.instance.material)) {
                    BlocBreakerContest.instance.addBlock(player.getUniqueId());
                }
            }
            if (!IslandManager.instance.asAnIsland(player)) {
                return;
            }
            Island playerIsland = IslandManager.instance.getPlayerIsland(player);
            IslandChallenge challenge = null;

            for (IslandChallenge c : playerIsland.getChallenges()) {
                if (c.getMaterial() == currenttype) {
                    challenge = c;
                    break;
                }
            }
            if (challenge == null) {
                return;
            }

            if (!challenge.isActive()) {
                return;
            }

            challenge.addProgress(1);

            if (challenge.getProgress() >= challenge.getMaxProgress() * (challenge.getPalier() + 1) * playerIsland.getMembers().size()) {
                challenge.setProgress(0);
                challenge.setPalier(challenge.getPalier() + 1);
                IslandChallengesGuis.instance.completeChallenge(player, challenge);
            }
        }
    }
}
