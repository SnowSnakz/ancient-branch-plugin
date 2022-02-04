package net.ancientbranchmc.ancientnet.commands.base;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.players.AncientNetPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CommandBase extends SubCommandBase implements CommandExecutor, TabCompleter {
	private AncientNetPlugin pl;
	private String perrmission;

	public AncientNetPlugin getPlugin() {
		return pl;
	}

	public CommandBase(AncientNetPlugin plugin) {
		super("__do__");

		pl = plugin;
	}

	public CommandBase(AncientNetPlugin plugin, String visiblePermission) {
		this(plugin);
		perrmission = visiblePermission;
	}

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String alias, @NotNull String[] args) {

		// Do not send suggestions if the command shouldn't be visible to the user.
		if(perrmission != null) {
			if (!sender.hasPermission(perrmission)) {
				return null;
			}
		}

		// Create a command context from the provided information.
		CommandContext cmdCtx = new CommandContext(sender, args, this);

		// Determine the sub command being executed.
		SubCommandBase executingCommand = cmdCtx.getExecutingCommand();

		// Create new list for suggestions.
		List<String> suggestions = new ArrayList<>();

		// Populate Suggestions List.
		executingCommand.populateTabCompleteSuggestions(
				cmdCtx.getSubCommandArgIndex(),
				args,
				args[args.length - 1],
				suggestions
			);

		// Return the populated suggestions.
		return suggestions;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {

		// Pretend the command doesn't exist if the command shouldn't be visible to the user.
		if(perrmission != null) {
			if (!sender.hasPermission(perrmission)) {
				return false;
			}
		}

		// Create a command context from the provided information.
		CommandContext cmdCtx = new CommandContext(sender, args, this);

		// Determine the sub command being executed.
		SubCommandBase executingCommand = cmdCtx.getExecutingCommand();

		// Execute the command and store the result.
		CommandResult result = executingCommand.execute(cmdCtx);

		// If the state is unknown or undefined, we send the "something broke" message to the executor.
		if(result.isStateUndefined()) {
			sender.sendMessage(Component.text("Whoops! Something broke...", Style.style(TextColor.color(186, 67, 93), TextDecoration.ITALIC)));
			sender.sendMessage(Component.text("Please tell a developer, moderator or administrator as soon as possible!", Style.style(TextColor.color(214, 79, 0), TextDecoration.UNDERLINED)));
		}
		else {
			// Otherwise, we send the result message to the executor.
			sender.sendMessage(result.getMessage());
		}

		// Signify the command exists and has executed.
		return true;
	}
	
}
