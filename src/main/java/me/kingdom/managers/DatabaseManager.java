package me.kingdom.managers;

import me.kingdom.KingdomMain;
import me.kingdom.database.models.ChunkModel;
import me.kingdom.database.models.KingdomModel;
import org.bukkit.configuration.file.FileConfiguration;
import org.hibernate.cfg.Configuration;

import java.util.Locale;

public class DatabaseManager {
    private final FileConfiguration serverConfig;
    private final Configuration cfg;
    private final KingdomMain plugin;

    public DatabaseManager(KingdomMain plugin) {
        this.plugin = plugin;
        this.serverConfig = plugin.getConfig();
        this.cfg = new Configuration();

        this.configConnection();
        this.loadModels();

        this.cfg.buildSessionFactory();
    }

    public void configConnection() {
        this.cfg.setProperty("hibernate.show_sql", true);
        this.cfg.setProperty("hibernate.format_sql", true);
        this.cfg.setProperty("hibernate.highlight_sql", true);

        String url = this.serverConfig.getString("database.url", "jdbc:sqlite:"+this.plugin.getDataFolder()+"/kingdom.db");
        String dialect = this.serverConfig.getString("database.dialect", "org.hibernate.community.dialect.SQLiteDialect");

        if (!dialect.startsWith("org.hibernate.")) {
            switch (dialect.toLowerCase(Locale.ROOT)) {
                case "mysql":
                    dialect = "org.hibernate.dialect.MySQLDialect";
                    break;
                case "mariadb":
                    dialect = "org.hibernate.dialect.MariaDBDialect";
                    break;
                default:
                    System.out.println("No support to " + dialect + " database, falling back to sqlite");
                case "local":
                case "sqlite":
                    dialect = "org.hibernate.community.dialect.SQLiteDialect";
                    break;
            }
        }

        this.cfg.setProperty("hibernate.dialect", dialect);
        this.cfg.setProperty("hibernate.connection.url", url);
        this.cfg.setProperty("hibernate.connection.username", this.serverConfig.getString("database.username", ""));
        this.cfg.setProperty("hibernate.connection.password", this.serverConfig.getString("database.password", ""));
    }

    public void loadModels() {
        this.cfg.addAnnotatedClass(KingdomModel.class);
        this.cfg.addAnnotatedClass(ChunkModel.class);
    }
}
