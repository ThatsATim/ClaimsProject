package me.thatsatim.claimsProject.commands;

import me.thatsatim.claimsProject.ClaimsProject;
import me.thatsatim.claimsProject.database.ClaimsDatabase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ClaimChunkCommand implements CommandExecutor {

    public ClaimChunkCommand(ClaimsProject plugin) {
        plugin.getCommand("claim").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {

        if (!(sender instanceof Player)) {
            sender.sendPlainMessage("Chunks can not be managed by the console");
            return true;
        }

        Player player = (Player) sender;

        try {
            boolean successful = ClaimsDatabase.claimChunk(player, player.getChunk());
            if (!successful) {
                sender.sendMessage("Could not claim chunk");
                return true;
            }
            sender.sendMessage("Successfully claimed chunk");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
