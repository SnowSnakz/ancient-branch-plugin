package net.ancientbranchmc.ancientnet.config;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.api.ConfigPath;
import net.ancientbranchmc.ancientnet.api.PluginPlaceholder;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class ConfigLoader {
    private FileConfiguration cfg;
    private AncientNetPlugin pl;
    private Logger logger;

    public ConfigLoader(FileConfiguration cfg, AncientNetPlugin plugin) {
        this.cfg = cfg;
        pl = plugin;
        logger = plugin.getConfigLogger();
    }


    public <T> T loadFrom(String path, Class<T> clazz) {
        T result = null;

        try {
            result = clazz.getConstructor().newInstance();
        }
        catch(NoSuchMethodException e) {
            logger.severe("Unable to find parameterless constructor for class: '" + clazz.getName() + "'");
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e2) {
            logger.severe("Unable to invoke parameterless constructor for class: '" + clazz.getName() + "'");
            e2.printStackTrace();
        }

        if(result == null) return null;

        for(Field field : clazz.getFields()) {
            ConfigPath cfgPath = field.getAnnotation(ConfigPath.class);

            if(cfgPath != null) {
                String absolutePath = path;
                absolutePath = cfgPath.absolute() ? cfgPath.path() : absolutePath + "." + cfgPath.path();

                try {
                    field.set(result, cfg.get(absolutePath));
                }
                catch (IllegalAccessException e) {
                    logger.severe("Unable to access the specified field: '" + field.getName() + "', it may be private or protected...");
                    logger.severe("Attempting to retry by giving access through reflections.");

                    try {
                        field.setAccessible(true);
                        field.set(result, cfg.get(absolutePath));
                    }
                    catch (Exception e2) {
                        logger.severe("Failed to write to the field...");
                        e2.printStackTrace();
                    }
                }
            }

            PluginPlaceholder pp = field.getAnnotation(PluginPlaceholder.class);
            if(pp != null) {
                try {
                    field.set(result, pl);
                }
                catch (IllegalAccessException e) {
                    logger.severe("Unable to access the specified field: '" + field.getName() + "', it may be private or protected...");
                    logger.severe("Attempting to retry by giving access through reflections.");

                    try {
                        field.setAccessible(true);
                        field.set(result, pl);
                    }
                    catch (Exception e2) {
                        logger.severe("Failed to write to the field...");
                        e2.printStackTrace();
                    }
                }
            }
        }

        return result;
    }
}
