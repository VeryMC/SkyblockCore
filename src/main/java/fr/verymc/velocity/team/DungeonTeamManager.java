package main.java.fr.verymc.velocity.team;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import main.java.fr.verymc.spigot.dungeon.DungeonFloors;
import main.java.fr.verymc.velocity.Main;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DungeonTeamManager {

    public static DungeonTeamManager instance;
    private final ProxyServer server;
    private final Logger logger;
    public ArrayList<DungeonTeam> dungeonTeams = new ArrayList<>();
    public ArrayList<UUID> playerConfirmation = new ArrayList<>();

    public DungeonTeamManager(ProxyServer server, Logger logger) {
        instance = this;

        this.server = server;
        this.logger = logger;
    }

    public DungeonTeam getPlayerTeam(Player player) {
        for (DungeonTeam team : dungeonTeams) {
            if (team.isPlayerInTeam(player)) {
                return team;
            }
        }
        return null;
    }

    public boolean isInATeam(Player player) {
        DungeonTeam dungeonTeam = getPlayerTeam(player);
        if (dungeonTeam == null) {
            return false;
        }
        return true;
    }

    public void addTeam(DungeonTeam team) {
        dungeonTeams.add(team);
    }

    public void removeTeam(DungeonTeam team) {
        dungeonTeams.remove(team);
    }

    public void removePlayerFromTeam(DungeonTeam dungeonTeam, Player player) {
        if (dungeonTeam.isPlayerInTeam(player)) {
            dungeonTeam.removePlayer(player);
        }
    }

    public void addPlayerToTeam(DungeonTeam dungeonTeam, Player player) {
        if (!dungeonTeam.isPlayerInTeam(player)) {
            dungeonTeam.addPlayer(player);
        }
    }

    public void addPlayerToConfirmation(Player player) {
        playerConfirmation.add(player.getUniqueId());
        server.getScheduler()
                .buildTask(Main.instance, () -> {
                    if (playerConfirmation.contains(player.getUniqueId())) {
                        playerConfirmation.remove(player.getUniqueId());
                        player.sendMessage(Component.text("§6§lDungeon §8» §fVotre demande de suppression a été annulé."));
                    }
                })
                .delay(10L, TimeUnit.SECONDS)
                .schedule();
    }

    public boolean isPlayerInConfirmation(Player player) {
        return playerConfirmation.contains(player.getUniqueId());
    }

    public void removePlayerFromConfirmation(Player player) {
        playerConfirmation.remove(player.getUniqueId());
    }

    public boolean isATeam(DungeonTeam dungeonTeam) {
        return dungeonTeams.contains(dungeonTeam);
    }

    public void makeInviteExpireForPlayer(Player player, DungeonTeam dungeonTeam) {
        server.getScheduler()
                .buildTask(Main.instance, () -> {
                    if (DungeonTeamManager.instance.isATeam(dungeonTeam)) {
                        if (dungeonTeam.isPendingInvite(player.getUniqueId())) {
                            dungeonTeam.removePendingInvite(player.getUniqueId());
                        }
                    }
                })
                .repeat(30L, TimeUnit.SECONDS)
                .schedule();
    }

    public void createTeam(Player player) {
        addTeam(new DungeonTeam(new ArrayList<>(Collections.singletonList(player)), player.getUniqueId(), DungeonFloors.FLOOR_1, true));
    }

}
