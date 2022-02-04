package net.ancientbranchmc.ancientnet.commands.base;

import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class CommandResult {
	private int exitState;
	private Component message;
	
	public CommandResult(CommandContext context) {
		exitState = -1;
	}
	
	public CommandResult error() {
		exitState = 1;
		return this;
	}
	
	public CommandResult success() {
		exitState = 0;
		return this;
	}
	
	public boolean wasSuccessful() {
		return exitState == 0;
	}

	public boolean wasUnsuccessful() {
		return exitState == 1;
	}

	public boolean isStateUndefined() {
		return exitState == -1;
	}
	
	public Component getMessage() {
		return message;
	}
	
	public CommandResult message(Component message) {
		this.message = message;
		return this;
	}
	
	public CommandResult message(String message) {
		return this.message(message, false);
	}
	
	public CommandResult message(String message, boolean isJson) {
		if(isJson) {
			this.message = PaperComponents.gsonSerializer().deserialize(message);
		}
		else {
			this.message = LegacyComponentSerializer.legacySection().deserializeOrNull(message);
		}
		
		return this;
	}
}
