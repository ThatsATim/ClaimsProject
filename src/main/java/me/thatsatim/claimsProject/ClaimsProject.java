package me.thatsatim.claimsProject;

import me.thatsatim.claimsProject.commands.ClaimChunkCommand;
import me.thatsatim.claimsProject.commands.TransferChunkCommand;
import me.thatsatim.claimsProject.commands.UnclaimChunkCommand;
import me.thatsatim.claimsProject.database.ClaimsDatabase;
import me.thatsatim.claimsProject.listeners.ArmorStandManipulate;
import me.thatsatim.claimsProject.listeners.Block;
import me.thatsatim.claimsProject.listeners.PlayerHit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class ClaimsProject extends JavaPlugin {

    @Override
    public void onEnable() {

        // -- Register other classes
        // Commands
        new ClaimChunkCommand(this);
        new UnclaimChunkCommand(this);
        new TransferChunkCommand(this);

        // Listeners
        new Block(this);
        new PlayerHit(this);
        new ArmorStandManipulate(this);

        // -- Create the database connection
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            ClaimsDatabase claimsDatabase = new ClaimsDatabase(this);
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Failed to connect to the database! " + exception.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
