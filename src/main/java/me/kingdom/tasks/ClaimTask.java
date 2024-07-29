package me.kingdom.tasks;

import me.kingdom.managers.ClaimManager;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class ClaimTask extends BukkitRunnable {
    private final Player player;
    private final Chunk chunk;
    private final long start_timestamp;
    private long last_message_ts;
    private final ClaimManager claimManager;

    public ClaimTask(ClaimManager claimManager, Player player) {
        this.player = player;
        this.chunk = player.getChunk();
        this.claimManager = claimManager;

        this.start_timestamp = new Date().getTime();
        this.last_message_ts = 0;
    }

    public void run() {
        if (!this.claimManager.checkConfirmation(this.player.getUniqueId())) {
            this.cancel();
            return;
        }

        long ts = new Date().getTime();
        if (ts - this.start_timestamp > 30*1000) {
            this.player.sendMessage("You took too long to confirm the claim... Cancelling claim request...");
            this.claimManager.removeConfirmation(player.getUniqueId());

            this.cancel();
            return;
        }

        if (ts - this.last_message_ts >= 10*1000) {
            this.last_message_ts = ts;
            this.player.sendMessage("Use /claim confirm to confirm the chunk claim.");
        }

        int y = this.player.getLocation().getBlockY()+2;

        for (int x = chunk.getX()*16; x < chunk.getX()*16+16; x++) {
            player.spawnParticle(Particle.REDSTONE, x, y, chunk.getZ()*16, 50, new Particle.DustOptions(Color.AQUA,2));
            player.spawnParticle(Particle.REDSTONE, x, y, chunk.getZ()*16+16, 50, new Particle.DustOptions(Color.RED, 2));
        }

        for (int z = chunk.getZ()*16; z < chunk.getZ()*16+16; z++) {
            player.spawnParticle(Particle.REDSTONE, chunk.getX()*16, y, z, 50, new Particle.DustOptions(Color.AQUA,2));
            player.spawnParticle(Particle.REDSTONE, chunk.getX()*16+16, y, z, 50, new Particle.DustOptions(Color.RED,2));
        }
    }
}
