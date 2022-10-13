package kiinse.plugins.ggo.gungalewiki.gui.menus;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.*;
import org.bukkit.inventory.FurnaceRecipe;
import org.jetbrains.annotations.NotNull;

public class FurnaceGUI extends CreatedGui {

    public FurnaceGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        assert getPageManager() != null;
        assert getItem() != null;
        getGunGaleWiki().getPluginData().addToPlayerLastSeen(getPlayer(), getItem());
        var config = gunGaleWiki.getConfiguration();
        var recipe = getPageManager().getPageRecipe(getPage());
        var pluginData = gunGaleWiki.getPluginData();

        if (recipe != null) {
            var input = ((FurnaceRecipe) recipe).getInput();
            var custom = CustomStack.byItemStack(input);

            if (custom != null) {
                setCreatedItem(new CustomItem(custom, 11, pluginData, this));
            } else {
                setCreatedItem(new StandartItem(11, input));
            }
        }

        setCreatedItem(new FireItem(20, gunGaleWiki, getPlayerLocale()));
        setCreatedItem(new CoalItem(29, gunGaleWiki, getPlayerLocale()));
        setCreatedItem(new CustomItem(getItem(), 24, pluginData, this));


        if (getPageManager().hasPage(getPage() + 1)) setCreatedItem(new NextPageButton(getPlayerLocale(), gunGaleWiki, 52, ((clickType, player) -> {
            var isFurnace = getPageManager().getPageRecipe(getPage() + 1) instanceof FurnaceRecipe;
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() + 1)
                    .getGui(isFurnace ? Gui.FURNACE : Gui.WORKBENCH)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setStringItem(getItem())
                    .setName(config.getString(isFurnace ? Config.MENU_FURNACE_NAME : Config.MENU_WORKBENCH_NAME))
                    .open(player);
        })));

        setItem(new AddToBookMarkButton(51, getPlayerLocale(), gunGaleWiki, this, getPlayer(), getItem()));

        if (getPageManager().hasPage(getPage() - 1)) setCreatedItem(new PrevPageButton(getPlayerLocale(), gunGaleWiki, 50, ((clickType, player) -> {
            var isFurnace = getPageManager().getPageRecipe(getPage() - 1) instanceof FurnaceRecipe;
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() - 1)
                    .getGui(isFurnace ? Gui.FURNACE : Gui.WORKBENCH)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setStringItem(getItem())
                    .setName(config.getString(isFurnace ? Config.MENU_FURNACE_NAME : Config.MENU_WORKBENCH_NAME))
                    .open(player);
        })));

        for (var i : new int[]{45, 46, 47}) {
            setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, i, this));
        }
    }
}
