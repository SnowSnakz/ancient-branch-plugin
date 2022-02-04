package net.ancientbranchmc.ancientnet.players;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;

public class AncientNetPlayer {
	private OfflinePlayer plr;
	private PlayerProfile profile;
	private AncientNetPlugin pl;
	
	public AncientNetPlayer(UUID playerId) {
		this(Bukkit.getOfflinePlayer(playerId));
	}
	
	public AncientNetPlayer(OfflinePlayer player) {
		pl = JavaPlugin.getPlugin(AncientNetPlugin.class);
		
		plr = player;
		profile = new PlayerProfile(plr);
		
		try {
			profile.fillData();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasPlayedBefore() {
		return plr.hasPlayedBefore();
	}
}
