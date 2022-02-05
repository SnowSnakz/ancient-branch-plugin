package net.ancientbranchmc.ancientnet.events;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public abstract class EventHandlers implements Listener {
    private AncientNetPlugin pl;

    public EventHandlers(AncientNetPlugin plugin) {
        pl = plugin;
    }

    public AncientNetPlugin getPlugin() {
        return pl;
    }

    public abstract void reloadConfig(FileConfiguration config);
}
