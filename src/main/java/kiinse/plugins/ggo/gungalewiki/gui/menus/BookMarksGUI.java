package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.BackButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.CustomItem;
import kiinse.plugins.ggo.gungalewiki.gui.items.NextPageButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.PrevPageButton;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class BookMarksGUI extends CreatedGui {

    public BookMarksGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        var userData = gunGaleWiki.getPluginData().getUserData(getPlayer());
        if (getPageManager() == null) {
            var bookmarks = userData.getBookmarks();
            Collections.reverse(bookmarks);
            setPageManager(new PageManager().setItemsList(bookmarks));
        }

        int pos = 9;
        for (var str : getPageManager().get(getPage(), new ArrayList<String>())) {
            setCreatedItem(new CustomItem(str, pos, userData, this));
            pos++;
        }

        setCreatedItem(new NextPageButton(getPageManager().hasPage(getPage() + 1), getPlayerLocale(), gunGaleWiki, 50, ((clickType, player) -> {
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() + 1)
                    .setGui(Gui.BOOKMARKS)
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
                    .setGui(Gui.BOOKMARKS)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setName(getName())
                    .open(player);
        })));
    }
}
