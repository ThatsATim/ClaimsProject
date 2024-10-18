package me.thatsatim.claimsProject.listeners;

import me.thatsatim.claimsProject.ClaimsProject;
import me.thatsatim.claimsProject.utils.permissionCheck;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    public PlayerInteract(ClaimsProject plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;

        Player player = event.getPlayer();
        Chunk chunk = event.getClickedBlock().getChunk();

        if (permissionCheck.isAllowed(chunk, player)) return;

        Material[] types = {Material.CHEST, Material.TRAPPED_CHEST, Material.BARREL, Material.FURNACE, Material.HOPPER,
                Material.CHISELED_BOOKSHELF, Material.SHULKER_BOX, Material.BREWING_STAND, Material.BLAST_FURNACE,
                Material.DROPPER, Material.DISPENSER, Material.LECTERN, Material.JUKEBOX, Material.CHEST_MINECART,};

        for (Material type : types) {
            if (event.getClickedBlock().getType() == type) {
                event.setCancelled(true);
            }
        }
    }

}
