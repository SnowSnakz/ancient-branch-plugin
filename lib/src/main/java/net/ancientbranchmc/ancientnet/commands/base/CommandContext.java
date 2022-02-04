package net.ancientbranchmc.ancientnet.commands.base;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandContext {
	private ConsoleCommandSender consoleSender;
	private CommandSender genericSender;
	private Player playerSender;
	
	private String[] parameters;

	private int executingCommandStart;
	private CommandBase topLevelCommand;
	private SubCommandBase executingCommand;
	
	public CommandContext(CommandSender sender, String[] params, CommandBase tlc) {
		topLevelCommand = tlc;
		executingCommand = tlc;

		SubCommandBase tempCmd = tlc;
		int i = 0;

		while(executingCommand != (tempCmd = tempCmd.getSubCommand(params[i++]))) {
			executingCommand = tempCmd;
		}

		executingCommandStart = i - 1;

		parameters = params;
		
		genericSender = sender;
		
		if(sender instanceof Player) {
			playerSender = (Player)sender;
		}
		
		if(sender instanceof ConsoleCommandSender) {
			consoleSender = (ConsoleCommandSender)sender;
		}
 	}

	public String[] getPlainArgs() {
		return parameters.clone();
	}

	public CommandBase getTopLevelCommand() {
		return topLevelCommand;
	}

	public SubCommandBase getExecutingCommand() {
		return executingCommand;
	}

	public int getSubCommandArgIndex() {
		return executingCommandStart;
	}

	public boolean senderIsConsole() {
		return consoleSender != null;
	}
	
	public boolean senderIsPlayer() {
		return playerSender != null;
	}
	
	public CommandSender getGenericSender() {
		return genericSender;
	}
	
	public ConsoleCommandSender getConsoleSender() {
		return consoleSender;
	}
	
	public Player getPlayerSender() {
		return playerSender;
	}
}
