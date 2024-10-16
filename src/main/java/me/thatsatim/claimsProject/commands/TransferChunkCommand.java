package me.thatsatim.claimsProject.commands;

import me.thatsatim.claimsProject.ClaimsProject;
import me.thatsatim.claimsProject.database.ClaimsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class TransferChunkCommand implements CommandExecutor {

    public TransferChunkCommand(ClaimsProject plugin) {
        plugin.getCommand("transfer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {

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
