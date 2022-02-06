package net.ancientbranchmc.ancientnet.api;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.jdi.InvalidTypeException;
import net.ancientbranchmc.ancientnet.exceptions.MissingAnnotationException;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.plugin.java.JavaPlugin;

import net.ancientbranchmc.ancientnet.AncientNetPlugin;

public abstract class DatabaseItem {
	private transient final AncientNetPlugin pl;

	protected abstract String getKeyValue();

	protected DatabaseItem() {
		pl = JavaPlugin.getPlugin(AncientNetPlugin.class);
	}

	public void updateData() throws SQLException, NullArgumentException, MissingAnnotationException, IllegalAccessException, InvalidTypeException {
		Class<?> myClass = getClass();

		FromTable ft = myClass.getAnnotation(FromTable.class);

		if(ft == null) {
			throw new MissingAnnotationException(FromTable.class.getName(), "This is required to know which table to take the data from, and to know which column is the primary key.");
		}

		Connection connection = pl.connectToDatabase(ft.database());

		StringBuilder sql = new StringBuilder();
		sql.append("update ? set (");

		HashMap<Field, DatabaseDataType> fields = new HashMap<>();

		boolean first = true;
		for (Field field : myClass.getDeclaredFields()) {
			DatabaseColumn ann = field.getAnnotation(DatabaseColumn.class);

			if (ann == null) {
				continue;
			}

			DatabaseDataType type = ann.type();
			String columnName = ann.name();

			fields.put(field, type);

			if(first) {
				first = false;
			}
			else {
				sql.append(", ");
			}

			sql.append(columnName).append("=?");
		}

		sql.append(") where ?=?");

		PreparedStatement stmt = connection.prepareStatement(sql.toString());

		stmt.setString(1, ft.table());

		int i = 2;
		for(Map.Entry<Field, DatabaseDataType> field : fields.entrySet()) {
			switch (field.getValue()) {
				case String:
					stmt.setString(i, (String)field.getKey().get(this));
					break;
				case BigInt:
					stmt.setLong(i, (long)field.getKey().get(this));
					break;
				case Int:
					stmt.setInt(i, (int)field.getKey().get(this));
					break;
				case TinyInt:
					stmt.setInt(i, (byte)field.getKey().get(this));
					break;
				case Boolean:
					stmt.setBoolean(i, (boolean)field.getKey().get(this));
					break;
				case Blob:
					stmt.setBlob(i, (Blob)field.getKey().get(this));
					break;
				default:
					throw new InvalidTypeException("DatabaseDataType." + field.getValue().name() + " is not supported at this time.");
			}

			i++;
		}

		stmt.setString(i, ft.primaryKey());
		stmt.setString(i + 1, getKeyValue());
	}
	
	public void fillData() throws SQLException, NullArgumentException, MissingAnnotationException {
		Class<?> myClass = getClass();

		FromTable ft = myClass.getAnnotation(FromTable.class);

		if(ft == null) {
			throw new MissingAnnotationException(FromTable.class.getName(), "This is required to know which table to take the data from, and to know which column is the primary key.");
		}

		Connection connection = pl.connectToDatabase(ft.database());

		PreparedStatement stmt = connection.prepareStatement("select * from ? where ?=?");

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
