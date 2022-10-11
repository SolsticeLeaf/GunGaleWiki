package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.PluginData;
import kiinse.plugins.ggo.gungalewiki.gui.items.BackButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.CustomItem;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LastCraftsGUI extends DarkGUI {

    private final PluginData pluginData;
    private final Player openedPlayer;

    public LastCraftsGUI(@NotNull GunGaleWiki gunGaleWiki, @NotNull Player player) {
        super(gunGaleWiki);
        this.pluginData = gunGaleWiki.getPluginData();
        this.openedPlayer = player;
    }

    @Override
    protected void inventory(@NotNull GunGaleJavaPlugin darkWaterJavaPlugin) {
        setItem(new BackButton(49, ((clickType, player) -> {
            delete();
            //TODO: Сделать открытие предыдущего
        })));

        int pos = 9;
        for (var item : pluginData.getPlayerLastSeen(openedPlayer)) {
            setItem(new CustomItem(item, pos, pluginData, this));
        }
    }
}
