package net.ancientbranchmc.ancientnet;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import com.mysql.cj.jdbc.MysqlDataSource;

import net.ancientbranchmc.ancientnet.commands.BranchCommand;
import net.ancientbranchmc.ancientnet.commands.FriendCommand;
import net.ancientbranchmc.ancientnet.commands.ReportCommand;

public class AncientNetPlugin extends JavaPlugin {
	private MysqlDataSource dataSource;
	
	private BranchCommand branchCommand;
	private FriendCommand friendCommand;
	private ReportCommand reportCommand;
	
	private Permission executeBranchCommand;
	private Permission executeFriendCommand;
	private Permission executeReportCommand;
	
	public Connection connectSQL() throws SQLException {
		return dataSource.getConnection();
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
		
		// Configure DataSource
		dataSource = new MysqlDataSource();
		dataSource.setServerName(cfg.getString("mysql.host"));
		dataSource.setPort(cfg.getInt("mysql.host"));
		dataSource.setDatabaseName(cfg.getString("mysql.database"));
		dataSource.setUser(cfg.getString("mysql.username"));
		dataSource.setPassword(cfg.getString("mysql.password"));
		
	}
	
	@Override
	public void onEnable() {
		
		// In this scenario, the name is misleading... Using it here loads everything, *for the first time*!
		reloadEverything();
	}
}
