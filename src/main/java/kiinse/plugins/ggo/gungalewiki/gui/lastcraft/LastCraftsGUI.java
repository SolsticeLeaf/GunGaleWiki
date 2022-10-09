package kiinse.plugins.ggo.gungalewiki.gui.lastcraft;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.PluginData;
import kiinse.plugins.ggo.gungalewiki.gui.GUIData;
import kiinse.plugins.ggo.gungalewiki.gui.items.BackItem;
import kiinse.plugins.ggo.gungalewiki.gui.items.CustomItem;
import org.jetbrains.annotations.NotNull;

public class LastCraftsGUI extends DarkGUI {

    private final GUIData guiData;
    private final PluginData pluginData;

    public LastCraftsGUI(@NotNull GUIData guiData) {
        super(guiData.getGunGaleWiki());
        this.guiData = guiData;
        this.pluginData = guiData.getGunGaleWiki().getPluginData();
    }

    @Override
    protected void inventory(@NotNull DarkWaterJavaPlugin darkWaterJavaPlugin) {
        setItem(new BackItem(49, ((clickType, player) -> {
            delete();
            guiData.getPrevGui().open(player);
        })));

        int pos = 9;
        for (var item : pluginData.getPlayerLastSeen(guiData.getPlayer())) {
            setItem(new CustomItem(item, pos, pluginData, new GUIData(guiData.getPlayer(), guiData.getGunGaleWiki(), this)));
        }
    }
}
