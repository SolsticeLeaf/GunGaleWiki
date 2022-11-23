package kiinse.plugins.ggo.gungalewiki.gui.menus;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.*;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FromThisItemCraftGUI extends CreatedGui {

    public FromThisItemCraftGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        if (getItem() == null) return;
        if (getPageManager() == null) {
            setPageManager(new PageManager().setItems(GuiUtils.getItemsFromThis(getItem()), 36));
        }

        var userData = gunGaleWiki.getPluginData().getUserData(getPlayer());
        setCreatedItem(new CustomItem(getItem(), 4, userData, this));

        var i = 9;
        for (var item : getPageManager().get(getPage(), new ArrayList<ItemStack>())) {
            var custom = CustomStack.byItemStack(item);
            if (custom != null) {
                setCreatedItem(new CustomItem(custom, i, userData, this));
            } else {
                setCreatedItem(new StandartItem(i, item));
            }
            i++;
        }

        setCreatedItem(new NextPageButton(getPageManager().hasPage(getPage() + 1), getPlayerLocale(), gunGaleWiki, 50, ((clickType, player) -> {
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() + 1)
                    .setItem(getItem())
                    .setGui(Gui.FROMITEM)
                    .setLastGui(getLastGui())
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
                    .setGui(Gui.FROMITEM)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setName(getName())
                    .open(player);
        })));

        for (var j : new int[]{45, 46}) {
            setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, j, this));
        }
    }
}
