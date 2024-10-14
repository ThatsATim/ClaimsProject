package me.thatsatim.ClaimsProject.commands;

import me.thatsatim.ClaimsProject.ClaimsProject;
import me.thatsatim.ClaimsProject.database.ClaimsDatabase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class ClaimChunkCommand implements CommandExecutor {

    public ClaimChunkCommand(ClaimsProject plugin) {
        plugin.getCommand("claim").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Chunks can not be managed by the console");
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
