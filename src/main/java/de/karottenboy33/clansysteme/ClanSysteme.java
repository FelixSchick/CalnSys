package de.karottenboy33.clansysteme;

import de.karottenboy33.clansysteme.commands.ClanCommand;
import de.karottenboy33.clansysteme.events.ProxiedPlayerJoinEvent;
import de.karottenboy33.clansysteme.mysql.MySQL;
import de.karottenboy33.clansysteme.mysql.MySQLCreate;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class ClanSysteme extends Plugin {

    public static String prefix = "§8┃ §9Distaria §8» §7";
    public static String host;
    public static String database;
    public static String username;
    public static String password;
    @Override
    public void onEnable() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            File file = new File(getDataFolder().getPath(), "mysql.yml");
            if (!file.exists()) {
                file.createNewFile();
                Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                configuration.set("host", "");
                configuration.set("database", "");
                configuration.set("username", "");
                configuration.set("password", "");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            }
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            host = configuration.getString("host");
            database = configuration.getString("database");
            username = configuration.getString("username");
            password = configuration.getString("passwort");

        } catch (IOException e) {
            e.printStackTrace();
        }
        MySQLCreate.connect();
        MySQLCreate.createsClanTable();
        MySQLCreate.createsUserTable();
        getProxy().getPluginManager().registerCommand(this, new ClanCommand());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ProxiedPlayerJoinEvent());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
