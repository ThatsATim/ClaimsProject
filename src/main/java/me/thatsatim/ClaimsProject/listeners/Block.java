package me.thatsatim.ClaimsProject.listeners;

import me.thatsatim.ClaimsProject.ClaimsProject;
import me.thatsatim.ClaimsProject.database.ClaimsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Block implements Listener {

    public Block(ClaimsProject plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        boolean allowed = isAllowed(event.getBlock().getChunk(), event.getPlayer());
        if (!allowed) { event.setCancelled(true); }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        boolean allowed = isAllowed(event.getBlock().getChunk(), event.getPlayer());
        if (!allowed) { event.setCancelled(true); }
    }

    private boolean isAllowed(Chunk chunk, Player player) {
        try {
            return ClaimsDatabase.permissionInChunk(player, chunk);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}