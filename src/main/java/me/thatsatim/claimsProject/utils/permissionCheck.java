package me.thatsatim.claimsProject.utils;

import me.thatsatim.claimsProject.database.ClaimsDatabase;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class permissionCheck {

    public static boolean isAllowed(Chunk chunk, Player player) {
        try {
            String chunkID = (chunk.getX()) + "," + (chunk.getZ());
            return ClaimsDatabase.permissionInChunk(player, chunkID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
