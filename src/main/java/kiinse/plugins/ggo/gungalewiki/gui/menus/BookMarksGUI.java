package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Gui;
import kiinse.plugins.ggo.gungalewiki.enums.PageType;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.BookMarks;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.BackButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.CustomItem;
import kiinse.plugins.ggo.gungalewiki.gui.items.NextPageButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.PrevPageButton;
import kiinse.plugins.ggo.gungalewiki.managers.pagemanager.PageManager;
import org.jetbrains.annotations.NotNull;

public class BookMarksGUI extends CreatedGui implements BookMarks {

    public BookMarksGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        if (getPageManager() == null) {
            setPageManager(new PageManager(PageType.BOOKMARK).setItems(gunGaleWiki.getPluginData().getPlayerBookmarks(getPlayer())));
        }

        int pos = 9;
        for (var item : getPageManager().getPageList(getPage())) {
            setCreatedItem(new CustomItem(item, pos, gunGaleWiki.getPluginData(), this));
            pos++;
        }

        if (getPageManager().hasPage(getPage() + 1)) setCreatedItem(new NextPageButton(getPlayerLocale(), gunGaleWiki, 50, ((clickType, player) -> {
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() + 1)
                    .getGui(Gui.BOOKMARKS)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setName(getName())
                    .open(player);
        })));

        setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, 49, this));

        if (getPageManager().hasPage(getPage() - 1)) setCreatedItem(new PrevPageButton(getPlayerLocale(), gunGaleWiki, 48, ((clickType, player) -> {
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() - 1)
                    .getGui(Gui.BOOKMARKS)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setName(getName())
                    .open(player);
        })));
    }
}
