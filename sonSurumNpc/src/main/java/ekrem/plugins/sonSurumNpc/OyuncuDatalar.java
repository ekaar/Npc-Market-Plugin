package ekrem.plugins.sonSurumNpc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class OyuncuDatalar {
    private static SonSurumNpc plugin = SonSurumNpc.getPlugin(SonSurumNpc.class);
    public static void setBalance(Player player, double balance) {
        FileConfiguration config = plugin.getConfig();
        config.set("players." + player.getUniqueId() + ".balance", balance);
        plugin.saveConfig();
    }
    public static double getBalance(Player player) {
        FileConfiguration config = plugin.getConfig();
        return config.getDouble("players." + player.getUniqueId() + ".balance", 0);
    }
}
