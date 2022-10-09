package kiinse.plugins.ggo.gungalewiki.database.utils;

import org.jetbrains.annotations.NotNull;

public class DatabaseSettings {

    private String host = "localhost";
    private String port = "3306";
    private String login = "mysql";
    private String password = "mysql";
    private String dbName = "gungalewiki";

    public @NotNull String getHost() {
        return this.host;
    }

    public @NotNull DatabaseSettings setHost(@NotNull String sqlHost) throws IllegalArgumentException {
        if (sqlHost.isBlank()) throw new IllegalArgumentException("SQL host is empty");
        this.host = sqlHost;
        return this;
    }

    public @NotNull String getPort() {
        return this.port;
    }

    public @NotNull DatabaseSettings setPort(@NotNull String sqlPort) throws IllegalArgumentException {
        if (sqlPort.isBlank()) throw new IllegalArgumentException("SQL port is empty");
        this.port = sqlPort;
        return this;
    }

    public @NotNull String getLogin() {
        return this.login;
    }

    public @NotNull DatabaseSettings setLogin(@NotNull String sqlLogin) throws IllegalArgumentException {
        if (sqlLogin.isBlank()) throw new IllegalArgumentException("SQL login is empty");
        this.login = sqlLogin;
        return this;
    }

    public @NotNull String getPassword() {
        return this.password;
    }

    public @NotNull DatabaseSettings setPassword(@NotNull String sqlPassword) throws IllegalArgumentException {
        if (sqlPassword.isBlank()) throw new IllegalArgumentException("SQL password is empty");
        this.password = sqlPassword;
        return this;
    }

    public @NotNull String getDbName() {
        return this.dbName;
    }

    public @NotNull DatabaseSettings setDbName(@NotNull String sqldbName) throws IllegalArgumentException {
        if (sqldbName.isBlank()) throw new IllegalArgumentException("SQL database name is empty");
        this.dbName = sqldbName;
        return this;
    }
}

