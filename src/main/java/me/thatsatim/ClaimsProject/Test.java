package me.thatsatim.ClaimsProject;

import me.thatsatim.ClaimsProject.database.ClaimsDatabase;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class Test implements CommandExecutor {

    public Test(ClaimsProject plugin) {
        plugin.getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        if (!(sender instanceof Player)) {return true;}

        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getBlock().getChunk();

        try {
            ClaimsDatabase.claimChunk(player, chunk);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}