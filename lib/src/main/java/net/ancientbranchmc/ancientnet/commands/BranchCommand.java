package net.ancientbranchmc.ancientnet.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.commands.base.CommandBase;

public class BranchCommand extends CommandBase {
	private AncientNetPlugin pl;
	
	public BranchCommand(AncientNetPlugin plugin) {
		super();
		pl = plugin;
	}
	
	public void reload() {
		
	}

	@Override
	public void populateTabCompleteSuggestions(int parameterIndex, String[] args, String partialArg,
			List<String> suggestions) {
		// TODO Auto-generated method stub
		
	}
}
