package kiinse.plugins.ggo.gungalewiki.database.interfaces;

import com.zaxxer.hikari.HikariDataSource;
import kiinse.plugins.ggo.gungalewiki.database.Database;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;

public interface HikariDatabase {

    @NotNull HikariDataSource getSource();

    @NotNull Connection getConnection() throws SQLException;

    @NotNull Database closeConnection();
}
