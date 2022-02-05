package net.ancientbranchmc.ancientnet.commands;

import java.util.ArrayList;
import java.util.List;

import net.ancientbranchmc.ancientnet.commands.base.CommandContext;
import net.ancientbranchmc.ancientnet.commands.base.CommandResult;
import net.ancientbranchmc.ancientnet.commands.subcommands.branch.ReloadCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.commands.base.CommandBase;

public class BranchCommand extends CommandBase {
	public BranchCommand(AncientNetPlugin plugin) {
		super(plugin, "ab-lib.commands.branch.see");
	}

	ReloadCommand reload;
	
	public void reloadFromConfig() {

	}

	@Override
	public void populateTabCompleteSuggestions(int parameterIndex, String[] args, String partialArg,
			List<String> suggestions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommandResult execute(CommandContext context) {
		return new CommandResult(context).message(
				Component.text("Hello, use ", Style.style(TextColor.color(97, 70, 179)))
						.append(Component.text("/branch help", Style.style(TextColor.color(0, 222, 174), TextDecoration.UNDERLINED)))
						.append(Component.text(" to get started!", Style.style(TextColor.color(97, 70, 179))))
			);
	}
}
