package net.ancientbranchmc.ancientnet.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConfigSection {
    Map<String, DatabaseConfig> configurations;

    public DatabaseConfigSection(ConfigLoader loader, ConfigurationSection section) {
        configurations = new HashMap<>();

        for(String key : section.getKeys(false)) {
            DatabaseConfig database = loader.loadFrom(key, DatabaseConfig.class);

            if(database != null) {
                configurations.putIfAbsent(key, database);
            }
        }
    }

    public DatabaseConfig getDatabase(String name) {
        return configurations.getOrDefault(name, null);
    }
}
