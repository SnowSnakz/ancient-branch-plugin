package net.ancientbranchmc.ancientnet.players;

import java.sql.Connection;

import org.bukkit.OfflinePlayer;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.api.DatabaseItem;

public class PlayerProfile extends DatabaseItem {

	public PlayerProfile(AncientNetPlugin plugin, OfflinePlayer plr) {
		
	}
	
	@Override
	protected void requestData(Connection sql) {
		// TODO Auto-generated method stub
		
	}

}
