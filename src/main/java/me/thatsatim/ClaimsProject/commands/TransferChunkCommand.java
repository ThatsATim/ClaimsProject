package me.thatsatim.ClaimsProject.commands;

import me.thatsatim.ClaimsProject.ClaimsProject;
import me.thatsatim.ClaimsProject.database.ClaimsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class TransferChunkCommand implements CommandExecutor {

    public TransferChunkCommand(ClaimsProject plugin) {
        plugin.getCommand("transfer").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Chunks can not be managed by the console");
            return true;
        }

        if (arguments.length != 1) {
            sender.sendMessage("Wrong command usage");
            return true;
        }

        Player owner = (Player) sender;
        Player newOwner = Bukkit.getPlayerExact(arguments[0]);

        try {
            boolean successful = ClaimsDatabase.transferChunk(owner, newOwner, owner.getChunk());
            if (!successful) {
                sender.sendMessage("Could not transfer chunk");
                return true;
            }
            sender.sendMessage("Successfully transferred chunk");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
