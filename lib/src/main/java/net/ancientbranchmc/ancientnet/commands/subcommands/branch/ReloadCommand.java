package net.ancientbranchmc.ancientnet.commands.subcommands.branch;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.commands.base.CommandContext;
import net.ancientbranchmc.ancientnet.commands.base.CommandResult;
import net.ancientbranchmc.ancientnet.commands.base.SubCommandBase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.List;

public class ReloadCommand extends SubCommandBase {
    AncientNetPlugin pl;

    public ReloadCommand(AncientNetPlugin plugin) {
        super("reload");

        pl = plugin;
    }

    @Override
    public void populateTabCompleteSuggestions(
            int parameterIndex, String[] args, String partialArg, List<String> suggestions) {
        // We don't need any suggestions for this command (yet..)
    }

    @Override
    public CommandResult execute(CommandContext context) {

        // Reload all the things
        pl.reloadEverything();

        // Create a result that tells the sender that a full server restart might be required
        CommandResult result = new CommandResult(context).success().message(
                Component.text("The config was reloaded. ", Style.style(TextColor.color(16, 201, 100)))
                        .append(
                                Component.text(
                                        "Please note that certain aspects of" +
                                                " this plugin require a full server restart!",
                                Style.style(TextColor.color(176, 47, 42), TextDecoration.ITALIC)))
            );

        // Give the result
        return result;
    }
}
