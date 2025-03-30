package ekrem.plugins.sonSurumNpc;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ParaAlisVeris {

    public static Economy economy = null;
    public static boolean setupEconomy() {
        if (economy == null) {
            economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        }
        return economy != null;
    }
    public static boolean ParaCekme(Player player, long bakiye) {
        if (economy == null) {
            return false;
        }
        double currentBalance = OyuncuDatalar.getBalance(player); if (currentBalance < bakiye) {
            return false;
        }
        OyuncuDatalar.setBalance(player, currentBalance - bakiye);
        economy.withdrawPlayer(player, bakiye);
        return true;
    }

    public static boolean ParaYatirma(Player player, long bakiye) { if (economy == null) {
            return false;
        }

        double currentBalance = OyuncuDatalar.getBalance(player);
        OyuncuDatalar.setBalance(player, currentBalance + bakiye);
        economy.depositPlayer(player, bakiye);
        return true;
    }
}
