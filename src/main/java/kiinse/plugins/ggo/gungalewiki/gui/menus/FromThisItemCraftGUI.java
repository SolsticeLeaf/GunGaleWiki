package kiinse.plugins.ggo.gungalewiki.gui.menus;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Gui;
import kiinse.plugins.ggo.gungalewiki.enums.PageType;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.*;
import org.jetbrains.annotations.NotNull;

public class FromThisItemCraftGUI extends CreatedGui {

    public FromThisItemCraftGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        assert getItem() != null;
        assert getPageManager() != null;

        var pluginData = gunGaleWiki.getPluginData();

        setCreatedItem(new CustomItem(getItem(), 4, pluginData, this));

        var i = 9;
        for (var item : getPageManager().getPageItemStackList(getPage())) {
            var custom = CustomStack.byItemStack(item);
            if (custom != null) {
                setCreatedItem(new CustomItem(custom, i, pluginData, this));
            } else {
                setCreatedItem(new StandartItem(i, item));
            }
            i++;
        }

        if (getPageManager().hasPage(getPage() + 1)) setCreatedItem(
                new NextPageButton(getPlayerLocale(), gunGaleWiki, 50, ((clickType, player) -> {
                    delete();
                    new GuiBuilder(player)
                            .setPage(getPage() + 1)
                            .setItem(getItem())
                            .getGui(Gui.FROM_THIS_ITEM_CRAFT)
                            .setLastGui(getLastGui())
                            .setPageManager(getPageManager())
                            .setName(getName())
                            .open(player);
                })));

        setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, 49, this));

        if (getPageManager().hasPage(getPage() - 1)) setCreatedItem(
                new PrevPageButton(getPlayerLocale(), gunGaleWiki, 48, ((clickType, player) -> {
                    delete();
                    new GuiBuilder(player)
                            .setPage(getPage() - 1)
                            .setItem(getItem())
                            .getGui(Gui.FROM_THIS_ITEM_CRAFT)
                            .setLastGui(getLastGui())
                            .setPageManager(getPageManager())
                            .setName(getName())
                            .open(player);
                })));
    }
}
