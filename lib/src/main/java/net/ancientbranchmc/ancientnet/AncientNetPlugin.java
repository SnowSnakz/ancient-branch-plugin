package net.ancientbranchmc.ancientnet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.ancientbranchmc.ancientnet.api.DatabaseItem;
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
	private BranchCommand branchCommand;
	private FriendCommand friendCommand;
	private ReportCommand reportCommand;
	
	private Permission executeBranchCommand;
	private Permission executeFriendCommand;
	private Permission executeReportCommand;

	private Logger databaseItemLogger;
	private Logger playerProfileLogger;

	private String dbHost;
	private String dbUser;
	private String dbPass;
	private int dbPort;

	public Logger getDatabaseItemLogger(DatabaseItem item) {
		if(item != null) {
			databaseItemLogger.log(Level.INFO, "Log from item '" + item.getClass().getName() + "'");
			return databaseItemLogger;
		}

		return null;
	}

	public Logger getPlayerProfileLogger() {
		return playerProfileLogger;
	}

	public Connection connectToDatabase(String databaseName) throws SQLException {

	}

	public void reloadEverything() {
		if(branchCommand == null) {
			branchCommand = new BranchCommand(this);
		}
		else {
			branchCommand.reload();
		}
		
		// Create and Load default config if it doesn't exist.
		saveDefaultConfig();
		reloadConfig();
		
		// 'cfg' variable for easier reference.
		FileConfiguration cfg = getConfig();

		// Load database stuff
		dbHost = cfg.getString("mysql.host");
		dbPort = cfg.getInt("mysql.host");
		dbUser = cfg.getString("mysql.username");
		dbPass = cfg.getString("mysql.password");
		
	}
	
	@Override
	public void onEnable() {
		databaseItemLogger = Logger.getLogger("DatabaseItem");
		playerProfileLogger = Logger.getLogger("PlayerProfiles");

		// In this scenario, the name is misleading... Using it here loads everything, *for the first time*!
		reloadEverything();
	}
}
