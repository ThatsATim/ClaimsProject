package me.thatsatim.ClaimsProject.database;

import me.thatsatim.ClaimsProject.ClaimsProject;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Objects;
import java.util.UUID;

public class ClaimsDatabase {

    private static Connection connection;

    public ClaimsDatabase(ClaimsProject plugin) throws SQLException {
        String path = plugin.getDataFolder().getAbsolutePath() + "/claims.db";
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                        CREATE TABLE IF NOT EXISTS claims (
                        chunkID TEXT PRIMARY KEY,
                        owner TEXT);
                    """);
        }
    }

    public static boolean claimChunk(Player player, Chunk chunk) throws SQLException {
        UUID uuid = player.getUniqueId();
        String chunkID = (chunk.getX()) + "," + (chunk.getZ());
        String[] chunkData = getChunk(chunkID);

        if (!(chunkData[0] == null)) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO claims (chunkID, owner) VALUES (?, ?)"
        )) {
            preparedStatement.setString(1, chunkID);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            return true;
        }
    }

    public static boolean unClaimChunk(Player player, Chunk chunk) throws SQLException {
        UUID uuid = player.getUniqueId();
        String chunkID = (chunk.getX()) + "," + (chunk.getZ());
        String[] chunkData = getChunk(chunkID);

        if (!Objects.equals(chunkData[1], uuid.toString())) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM claims WHERE chunkID = ?")) {
            preparedStatement.setString(1, chunkID);
            preparedStatement.executeUpdate();
            return true;
        }

    }

    public static boolean transferChunk(Player owner, Player newOwner, Chunk chunk) throws SQLException {

        if (!ownsChunk(owner, chunk)) { return false; }

        String chunkID = (chunk.getX()) + "," + (chunk.getZ());

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE claims SET owner = ? WHERE chunkID = ?")) {
            preparedStatement.setString(1, newOwner.getUniqueId().toString());
            preparedStatement.setString(2, chunkID);
            preparedStatement.executeUpdate();
            return true;
        }
    }

    public static boolean ownsChunk(Player player, Chunk chunk) throws SQLException {
        UUID uuid = player.getUniqueId();
        String chunkID = (chunk.getX()) + "," + (chunk.getZ());
        String[] chunkData = getChunk(chunkID);

        return Objects.equals(chunkData[1], uuid.toString());
    }

    public static boolean permissionInChunk(Player player, Chunk chunk) throws SQLException {
        UUID uuid = player.getUniqueId();
        String chunkID = (chunk.getX()) + "," + (chunk.getZ());
        String[] chunkData = getChunk(chunkID);

        if (chunkData[0] == null) { return true; }
        return Objects.equals(chunkData[1], uuid.toString());
    }

    private static String[] getChunk(String chunkID) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM claims WHERE chunkID = ?")) {
            preparedStatement.setString(1, chunkID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return new String[]{resultSet.getString("chunkID"), resultSet.getString("owner")};
        }
    }

}