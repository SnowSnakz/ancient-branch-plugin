package net.ancientbranchmc.ancientnet.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import net.ancientbranchmc.ancientnet.AncientNetPlugin;
import net.ancientbranchmc.ancientnet.api.ConfigPath;
import net.ancientbranchmc.ancientnet.api.PluginPlaceholder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConfig {
    @PluginPlaceholder
    private AncientNetPlugin pl;

    @ConfigPath(path = "host")
    private String host;

    @ConfigPath(path = "database")
    private String databaseName;

    @ConfigPath(path = "port")
    private int port;

    @ConfigPath(path = "username")
    private String username;

    @ConfigPath(path = "password")
    private String password;

    public Connection makeConnection() {
        MysqlDataSource ds = new MysqlDataSource();

        ds.setServerName(host);
        ds.setPort(port);
        ds.setDatabaseName(databaseName);
        ds.setUser(username);
        ds.setPassword(password);

        try {
            return ds.getConnection();
        }
        catch (SQLException ex) {
            pl.getConfigLogger().severe("Unable to connect to database '" + host + ":" + port + "' with user '" + username + "'.");
            ex.printStackTrace();

            return null;
        }
    }
}
