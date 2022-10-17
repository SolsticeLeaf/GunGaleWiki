package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Gui;
import kiinse.plugins.ggo.gungalewiki.enums.PageType;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.BackButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.CustomItem;
import kiinse.plugins.ggo.gungalewiki.gui.items.NextPageButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.PrevPageButton;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.jetbrains.annotations.NotNull;

public class ItemsGUI extends CreatedGui {

    public ItemsGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        if (getPageManager() == null) {
            setPageManager(new PageManager(PageType.ITEMS));
        }

        var userData = gunGaleWiki.getPluginData().getUserData(getPlayer());
        var i = 9;
        for (var item : getPageManager().getPageList(getPage())) {
            setCreatedItem(new CustomItem(item, i, userData, this));
            i++;
        }

        setCreatedItem(new NextPageButton(getPageManager().hasPage(getPage() + 1), getPlayerLocale(), gunGaleWiki, 50, ((clickType, player) -> {
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() + 1)
                    .getGui(Gui.ITEMS)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setName(getName())
                    .open(player);
        })));
        setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, 49, this));
        setCreatedItem(new PrevPageButton(getPageManager().hasPage(getPage() - 1), getPlayerLocale(), gunGaleWiki, 48, ((clickType, player) -> {
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() - 1)
                    .getGui(Gui.ITEMS)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setName(getName())
                    .open(player);
        })));
    }
}
