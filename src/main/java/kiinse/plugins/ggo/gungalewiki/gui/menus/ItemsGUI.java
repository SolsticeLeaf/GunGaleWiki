package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.files.buttons.Button;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.*;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.jetbrains.annotations.NotNull;

public class ItemsGUI extends CreatedGui {

    public ItemsGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        if (getPageManager() == null) {
            assert getItem() != null;
            setPageManager(new PageManager().setItems(gunGaleWiki.getFilterButtons().getButton(Button.valueOf(getItem().toUpperCase()),
                                                                                               gunGaleWiki.getGunGaleAPI().getPlayerLocales().getLocale(
                                                                                                       getPlayer())).getItems()));
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
                    .setItem(getItem())
                    .setGui(Gui.ITEMS)
                    .setType(getType())
                    .setLastGui(getLastGui())
                    .setStringItem(getItem())
                    .setPageManager(getPageManager())
                    .setName(getName())
                    .open(player);
        })));
        setCreatedItem(new HomeButton(getPlayerLocale(), gunGaleWiki, 49, this));
        setCreatedItem(new PrevPageButton(getPageManager().hasPage(getPage() - 1), getPlayerLocale(), gunGaleWiki, 48, ((clickType, player) -> {
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() - 1)
                    .setItem(getItem())
                    .setGui(Gui.ITEMS)
                    .setType(getType())
                    .setLastGui(getLastGui())
                    .setStringItem(getItem())
                    .setPageManager(getPageManager())
                    .setName(getName())
                    .open(player);
        })));

        for (var j : new int[]{45, 46}) {
            setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, j, this));
        }
    }
}
