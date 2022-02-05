package net.ancientbranchmc.ancientnet.events;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.config.ConfigTextComponent;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatEvents extends EventHandlers {
    boolean blockPluginList;
    boolean blockNamespacedCommands;

    Component messageBlockPluginList;
    Component messageBlockNamespace;

    public ChatEvents(AncientNetPlugin pl) {
        super(pl);
    }

    @Override
    public void reloadConfig(FileConfiguration config) {
        blockNamespacedCommands = config.getBoolean("block-namespaced-commands");
        blockPluginList = config.getBoolean("block-plugin-list-command");

        messageBlockNamespace = new ConfigTextComponent(config, "messages.blocked-plugin-list").getComponent();
        messageBlockPluginList = new ConfigTextComponent(config, "messages.blocked-namespaced-command").getComponent();
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent ev) {
        if(ev.isCancelled()) return;

        String msg = ev.getMessage();
        String command = msg.substring(0, msg.indexOf(' '));

        Player player = ev.getPlayer();

        if(!player.hasPermission("ancientbranch.bypass.blockpluginlist")) {
            if (blockPluginList) {
                if (command.startsWith("/pl") || command.startsWith("pl")
                    || command.startsWith("/plugins") || command.startsWith("plugins")) {
                    ev.setCancelled(true);
                    ev.getPlayer().sendMessage(messageBlockPluginList);
                }
            }
        }

        if(!player.hasPermission("ancientbranch.bypass.blocknamespacedcommands")) {
            if (blockNamespacedCommands) {
                if (command.contains(":")) {
                    ev.setCancelled(true);
                    ev.getPlayer().sendMessage(messageBlockNamespace);
                }
            }
        }
    }
}
