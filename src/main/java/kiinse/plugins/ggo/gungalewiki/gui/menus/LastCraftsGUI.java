package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.BackButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.CustomItem;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class LastCraftsGUI extends CreatedGui {

    public LastCraftsGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        var userData = gunGaleWiki.getPluginData().getUserData(getPlayer());
        setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, 49, this));
        var list = userData.getLastSeen();
        Collections.reverse(list);
        int pos = 9;
        for (var item : list) {
            setCreatedItem(new CustomItem(item, pos, userData, this));
            pos++;
        }
    }
}
