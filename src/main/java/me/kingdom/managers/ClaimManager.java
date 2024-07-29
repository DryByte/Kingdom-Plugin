package me.kingdom.managers;

import org.bukkit.Chunk;

import java.util.HashMap;
import java.util.UUID;

public class ClaimManager {
    public HashMap<UUID, Chunk> confirmations;

    public ClaimManager() {
        this.confirmations = new HashMap<>();
    }

    public boolean newConfirmation(UUID uuid, Chunk chunk) {
        if (this.confirmations.containsKey(uuid))
            return false;

        this.confirmations.put(uuid, chunk);
        return true;
    }

    public void removeConfirmation(UUID uuid) {
        this.confirmations.remove(uuid);
    }

    public boolean checkConfirmation(UUID uuid) {
        return this.confirmations.containsKey(uuid);
    }
}
