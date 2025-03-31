package ekrem.plugins.sonSurumNpc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SonSurumNpc extends JavaPlugin implements Listener {

    public static SonSurumNpc instance;
    private static boolean economyInitialized = false;

    @Override
    public void onEnable() {
        instance = this;
        NpcYonetici npcYonetici = new NpcYonetici();
        this.getCommand("npccagir").setExecutor(npcYonetici);
        Bukkit.getPluginManager().registerEvents(npcYonetici, this);

        MenuYonetici menuYonetici = new MenuYonetici();
        Bukkit.getPluginManager().registerEvents(menuYonetici, this);

        if (setupEconomy()) {
            getLogger().info("Economy basladı");
        } else {
            getLogger().warning("Economy baslayamadı");
        }
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    public static boolean setupEconomy() {
        if (economyInitialized) {
            return true;
        }
        economyInitialized = ParaAlisVeris.setupEconomy();
        return economyInitialized;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        getLogger().info(player.getName()+" sunucuya girdi, scoreboard açılacak");
        if (economyInitialized) {
            ParaAlisVeris.panelBoard(player);
        }}
    @Override
    public void onDisable() {
    }
    public static SonSurumNpc getInstance() {
        return instance;
    }
}
