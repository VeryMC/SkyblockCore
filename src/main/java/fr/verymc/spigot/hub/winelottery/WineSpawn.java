package main.java.fr.verymc.spigot.hub.winelottery;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class WineSpawn {

    static NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§6Barman");

    public static void SpawnPnj(Location loc) {
        SkinTrait skin = npc.getTrait(SkinTrait.class);
        npc.setAlwaysUseNameHologram(false);
        npc.setProtected(true);
        npc.setFlyable(true);
        skin.setSkinName("MANDOWNS");
        npc.spawn(loc);
    }
}
