package net.ancientbranchmc.ancientnet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.ancientbranchmc.ancientnet.api.DatabaseItem;
import net.ancientbranchmc.ancientnet.config.ConfigLoader;
import net.ancientbranchmc.ancientnet.config.ConfigTextComponent;
import net.ancientbranchmc.ancientnet.config.DatabaseConfig;
import net.ancientbranchmc.ancientnet.config.DatabaseConfigSection;
import net.ancientbranchmc.ancientnet.events.ChatEvents;
import net.ancientbranchmc.ancientnet.events.PlayerEvents;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import com.mysql.cj.jdbc.MysqlDataSource;

import net.ancientbranchmc.ancientnet.commands.BranchCommand;
import net.ancientbranchmc.ancientnet.commands.FriendCommand;
import net.ancientbranchmc.ancientnet.commands.ReportCommand;

import javax.sql.DataSource;

public class AncientNetPlugin extends JavaPlugin {
	// Events
	private ChatEvents chatEvents;
	private PlayerEvents playerEvents;

	// Commands
	private BranchCommand branchCommand;
	private FriendCommand friendCommand;
	private ReportCommand reportCommand;

	// Configuration
	private DatabaseConfigSection databases;

	// Permissions
	private Permission executeBranchCommand;
	private Permission executeFriendCommand;
	private Permission executeReportCommand;

	// Loggers
	private Logger databaseItemLogger;
	private Logger playerProfileLogger;
	private Logger configLogger;
	private Logger commandLogger;

	// Text Components
	private Component noPermissionCommand;

	private Component reloadStarted;
	private Component reloadCompleted;
	private Component reloadFailed;

	public Logger getDatabaseItemLogger(DatabaseItem item) {
		if(item != null) {
			databaseItemLogger.log(Level.INFO, "Log from item '" + item.getClass().getName() + "'");
			return databaseItemLogger;
		}

		return null;
	}

	public Logger getConfigLogger() {
		return configLogger;
	}

	public Logger getCommandLogger() {
		return commandLogger;
	}

	public Logger getPlayerProfileLogger() {
		return playerProfileLogger;
	}

	public Connection connectToDatabase(String databaseName) {
		return databases.getDatabase(databaseName).makeConnection();
	}

	public void reloadEverything() {
		// Refresh Commands
		branchCommand.reloadFromConfig();
		friendCommand.reloadFromConfig();
		reportCommand.reloadFromConfig();

		// Create default config if it doesn't exist.
		saveDefaultConfig();

		// Make sure it's loaded.
		reloadConfig();
		
		// 'cfg' variable for easier reference.
		FileConfiguration cfg = getConfig();
		ConfigLoader loader = new ConfigLoader(cfg, this);

		// Only load the database configurations ONCE
		if(databases == null) {
			databases = new DatabaseConfigSection(loader, cfg.getConfigurationSection("databases"));
		}
		else {
			databaseItemLogger.warning("IF any changes were made to the configuration section of the " +
					"databases, a server restart is required to apply those changes.");
		}

		noPermissionCommand = new ConfigTextComponent(cfg, "messages.no-permission").getComponent();
		reloadStarted = new ConfigTextComponent(cfg, "messages.reload-started").getComponent();
		reloadCompleted = new ConfigTextComponent(cfg, "messages.reload-completed").getComponent();
		reloadFailed = new ConfigTextComponent(cfg, "messages.reload-errored").getComponent();
	}
	
	@Override
	public void onEnable() {
		// Create Loggers
		databaseItemLogger = Logger.getLogger("AB-Databases");
		playerProfileLogger = Logger.getLogger("AB-Profiles");
		configLogger = Logger.getLogger("AB-Configuration");
		commandLogger = Logger.getLogger("AB-Commands");

		// Create Commands
		branchCommand = new BranchCommand(this);
		friendCommand = new FriendCommand(this);
		reportCommand = new ReportCommand(this);

		// Register Commands
		Bukkit.getPluginCommand("branch").setExecutor(branchCommand);
		Bukkit.getPluginCommand("branch").setTabCompleter(branchCommand);

		Bukkit.getPluginCommand("ab").setExecutor(branchCommand);
		Bukkit.getPluginCommand("ab").setTabCompleter(branchCommand);

		Bukkit.getPluginCommand("friends").setExecutor(friendCommand);
		Bukkit.getPluginCommand("friends").setTabCompleter(friendCommand);

		Bukkit.getPluginCommand("fr").setExecutor(friendCommand);
		Bukkit.getPluginCommand("fr").setTabCompleter(friendCommand);

		Bukkit.getPluginCommand("report").setExecutor(reportCommand);
		Bukkit.getPluginCommand("report").setTabCompleter(reportCommand);

		// Create Event Handlers


		// Load the config
		reloadEverything();
	}
}
