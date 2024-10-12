package me.thatsatim.ClaimsProject;

import me.thatsatim.ClaimsProject.database.ClaimsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class ClaimsProject extends JavaPlugin {

    @Override
    public void onEnable() {
        new Test(this);

        // Create the database connection
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
