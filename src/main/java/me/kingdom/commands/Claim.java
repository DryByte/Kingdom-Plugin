package me.kingdom.commands;

import me.kingdom.KingdomMain;
import me.kingdom.managers.ClaimManager;
import me.kingdom.tasks.ClaimTask;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Claim implements TabExecutor {
    private final ClaimManager ClaimManager;
    private final KingdomMain plugin;

    public Claim(KingdomMain plugin) {
        this.ClaimManager = new ClaimManager();
        this.plugin = plugin;
    }

    private boolean handleConfirmation(CommandSender sender) {
        Player p = (Player)sender;
        this.ClaimManager.removeConfirmation(p.getUniqueId());

        sender.sendMessage("Chunk claimed!");
        return true;
    }

    private boolean handleCancel(CommandSender sender) {
        Player p = (Player)sender;
        this.ClaimManager.removeConfirmation(p.getUniqueId());

        sender.sendMessage("Chunk claim cancelled!");
        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;
        if (args.length > 0) {
            if (!this.ClaimManager.checkConfirmation(p.getUniqueId())) {
                sender.sendMessage("You doesn't have any claim request to use this option.");
                return false;
            }

            switch (args[0]) {
                case "confirm":
                    return handleConfirmation(sender);

                case "cancel":
                    return handleCancel(sender);

                default:
                    sender.sendMessage("Invalid argument.");
            }

            return false;
        }

        Chunk chunk = p.getChunk();

        if (!this.ClaimManager.newConfirmation(p.getUniqueId(), chunk)) {
            sender.sendMessage("You already has a chunk claim request, please confirm it or cancel.");
            return false;
        }

        new ClaimTask(this.ClaimManager, p).runTaskTimer(this.plugin, 0, 20);

        sender.sendMessage("test " + chunk.getX()*16 + " " + chunk.getZ()*16);

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of("confirm", "cancel");
    }
}
