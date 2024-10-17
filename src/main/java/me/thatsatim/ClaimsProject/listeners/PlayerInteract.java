package me.thatsatim.ClaimsProject.listeners;

import me.thatsatim.ClaimsProject.ClaimsProject;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    public PlayerInteract(ClaimsProject plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.getPlayer().sendMessage(String.valueOf(event.getAction()));
    }
}
