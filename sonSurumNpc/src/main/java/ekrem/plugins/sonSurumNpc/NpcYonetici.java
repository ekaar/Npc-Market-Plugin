package ekrem.plugins.sonSurumNpc;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NpcYonetici implements org.bukkit.command.CommandExecutor, Listener {

    public MenuYonetici menuYonetici = new MenuYonetici();
    public Set<UUID> npcListesi = new HashSet<>();

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender send, org.bukkit.command.Command komut, String label, String[] args) {
        Player oyuncu = (Player) send;
        Location olusmaYeri = oyuncu.getLocation();
        Entity koylu = oyuncu.getWorld().spawnEntity(olusmaYeri, EntityType.VILLAGER);
        koylu.setCustomName(ChatColor.DARK_AQUA.BOLD +"Çavuş");
        koylu.setCustomNameVisible(true);
        LivingEntity koyluNpc = (LivingEntity) koylu;
        koyluNpc.setAI(false);

        npcListesi.add(koylu.getUniqueId());
        oyuncu.sendMessage(ChatColor.DARK_AQUA.BOLD +"NPC çağırıldı");
        return true;
    }

    @EventHandler
    public void koyluClick(PlayerInteractEntityEvent event) {
        Player oyuncu = event.getPlayer();
        Entity sagTiklandi = event.getRightClicked();
        if (npcListesi.contains(sagTiklandi.getUniqueId())) {
            menuYonetici.openMenu(oyuncu);
        } else {
            oyuncu.sendMessage(ChatColor.YELLOW+ "Bir varlığa tıkladın: " +sagTiklandi.getType());
        }
    }

    public String encodeTextureToBase64(String textureUrl) {
        try {
            URL link = new URL(textureUrl);
            return Base64.getEncoder().encodeToString(link.openStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
