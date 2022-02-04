package net.ancientbranchmc.ancientnet.commands.base;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandContext {
	private ConsoleCommandSender consoleSender;
	private CommandSender genericSender;
	private Player playerSender;
	
	private String[] parameters;
	
	private CommandBase topLevelCommand;
	private SubCommandBase executingCommand;
	
	public CommandContext(CommandSender sender, String[] params, CommandBase tlc) {
		topLevelCommand = tlc;
		executingCommand = tlc;
		
		parameters = params;
		
		genericSender = sender;
		
		if(sender instanceof Player) {
			playerSender = (Player)sender;
		}
		
		if(sender instanceof ConsoleCommandSender) {
			consoleSender = (ConsoleCommandSender)sender;
		}
 	}
	
	public CommandResult evaluate() {
		CommandResult result = new CommandResult(this);
		
		
		
		return result;
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
