package main.java.fr.verymc.spigot.core.scoreboard;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import main.java.fr.verymc.spigot.core.evenement.EventManager;
import main.java.fr.verymc.spigot.dungeon.Dungeon;
import main.java.fr.verymc.spigot.dungeon.DungeonManager;
import main.java.fr.verymc.spigot.dungeon.mobs.DungeonMobManager;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.challenges.IslandChallengesReset;
import main.java.fr.verymc.spigot.island.guis.IslandTopGui;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ScoreBoard implements Listener {

    public static ScoreBoard acces;
    public int cPassed = 0;

    public ScoreBoard() {
        acces = this;
    }

    public void setScoreBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("VeryMc", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§6VeryMc");

        obj.getScore("§6play.verymc.fr").setScore(1);
        obj.getScore("§g").setScore(15);
        obj.getScore("§l").setScore(12);
        obj.getScore("§o").setScore(6);
        obj.getScore("§d").setScore(2);

        Team rank = board.registerNewTeam("rank");
        Team money = board.registerNewTeam("money");
        Team gradeis = board.registerNewTeam("gradeis");
        Team classementis = board.registerNewTeam("classementis");
        Team ismembre = board.registerNewTeam("ismembre");
        Team iscrystaux = board.registerNewTeam("iscrystaux");
        Team ismoney = board.registerNewTeam("ismoney");
        Team online = board.registerNewTeam("online");
        Team challenges = board.registerNewTeam("challenges");
        Team event = board.registerNewTeam("event");

        online.addEntry("§k");
        ismoney.addEntry("§9");
        iscrystaux.addEntry("§8");
        ismembre.addEntry("§7");
        classementis.addEntry("§6");
        gradeis.addEntry("§5");
        money.addEntry("§3");
        rank.addEntry("§2");
        challenges.addEntry("§4");
        event.addEntry("§a");

        obj.getScore("§k").setScore(3);
        obj.getScore("§a").setScore(4);
        obj.getScore("§4").setScore(5);
        obj.getScore("§9").setScore(7);
        obj.getScore("§8").setScore(8);
        obj.getScore("§7").setScore(9);
        obj.getScore("§6").setScore(10);
        obj.getScore("§5").setScore(11);
        obj.getScore("§3").setScore(13);
        obj.getScore("§2").setScore(14);

        player.setScoreboard(board);
    }

    public void updateScoreBoard() {
        CompletableFuture.runAsync(() -> {
            ArrayList<String> Vanished = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.getMetadata("vanished").isEmpty()) {
                    Vanished.add(p.getName());
                }
            }
            String cMsg;
            String nextEvent;
            if (cPassed <= 15) {
                long timeBeforeReset = IslandChallengesReset.instance.getTimeBeforeReset();
                cMsg = "§7Reset /c: §c" + EventManager.instance.returnFormattedTime((int) TimeUnit.MILLISECONDS.toSeconds(timeBeforeReset));
            } else {
                cMsg = EventManager.instance.getEventDailyBonus();
                if (cPassed >= 30) {
                    cPassed = -1;
                }
            }
            cPassed++;
            nextEvent = EventManager.instance.getBreakerContest();

            int online = Bukkit.getOnlinePlayers().size() - Vanished.size();
            for (Player player : Bukkit.getOnlinePlayers()) {
                Scoreboard board = player.getScoreboard();

                try {

                    String Grade;
                    User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
                    if (user.getCachedData().getMetaData().getPrefix() != null) {
                        Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
                    } else {
                        Grade = "§fN/A";
                    }

                    if (board.getTeam("rank") != null) {
                        if (Grade.length() < 64) {
                            board.getTeam("rank").setPrefix("§7Grade: " + Grade);
                        } else {
                            board.getTeam("rank").setPrefix("§7Grade: ");
                        }
                    }
                    if (board.getTeam("money") != null) {
                        String a = EcoAccountsManager.instance.moneyGetarrondiNDecimales(player);
                        Objects.requireNonNull(board.getTeam("money")).setPrefix("§7Argent: §2" + a);
                    }
                    if (board.getTeam("online") != null) {
                        Objects.requireNonNull(board.getTeam("online")).setPrefix("§7" + Main.instance.serverName + ": §c" + online);
                    }
                    if (board.getTeam("challenges") != null) {
                        Objects.requireNonNull(Objects.requireNonNull(board.getTeam("challenges"))).setPrefix("§7" + cMsg);
                    }
                    if (board.getTeam("event") != null) {
                        Objects.requireNonNull(board.getTeam("event")).setPrefix("§7" + nextEvent);
                    }

                    if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
                        Island island = IslandManager.instance.getPlayerIsland(player);
                        if (island != null) {
                            String Gradeis = island.getIslandRankFromUUID(player.getUniqueId()).getName();
                            String classement = "#N/A";
                            if (IslandTopGui.instance.getTopIsland().containsKey(island)) {
                                classement = "#" + IslandTopGui.instance.getTopIsland().get(island);
                            }
                            int ismembre = island.getMembers().size();
                            double iscristal = island.getBank().getCrystaux();
                            double ismoney = island.getBank().getMoney();
                            int maxMembers = island.getMaxMembers();


                            if (board.getTeam("gradeis") != null)
                                Objects.requireNonNull(board.getTeam("gradeis")).setPrefix("§7Grade d'ile: §6" + Gradeis);
                            if (board.getTeam("classementis") != null)
                                Objects.requireNonNull(board.getTeam("classementis")).setPrefix("§7Classement: §2" + classement);
                            if (board.getTeam("ismembre") != null)
                                Objects.requireNonNull(board.getTeam("ismembre")).setPrefix("§7Membres: §3" + ismembre + "/" + maxMembers);
                            if (board.getTeam("iscrystaux") != null)
                                Objects.requireNonNull(board.getTeam("iscrystaux")).setPrefix("§7Crystaux: §d" + DecimalFormat.getNumberInstance().format(iscristal));
                            if (board.getTeam("ismoney") != null)
                                Objects.requireNonNull(board.getTeam("ismoney")).setPrefix("§7Argent: §d" +
                                        DecimalFormat.getNumberInstance().format(ismoney));

                        } else {
                            if (board.getTeam("gradeis") != null)
                                Objects.requireNonNull(board.getTeam("gradeis")).setPrefix("§7Grade d'ile: §6N/A");
                            if (board.getTeam("classementis") != null)
                                Objects.requireNonNull(board.getTeam("classementis")).setPrefix("§7Classement: §2N/A");
                            if (board.getTeam("ismembre") != null)
                                Objects.requireNonNull(board.getTeam("ismembre")).setPrefix("§7Membres: §3N/A");
                            if (board.getTeam("iscrystaux") != null)
                                Objects.requireNonNull(board.getTeam("iscrystaux")).setPrefix("§7Crystaux: §dN/A");
                            if (board.getTeam("ismoney") != null)
                                Objects.requireNonNull(Objects.requireNonNull(board.getTeam("ismoney"))).setPrefix("§7Argent: §dN/A");
                        }
                    } else if (Main.instance.serverType == ServerType.SKYBLOCK_DUNGEON) {
                        Dungeon dungeon = DungeonManager.instance.getDungeonByPlayer(player);
                        if (dungeon != null) {
                            if (board.getTeam("gradeis") != null)
                                Objects.requireNonNull(board.getTeam("gradeis")).setPrefix("§7En dungeon: §6" + dungeon.getName() + " (" + dungeon.getFloor().toString() + ")");
                            if (board.getTeam("classementis") != null)
                                Objects.requireNonNull(board.getTeam("classementis")).setPrefix("§7Temps restant: §2" + TimeUnit.MILLISECONDS.toSeconds((
                                        dungeon.getTime_of_start() + TimeUnit.MINUTES.toMillis(dungeon.getDuration_in_minutes())) - System.currentTimeMillis()) + "s");
                            if (board.getTeam("ismembre") != null) {
                                if (DungeonMobManager.instance.mobs.get(dungeon) != null) {
                                    Objects.requireNonNull(board.getTeam("ismembre")).setPrefix("§7Mobs restants: §3" + DungeonMobManager.instance.mobs.get(dungeon).size());
                                } else {
                                    Objects.requireNonNull(board.getTeam("ismembre")).setPrefix("§7Mobs restants: §3N/A");
                                }
                            }
                            if (board.getTeam("iscrystaux") != null)
                                Objects.requireNonNull(board.getTeam("iscrystaux")).setPrefix("§7Boss: §dEn vie");
                            if (board.getTeam("ismoney") != null)
                                Objects.requireNonNull(Objects.requireNonNull(board.getTeam("ismoney"))).setPrefix("§7Alliés en vie: §d" +
                                        (dungeon.getPlayers().size() - dungeon.getDeadPlayers().size()));
                        } else {
                            if (board.getTeam("gradeis") != null)
                                Objects.requireNonNull(board.getTeam("gradeis")).setPrefix("§7En dungeon: §6N/A");
                            if (board.getTeam("classementis") != null)
                                Objects.requireNonNull(board.getTeam("classementis")).setPrefix("§7Temps écoulé: §2N/A");
                            if (board.getTeam("ismembre") != null)
                                Objects.requireNonNull(board.getTeam("ismembre")).setPrefix("§7Mobs restants: §3N/A");
                            if (board.getTeam("iscrystaux") != null)
                                Objects.requireNonNull(board.getTeam("iscrystaux")).setPrefix("§7Boss: §dN/A");
                            if (board.getTeam("ismoney") != null)
                                Objects.requireNonNull(Objects.requireNonNull(board.getTeam("ismoney"))).setPrefix("§7Alliés en vie: §dN/A");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    board.getEntries().clear();
                    board.getEntries().add("§cErreur interne");
                    board.getEntries().add("§cVeuillez contacter un administrateur");
                }
            }
            Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    updateScoreBoard();
                }
            }, 20);
        });
    }
}
