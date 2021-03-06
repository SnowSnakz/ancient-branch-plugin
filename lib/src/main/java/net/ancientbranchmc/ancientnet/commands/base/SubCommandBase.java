package net.ancientbranchmc.ancientnet.commands.base;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommandBase {
	private String name;
	
	private List<SubCommandBase> subCommands;
	private List<CommandParameter> params;
	
	public SubCommandBase(String name) {
		this.name = name;
		
		subCommands = new ArrayList<>();
		params = new ArrayList<>();
	}
	
	public void registerSubCommand(SubCommandBase cmd) {
		subCommands.add(cmd);
	}

	public SubCommandBase getSubCommand(String subCommand) {
		for(SubCommandBase scb : subCommands) {
			if(scb.name.equals(subCommand)) {
				return scb;
			}
		}

		return null;
	}
	
	public SubCommandBase usesSubCommand(CommandContext ctx, int fromParam) {
		String[] args = ctx.getPlainArgs();

		if(args.length <= fromParam) {
			return this;
		}

		SubCommandBase sub = getSubCommand(args[fromParam]);

		if(sub == null) {
			return this;
		}

		return sub;
	}
	
	public void setParameter(int parameterSlot, CommandParameter param) {
		boolean invalidIndex = parameterSlot > params.size();
		invalidIndex |= parameterSlot < 0;
		
		if(invalidIndex) {
			throw new ArrayIndexOutOfBoundsException(parameterSlot);
		}
		
		if(parameterSlot == params.size()) {
			params.add(param);
		}
		else {
			params.set(parameterSlot, param);
		}
	}
	
	public abstract void populateTabCompleteSuggestions(int parameterIndex, String[] args, String partialArg, List<String> suggestions);
	public abstract CommandResult execute(CommandContext context);
}
