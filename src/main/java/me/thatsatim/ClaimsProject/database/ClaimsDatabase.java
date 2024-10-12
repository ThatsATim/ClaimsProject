package me.thatsatim.ClaimsProject.database;

import me.thatsatim.ClaimsProject.ClaimsProject;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class ClaimsDatabase {

    private static Connection connection;

    public ClaimsDatabase(ClaimsProject plugin) throws SQLException {
        String path = plugin.getDataFolder().getAbsolutePath() + "/claims.db";
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                        CREATE TABLE IF NOT EXISTS claims (
                        uuid TEXT PRIMARY KEY,
                        chunk TEXT);
                    """);
        }
    }

    public static void claimChunk(Player player, Chunk chunk) throws SQLException {
        UUID uuid = player.getUniqueId();
        String chunkID = (chunk.getX()) + "," + (chunk.getZ());
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO claims (uuid, chunk) VALUES (?, ?)"
        )) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, chunkID);
            preparedStatement.executeUpdate();
        }
    }
}
