package net.ancientbranchmc.ancientnet.api;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.ancientbranchmc.ancientnet.exceptions.MissingAnnotationException;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.plugin.java.JavaPlugin;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;

public abstract class DatabaseItem {
	private transient final AncientNetPlugin pl;
	
	// protected abstract void requestData(Connection sql);

	protected abstract String getKeyValue();

	protected DatabaseItem() {
		pl = JavaPlugin.getPlugin(AncientNetPlugin.class);
	}
	
	public void fillData() throws SQLException, NullArgumentException, MissingAnnotationException {
		Class<?> myClass = getClass();

		FromTable ft = myClass.getAnnotation(FromTable.class);

		if(ft == null) {
			throw new MissingAnnotationException(FromTable.class.getName(), "This is required to know which table to take the data from, and to know which column is the primary key.");
		}

		try(Connection connection = pl.connectToDatabase("mother-tree")) {
			PreparedStatement stmt = connection.prepareStatement("select * from ? where `?` = '?'");

			stmt.setString(1, ft.table());
			stmt.setString(2, ft.primaryKey());
			stmt.setString(3, getKeyValue());

			ResultSet results = stmt.executeQuery();

			Logger log = pl.getDatabaseItemLogger(this);

			for(Field field : myClass.getDeclaredFields()) {
				DatabaseColumn ann = field.getAnnotation(DatabaseColumn.class);

				if(ann == null)
				{
					continue;
				}

				if(results.next()) {
					Object obj = results.getRef(ann.name()).getObject();
					if(obj != null)
					{
						if(field.getClass().isAssignableFrom(obj.getClass())) {
							try {
								field.set(this, obj);
							}
							catch(Exception ex) {
								if(ex instanceof IllegalAccessException) {
									log.log(Level.SEVERE, "Unable to access field: '" + field.getName() + "'");
									log.log(Level.SEVERE, "Attempting to retry by giving access through reflections.");

									try {
										field.setAccessible(true);
										field.set(this, obj);
									}
									catch(Exception ex2) {
										log.log(Level.SEVERE, "Failed to load data into field '" + field.getName() + "', see stacktrace below.");
										ex2.printStackTrace();
									}
								}
								else {

									log.log(Level.SEVERE, "Failed to load data into field '" + field.getName() + "', see stacktrace below.");
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
}
