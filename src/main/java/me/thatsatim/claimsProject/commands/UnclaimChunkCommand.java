package me.thatsatim.claimsProject.commands;

import me.thatsatim.claimsProject.ClaimsProject;
import me.thatsatim.claimsProject.database.ClaimsDatabase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class UnclaimChunkCommand implements CommandExecutor {

    public UnclaimChunkCommand(ClaimsProject plugin) {
        plugin.getCommand("unclaim").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {

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
