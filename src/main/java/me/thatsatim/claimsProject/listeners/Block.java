package me.thatsatim.claimsProject.listeners;

import me.thatsatim.claimsProject.ClaimsProject;
import me.thatsatim.claimsProject.utils.permissionCheck;
import org.bukkit.Bukkit;
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
        boolean allowed = permissionCheck.isAllowed((event.getBlock().getChunk()), event.getPlayer());
        if (!allowed) { event.setCancelled(true); }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        boolean allowed = permissionCheck.isAllowed((event.getBlock().getChunk()), event.getPlayer());
        if (!allowed) { event.setCancelled(true); }
    }

}