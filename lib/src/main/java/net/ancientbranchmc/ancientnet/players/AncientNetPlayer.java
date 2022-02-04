package net.ancientbranchmc.ancientnet.players;

import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.ancientbranchmc.ancientnet.exceptions.MissingAnnotationException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;

public class AncientNetPlayer {
	private OfflinePlayer plr;
	private PlayerProfile profile;
	private AncientNetPlugin pl;
	private Logger logger;

	public AncientNetPlayer(UUID playerId) {
		this(Bukkit.getOfflinePlayer(playerId));

	}

	public AncientNetPlayer(OfflinePlayer player) {
		pl = JavaPlugin.getPlugin(AncientNetPlugin.class);
		logger = pl.getPlayerProfileLogger();

		plr = player;
		profile = new PlayerProfile(pl, plr);

		try {
			profile.fillData();
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to load profile for player " + plr.getName() + ": \n" + e.getMessage());
			e.printStackTrace();
		} catch (MissingAnnotationException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasPlayedBefore() {
		return plr.hasPlayedBefore();
	}
}
