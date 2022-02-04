package net.ancientbranchmc.ancientnet.commands;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.commands.base.CommandBase;
import net.ancientbranchmc.ancientnet.commands.base.CommandContext;
import net.ancientbranchmc.ancientnet.commands.base.CommandResult;

import java.util.List;

public class ReportCommand extends CommandBase {

    public ReportCommand(AncientNetPlugin plugin) {
        super(plugin, "ab-lib.commands.report.see");
    }

    @Override
    public void populateTabCompleteSuggestions(int parameterIndex, String[] args, String partialArg, List<String> suggestions) {

    }

    @Override
    public CommandResult execute(CommandContext context) {
        return null;
    }
}
