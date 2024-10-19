package me.thatsatim.claimsProject.commands;

import me.thatsatim.claimsProject.ClaimsProject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportToChunk implements CommandExecutor {

    public TeleportToChunk(ClaimsProject plugin) {
        plugin.getCommand("teleportToChunk").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if (!(sender instanceof Player)) {
            sender.sendPlainMessage("Chunks can not be managed by the console");
            return true;
        }

        Player player = (Player) sender;
        String chunkID = arguments[0];
        String[] coords = chunkID.split(",");
        int chunkX = Integer.parseInt(coords[0]);
        int chunkZ = Integer.parseInt(coords[1]);

        player.teleport(player.getWorld().getHighestBlockAt(chunkX * 16 + 8, chunkZ * 16 + 8).getLocation());
        return true;
    }
}
