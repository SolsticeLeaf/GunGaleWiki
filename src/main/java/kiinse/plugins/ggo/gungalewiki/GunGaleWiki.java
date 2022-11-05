package kiinse.plugins.ggo.gungalewiki;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.utilities.ItemStackUtils;
import kiinse.plugins.ggo.gungaleapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkItemUtils;
import kiinse.plugins.ggo.gungalewiki.data.GGWikiDataImpl;
import kiinse.plugins.ggo.gungalewiki.data.Ores;
import kiinse.plugins.ggo.gungalewiki.data.Totems;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.GGWikiData;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.OresData;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.TotemsData;
import kiinse.plugins.ggo.gungalewiki.files.buttons.ButtonsData;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButtons;
import kiinse.plugins.ggo.gungalewiki.initialize.RegisterCommands;
import kiinse.plugins.ggo.gungalewiki.listeners.OnQuitListener;
import org.jetbrains.annotations.NotNull;

public final class GunGaleWiki extends GunGaleJavaPlugin {

    private static GunGaleWiki instance;
    private GGWikiData wikiData;
    private FiltersButtons buttons;
    private OresData oresData;
    private TotemsData totemsData;
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
        this.totemsData = new Totems(this);
        getServer().getPluginManager().registerEvents(new OnQuitListener(), this);
        new RegisterCommands(this);
    }

    @Override
    public void onStop() throws Exception {
        wikiData.saveAll();
        for (var uuid : DarkGUI.getOpenInventories().values()) {
            var inv = DarkGUI.getInventoriesByUUID().get(uuid);
            inv.delete();
        }
    }

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

    public @NotNull TotemsData getTotemsData() {
        return totemsData;
    }
}
