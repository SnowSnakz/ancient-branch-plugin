package net.ancientbranchmc.ancientnet.api;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;

public abstract class DatabaseItem {
	private transient final AncientNetPlugin pl;
	
	protected abstract void requestData(Connection sql);
	
	protected DatabaseItem() {
		pl = JavaPlugin.getPlugin(AncientNetPlugin.class);
	}
	
	public void fillData() throws SQLException {
		try(Connection connection = pl.connectSQL()) {
			requestData(connection);
		}
		
		Class<?> myClass = getClass();
		for(Field field : myClass.getDeclaredFields()) {
			DatabaseColumn ann = field.getAnnotation(DatabaseColumn.class);
			if(ann != null)
			{
				ann.name()
			}
		}
	}
}
