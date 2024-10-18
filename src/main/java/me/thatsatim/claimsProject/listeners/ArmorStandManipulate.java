package me.thatsatim.claimsProject.listeners;

import me.thatsatim.claimsProject.ClaimsProject;
import me.thatsatim.claimsProject.utils.permissionCheck;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class ArmorStandManipulate implements Listener {

    public ArmorStandManipulate(ClaimsProject plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = event.getRightClicked().getChunk();
        if (permissionCheck.isAllowed(chunk, player)) { return; }
        event.setCancelled(true);
    }

}
