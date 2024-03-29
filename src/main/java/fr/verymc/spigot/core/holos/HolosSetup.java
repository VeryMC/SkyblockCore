package main.java.fr.verymc.spigot.core.holos;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.gui.MenuGui;
import main.java.fr.verymc.spigot.core.shopgui.MainShopGui;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HolosSetup implements Listener {

    static NPC npca = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§6Shop");
    static NPC npcb = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§6Menu du skyblock");

    static Location holo1 = new Location(Main.instance.mainWorld, -33.5, 101, -130.5);
    static Location legende = new Location(Main.instance.mainWorld, -196.5, 75, -63.5);
    static Location dieu = new Location(Main.instance.mainWorld, -194.5, 75, -56.5);
    static Location zeus = new Location(Main.instance.mainWorld, -187.5, 75, -54.5);
    static Hologram hologram1 = HologramsAPI.createHologram(Main.instance, holo1);
    static Hologram hololegende = HologramsAPI.createHologram(Main.instance, legende);
    static Hologram holodieu = HologramsAPI.createHologram(Main.instance, dieu);
    static Hologram holozeus = HologramsAPI.createHologram(Main.instance, zeus);

    public static void SpawnPnj2(Location loc1, Location loc2) {
        SkinTrait skin = npca.getTrait(SkinTrait.class);
        npca.setAlwaysUseNameHologram(false);
        npca.setProtected(true);
        npca.setFlyable(true);
        skin.setSkinName("mairekl");
        npca.spawn(loc1);

        SkinTrait skina = npcb.getTrait(SkinTrait.class);
        npcb.setAlwaysUseNameHologram(false);
        npcb.setProtected(true);
        npcb.setFlyable(true);
        skina.setSkinName("Farmeurimmo");
        npcb.spawn(loc2);
    }

    public static void SpawnCrates() {
        hololegende.appendTextLine("§c§lPREMIER GRADE");
        hololegende.appendTextLine("");
        hololegende.appendTextLine("§6§lPRIX IG: §e§l2 500 000$");
        hololegende.appendTextLine("§6§lPRIX BOUTIQUE: §e§lNon achetable");
        hololegende.appendTextLine("");
        hololegende.appendTextLine("§6§lAvantages du Grade §e§lLégende");
        hololegende.appendTextLine("");
        hololegende.appendTextLine("§c§mAccès au /fly permanant");
        hololegende.appendTextLine("§a90 minutes de fly journalier /dailyfly");
        hololegende.appendTextLine("§aDélai de téléportation de §c§m§l5§f §6§l➞ §2§l3 §asecondes");
        hololegende.appendTextLine("§aAccès au shop spawneurs");
        hololegende.appendTextLine("§aAccès au /craft");
        hololegende.appendTextLine("§aAccès au /feed");
        hololegende.appendTextLine("§c§mAccès au /hat");
        hololegende.appendTextLine("§c§mAccès au /enchantement");
        hololegende.appendTextLine("§c§mAccès au /is rename");
        hololegende.appendTextLine("§c§mAccès au /sellall");
        hololegende.appendTextLine("§aAccès au /claim §elegende");

        holodieu.appendTextLine("§c§lGRADE INTERMÉDIAIRE");
        holodieu.appendTextLine("");
        holodieu.appendTextLine("§6§lPRIX IG: §e§l7 500 000$");
        holodieu.appendTextLine("§6§lPRIX BOUTIQUE: §e§lNon achetable");
        holodieu.appendTextLine("");
        holodieu.appendTextLine("§6§lAvantages du Grade §9§lDieu");
        holodieu.appendTextLine("");
        holodieu.appendTextLine("§c§mAccès au /fly permanant");
        holodieu.appendTextLine("§c§m§l90§f §6§l➞ §2§l180 §aminutes de fly journalier /dailyfly");
        holodieu.appendTextLine("§aDélai de téléportation de §c§m§l5§f §6§l➞ §2§l1 §asecondes");
        holodieu.appendTextLine("§aAccès au shop spawneurs");
        holodieu.appendTextLine("§aAccès au /craft");
        holodieu.appendTextLine("§aAccès au /feed");
        holodieu.appendTextLine("§aAccès au /hat");
        holodieu.appendTextLine("§aAccès au /enchantement");
        holodieu.appendTextLine("§c§mAccès au /is rename");
        holodieu.appendTextLine("§c§mAccès au /sellall");
        holodieu.appendTextLine("§aAccès au /claim §9dieu§a, §elegende");

        holozeus.appendTextLine("§c§lMEILLEUR GRADE");
        holozeus.appendTextLine("");
        holozeus.appendTextLine("§6§lPRIX IG: §e§l14 000 000$");
        holozeus.appendTextLine("§6§lPRIX BOUTIQUE: §e§lNon achetable");
        holozeus.appendTextLine("");
        holozeus.appendTextLine("§6§lAvantages du Grade §b§lZeus");
        holozeus.appendTextLine("");
        holozeus.appendTextLine("§aAccès au /fly permanant");
        holozeus.appendTextLine("§c§m180 minutes de fly journalier /dailyfly");
        holozeus.appendTextLine("§aDélai de téléportation de §c§m§L5§f §6§l➞ §2§linstantané");
        holozeus.appendTextLine("§aAccès au shop spawneurs");
        holozeus.appendTextLine("§aAccès au /craft");
        holozeus.appendTextLine("§aAccès au /feed");
        holozeus.appendTextLine("§aAccès au /hat");
        holozeus.appendTextLine("§aAccès au /enchantement");
        holozeus.appendTextLine("§aAccès au /is rename");
        holozeus.appendTextLine("§aAccès au /sellall");
        holozeus.appendTextLine("§aAccès au /claim §bzeus§a, §9dieu§a, §elegende");

        hologram1.appendTextLine("§6Zone Enchantmenents");
        hologram1.appendTextLine("§7Vous souhaitez rajouter un peu piquant à votre stuff?");
        hologram1.appendTextLine("§7Cette zone est pour vous !");
    }

    @EventHandler
    public void OnInteractWithNPC(NPCRightClickEvent e) {
        Player player = e.getClicker();
        if (e.getNPC().getName().equalsIgnoreCase("§6Shop")) {
            MainShopGui.OpenMainShopMenu(player);
            return;
        }
        if (e.getNPC().getName().equalsIgnoreCase("§6Menu du skyblock")) {
            MenuGui.OpenMainMenu(player);
            return;
        }
    }
}
