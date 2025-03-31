package ekrem.plugins.sonSurumNpc;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;
import org.bukkit.scheduler.BukkitRunnable;

public class ParaAlisVeris {

    public static Economy economy = null;

    public static boolean setupEconomy() {
        if (economy == null) {
            economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        }
        return economy != null;
    }

    public static boolean ParaCekme(Player player, long bakiye) {
        if (economy == null || economy.getBalance(player) < bakiye) return false;
        economy.withdrawPlayer(player, bakiye);
        guncelleScoreboard(player);
        return true;
    }

    public static boolean ParaYatirma(Player player, long bakiye) {
        if (economy == null) return false;
        economy.depositPlayer(player, bakiye);
        guncelleScoreboard(player);
        return true;
    }

    public static void guncelleScoreboard(Player p) {
        if (p.getScoreboard() == null) return;
        Scoreboard board = p.getScoreboard();
        Objective obj = board.getObjective("Balance");
        if (obj == null) return;
        String para = ChatColor.BLUE + " " + getBakiye(p) + " ";
        board.resetScores(para);
        obj.getScore(para).setScore(1);
    }

    public static double getBakiye(Player p) {
        if (economy != null) {
            return economy.getBalance(p);
        } else {
            return 0.0;
        }
    }
    public static void panelBoard(Player p) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if (scoreboardManager == null)
            p.sendMessage("ScoreboardManager is null!");
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("Balance", "dummy", ChatColor.DARK_AQUA + "Para Bilgisi");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score paraBaslik = objective.getScore(ChatColor.WHITE + "Bakiye:");
        paraBaslik.setScore(2);

        String para = ChatColor.BLUE + " " + getBakiye(p) + " ";
        Score paraMiktar = objective.getScore(para);
        paraMiktar.setScore(1);

        p.setScoreboard(scoreboard);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    cancel();
                    return;
                }
                guncelleScoreboard(p);
            }
        }.runTaskTimer(ekrem.plugins.sonSurumNpc.SonSurumNpc.getInstance(), 0L, 75L);
    }
}
