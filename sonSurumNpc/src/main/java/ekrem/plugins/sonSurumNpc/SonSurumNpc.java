package ekrem.plugins.sonSurumNpc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SonSurumNpc extends JavaPlugin {

    @Override
    public void onEnable() {
        NpcYonetici npcYonetici = new NpcYonetici();
        this.getCommand("npccagir").setExecutor(npcYonetici);

        Bukkit.getPluginManager().registerEvents(npcYonetici, this);
        MenuYonetici menuYonetici = new MenuYonetici();
        Bukkit.getPluginManager().registerEvents(menuYonetici, this);
    }

    @Override
    public void onDisable() {
    }
}
