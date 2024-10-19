package me.thatsatim.claimsProject.database;

import me.thatsatim.claimsProject.ClaimsProject;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        if (!(chunkData[0] == null)) return false;

        if (checkForExistingClaim(player)) {
            String[] chunkIDs = {
                    chunk.getX() + 1 + "," + (chunk.getZ()),
                    chunk.getX() + "," + (chunk.getZ() + 1),
                    chunk.getX() - 1 + "," + (chunk.getZ()),
                    chunk.getX() + "," + (chunk.getZ() - 1)
            };
            boolean isOwner = false;
            for (String ID : chunkIDs) {
                isOwner = ownsChunk(player, ID);
                if (isOwner) break;
            }
            if (!isOwner) return false;
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
        if (!Objects.equals(chunkData[1], uuid.toString())) return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM claims WHERE chunkID = ?")) {
            preparedStatement.setString(1, chunkID);
            preparedStatement.executeUpdate();
            return true;
        }
    }

    public static boolean transferChunk(Player owner, Player newOwner, Chunk chunk) throws SQLException {
        String chunkID = (chunk.getX()) + "," + (chunk.getZ());
        if (!ownsChunk(owner, chunkID)) return false;
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE claims SET owner = ? WHERE chunkID = ?")) {
            preparedStatement.setString(1, newOwner.getUniqueId().toString());
            preparedStatement.setString(2, chunkID);
            preparedStatement.executeUpdate();
            return true;
        }
    }

    public static boolean permissionInChunk(Player player, String chunkID) throws SQLException {
        UUID uuid = player.getUniqueId();
        String[] chunkData = getChunk(chunkID);
        if (chunkData[0] == null) return true;
        return Objects.equals(chunkData[1], uuid.toString());
    }

    private static String[] getChunk(String chunkID) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM claims WHERE chunkID = ?")) {
            preparedStatement.setString(1, chunkID);
            ResultSet resultSet = preparedStatement.executeQuery();
            return new String[]{resultSet.getString("chunkID"), resultSet.getString("owner")};
        }
    }

    private static ArrayList<String[]> getChunks(Player player) throws SQLException {
        UUID uuid = player.getUniqueId();
        ArrayList<String[]> chunkData = new ArrayList<String[]>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM claims WHERE owner = ? LIMIT 100")) {
            preparedStatement.setString(1, String.valueOf(uuid));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                chunkData.add(new String[]{resultSet.getString("chunkID"), resultSet.getString("owner")});
            }
        }
        return chunkData;
    }

    private static boolean ownsChunk(Player player, String chunkID) throws SQLException {
        UUID uuid = player.getUniqueId();
        String[] chunkData = getChunk(chunkID);
        return Objects.equals(chunkData[1], uuid.toString());
    }

    private static boolean checkForExistingClaim(Player player) throws SQLException {
        UUID uuid = player.getUniqueId();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM claims WHERE owner = ?"
        )) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            String owner = resultSet.getString("owner");
            return owner != null;
        }
    }
}