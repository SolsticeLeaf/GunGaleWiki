package kiinse.plugins.ggo.gungalewiki;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.utilities.ItemStackUtils;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkItemUtils;
import kiinse.plugins.ggo.gungalewiki.data.GGWikiDataImpl;
import kiinse.plugins.ggo.gungalewiki.data.Ores;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.GGWikiData;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.OresData;
import kiinse.plugins.ggo.gungalewiki.files.buttons.ButtonsData;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButtons;
import kiinse.plugins.ggo.gungalewiki.initialize.RegisterCommands;
import org.jetbrains.annotations.NotNull;

public final class GunGaleWiki extends GunGaleJavaPlugin {

    private static GunGaleWiki instance;
    private GGWikiData wikiData;
    private FiltersButtons buttons;
    private OresData oresData;
    private ItemStackUtils itemStackUtils;

    public static @NotNull GunGaleWiki getInstance() {
        return instance;
    }

    @Override
    public void onStart() throws Exception {
        instance = this;
        this.wikiData = new GGWikiDataImpl(getServer().getPluginManager().getPlugin("GunGaleCore") != null);
        buttons = new ButtonsData(this).load();
        itemStackUtils = new DarkItemUtils(this);
        this.oresData = new Ores(this);
        new RegisterCommands(this);
    }

    @Override
    public void onStop() throws Exception {}

    public @NotNull GGWikiData getPluginData() {
        return wikiData;
    }

    public @NotNull FiltersButtons getFilterButtons() {
        return buttons;
    }

    public @NotNull ItemStackUtils getItemStackUtils() {
        return itemStackUtils;
    }

    public @NotNull OresData getOresData() {
        return oresData;
    }
}
