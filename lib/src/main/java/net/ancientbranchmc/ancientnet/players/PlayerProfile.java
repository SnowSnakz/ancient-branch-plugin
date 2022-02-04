package net.ancientbranchmc.ancientnet.players;

import java.sql.Connection;
import java.sql.Date;

import net.ancientbranchmc.ancientnet.api.DatabaseColumn;
import net.ancientbranchmc.ancientnet.api.FromTable;
import org.bukkit.OfflinePlayer;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.api.DatabaseItem;

@FromTable(database = "mtree", table = "global_player_profiles", primaryKey = "internalId")
public class PlayerProfile extends DatabaseItem {

	@DatabaseColumn(name = "internalId")
	protected int id;

	@DatabaseColumn(name = "uuid")
	protected String uuid;

	@DatabaseColumn(name = "last_known_name")
	protected String previousName;

	@DatabaseColumn(name = "date_first_joined")
	protected Date firstPlayed;

	@DatabaseColumn(name = "date_last_seen")
	protected Date lastPlayed;

	@DatabaseColumn(name = "last_known_ip")
	protected String previousIP;

	@DatabaseColumn(name = "time_played")
	protected long totalPlaytime;

	@DatabaseColumn(name = "spirit_essence")
	protected long globalCurrency;

	public PlayerProfile(AncientNetPlugin plugin, OfflinePlayer plr) {
		
	}

	@Override
	protected String getKeyValue() {
		return null;
	}
}
