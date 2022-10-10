package kiinse.plugins.ggo.gungalewiki.database;

import com.zaxxer.hikari.HikariDataSource;
import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.HikariDatabase;
import kiinse.plugins.ggo.gungalewiki.database.utils.DatabaseSettings;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

public class Database implements HikariDatabase {

    private final HikariDataSource source ;
    private final DarkWaterJavaPlugin plugin;

    public Database(@NotNull DarkWaterJavaPlugin plugin, @NotNull DatabaseSettings settings) throws IllegalStateException {
        this.plugin = plugin;
        plugin.sendLog("Connecting to database...");
        try (var ds = new HikariDataSource()) {
            ds.setJdbcUrl("jdbc:mariadb://" + settings.getHost() + ":" + settings.getPort() + "/" + settings.getDbName());
            ds.setUsername(settings.getLogin());
            ds.setPassword(settings.getPassword());
            ds.getConnection();
            //TODO: Реализовать создание таблиц
            this.source = ds;
            plugin.sendLog("Database connected");
        } catch (Exception e) {
            plugin.sendLog(Level.SEVERE, "Error on connection to database! Message: " + e.getMessage());
            throw new IllegalStateException();
        }
    }

    @Override
    public @NotNull HikariDataSource getSource() {
        return source;
    }

    @Override
    public @NotNull Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    @Override
    public @NotNull Database closeConnection() {
        if (source != null && !source.isClosed()) {
            source.close();
            plugin.sendLog("Database connection closed");
        } else {
            plugin.sendLog(Level.WARNING, "Database connection already closed");
        }
        return this;
    }
}
