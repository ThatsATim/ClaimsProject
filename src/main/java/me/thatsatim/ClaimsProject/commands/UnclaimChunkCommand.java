package me.thatsatim.ClaimsProject.commands;

import me.thatsatim.ClaimsProject.ClaimsProject;
import me.thatsatim.ClaimsProject.database.ClaimsDatabase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class UnclaimChunkCommand implements CommandExecutor {

    public UnclaimChunkCommand(ClaimsProject plugin) {
        plugin.getCommand("unclaim").setExecutor(this);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Chunks can not be managed by the console");
            return true;
        }

        Player player = (Player) sender;

        try {
            boolean successful = ClaimsDatabase.unClaimChunk(player, player.getChunk());
            if (!successful) {
                player.sendMessage("Could not unclaim this chunk!");
                return true;
            }
            player.sendMessage("Unclaimed Chunk Successfully");
            return true;
        } catch (SQLException e) {
            player.sendMessage("An error occurred while unclaiming a chunk");
            throw new RuntimeException(e);
        }

    }
}
