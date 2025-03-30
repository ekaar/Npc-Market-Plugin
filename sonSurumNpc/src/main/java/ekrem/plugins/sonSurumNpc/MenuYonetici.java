package ekrem.plugins.sonSurumNpc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MenuYonetici implements Listener {

    private static final String MENU_BASLIK ="Market";

    private static final List<Object[]> ESYA_LISTESI = List.of(
            new Object[]{Material.BEDROCK,999999,500},
            new Object[]{Material.NETHERITE_INGOT,5000,2500},
            new Object[]{Material.COAL,50,20},
            new Object[]{Material.PAPER,20,5},
            new Object[]{Material.DIAMOND,500,200},
            new Object[]{Material.IRON_INGOT,200,80},
            new Object[]{Material.GOLD_INGOT,300,120},
            new Object[]{Material.GUNPOWDER,40,15},
            new Object[]{Material.STRING,30,10}
    );

    public void openMenu(Player oyuncu) {
        Inventory menu = Bukkit.createInventory(null, 18, MENU_BASLIK);
        for (Object[] esya : ESYA_LISTESI) {
            menu.addItem(esyaOlustur((Material) esya[0], (int) esya[1], (int) esya[2]));
        }

        oyuncu.openInventory(menu);
        oyuncu.sendMessage(ChatColor.GRAY +"Menü Açıldı");
    }

    private ItemStack esyaOlustur(Material malzeme, int alisFiyati, int satisFiyati) {
        ItemStack item = new ItemStack(malzeme);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + malzeme.name());
        itemMeta.setLore(List.of(
                ChatColor.GREEN +"Sağ Tık: " + ChatColor.YELLOW + alisFiyati +"$ satın al",
                ChatColor.RED + "Sol Tık: " + ChatColor.YELLOW + satisFiyati +"sat"));
        item.setItemMeta(itemMeta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(MENU_BASLIK) || event.getCurrentItem() == null) return;
        event.setCancelled(true);

        Player oyuncu = (Player) event.getWhoClicked();
        Material tiklanan = event.getCurrentItem().getType();

        Object[] secilenEsya = null;
        for (Object[] esya : ESYA_LISTESI) {
            if (esya[0] == tiklanan) {
                secilenEsya = esya;
                break;
            }
        }
        int alisFiyati = (int) secilenEsya[1];
        int satisFiyati = (int) secilenEsya[2];
        if (oyuncu.getTargetEntity(5) instanceof org.bukkit.entity.Villager) {
            org.bukkit.entity.Villager villager = (org.bukkit.entity.Villager) oyuncu.getTargetEntity(5); villager.setAI(false);
        }

        if (event.isRightClick()) {
            if (ParaAlisVeris.ParaCekme(oyuncu, alisFiyati)) {
                oyuncu.getInventory().addItem(new ItemStack(tiklanan));
                OyuncuDatalar.setBalance(oyuncu, ParaAlisVeris.economy.getBalance(oyuncu));  // YML kaydetme
                oyuncu.sendMessage(ChatColor.GREEN + "" + alisFiyati + "$ karşılığında " + tiklanan.name() + " satın aldın!");
            } else {
                oyuncu.sendMessage(ChatColor.DARK_RED +"Yeterli paran yok!");
            }
        } else if (event.isLeftClick()) {
            if (oyuncu.getInventory().contains(tiklanan)) {
                oyuncu.getInventory().removeItem(new ItemStack(tiklanan, 1));
                ParaAlisVeris.ParaYatirma(oyuncu, satisFiyati);
                OyuncuDatalar.setBalance(oyuncu, ParaAlisVeris.economy.getBalance(oyuncu));  // YML kaydetme
                oyuncu.sendMessage(ChatColor.DARK_AQUA + "1 " + tiklanan.name() +" sattın, " + satisFiyati + "$ kazandın!");
            } else {
                oyuncu.sendMessage(ChatColor.RED +"Envanterinde bu eşyadan yok!");
            }
        }
    }
}
