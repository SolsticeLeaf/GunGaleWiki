package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.PageType;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.BackButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.CustomItem;
import kiinse.plugins.ggo.gungalewiki.managers.pagemanager.PageManager;
import org.jetbrains.annotations.NotNull;

public class LastCraftsGUI extends CreatedGui {

    public LastCraftsGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        if (getPageManager() == null) {
            setPageManager(new PageManager(PageType.BOOKMARK).setItems(gunGaleWiki.getPluginData().getPlayerLastSeen(getPlayer())));
        }

        var pluginData = gunGaleWiki.getPluginData();
        setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, 49, this));

        int pos = 9;
        for (var item : pluginData.getPlayerLastSeen(getPlayer())) {
            setCreatedItem(new CustomItem(item, pos, pluginData, this));
            pos++;
        }
    }
}
