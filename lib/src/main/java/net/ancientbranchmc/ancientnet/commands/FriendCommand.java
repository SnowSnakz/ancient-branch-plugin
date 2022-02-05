package net.ancientbranchmc.ancientnet.commands;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.commands.base.CommandBase;
import net.ancientbranchmc.ancientnet.commands.base.CommandContext;
import net.ancientbranchmc.ancientnet.commands.base.CommandResult;

import java.util.List;

public class FriendCommand extends CommandBase {

    public FriendCommand(AncientNetPlugin plugin) {
        super(plugin, "ab-lib.commands.friends.see");
    }

    public void reloadFromConfig() {

    }

    @Override
    public void populateTabCompleteSuggestions(int parameterIndex, String[] args, String partialArg, List<String> suggestions) {

    }

    @Override
    public CommandResult execute(CommandContext context) {
        return null;
    }
}
