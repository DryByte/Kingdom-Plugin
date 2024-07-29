package me.kingdom;

import me.kingdom.commands.Claim;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class KingdomMain extends JavaPlugin {
    @Override
    public void onEnable() {
        PluginCommand cmd = getCommand("claim");
        assert cmd != null;
        cmd.setExecutor(new Claim(this));
        getLogger().log(Level.INFO ,"Kingdom plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
