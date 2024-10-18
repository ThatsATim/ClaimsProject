package me.thatsatim.claimsProject.listeners;

import me.thatsatim.claimsProject.ClaimsProject;
import me.thatsatim.claimsProject.utils.permissionCheck;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHit implements Listener {

    public PlayerHit(ClaimsProject plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) { return; }
        Player player = (Player) event.getDamager();
        Chunk chunk = event.getEntity().getChunk();
        if (permissionCheck.isAllowed(chunk, player)) { return; }
        event.setCancelled(true);
    }



}
