package kiinse.plugins.ggo.gungalewiki;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.utilities.ItemStackUtils;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkItemUtils;
import kiinse.plugins.ggo.gungalewiki.database.DataUtils;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.HikariDatabase;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.PluginData;
import kiinse.plugins.ggo.gungalewiki.files.buttons.ButtonsData;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButtons;
import kiinse.plugins.ggo.gungalewiki.initialize.RegisterCommands;
import org.jetbrains.annotations.NotNull;

public final class GunGaleWiki extends GunGaleJavaPlugin {

    private static GunGaleWiki instance;
    private HikariDatabase database;
    private PluginData pluginData;
    private FiltersButtons buttons;
    private ItemStackUtils itemStackUtils;

    public static @NotNull GunGaleWiki getInstance() {
        return instance;
    }

    @Override
    public void onStart() throws Exception {
        instance = this;
        var config = getConfiguration();
//        this.database = new Database(this, new DatabaseSettings()
//                .setHost(config.getString(Config.DB_HOST))
//                .setPort(config.getString(Config.DB_PORT))
//                .setDbName(config.getString(Config.DB_NAME))
//                .setLogin(config.getString(Config.DB_LOGIN))
//                .setPassword(config.getString(Config.DB_PASSWORD)));
//        this.pluginData = new DataUtils(database.getConnection(), this).loadFromDB();
        this.pluginData = new DataUtils(this);
        buttons = new ButtonsData(this).load();
        itemStackUtils = new DarkItemUtils(this);
        new RegisterCommands(this);
    }

    @Override
    public void onStop() throws Exception {
        //       pluginData.saveToDB();
//        database.closeConnection();
    }

    public @NotNull PluginData getPluginData() {
        return pluginData;
    }

    public @NotNull FiltersButtons getFilterButtons() {
        return buttons;
    }

    public @NotNull ItemStackUtils getItemStackUtils() {
        return itemStackUtils;
    }
}
