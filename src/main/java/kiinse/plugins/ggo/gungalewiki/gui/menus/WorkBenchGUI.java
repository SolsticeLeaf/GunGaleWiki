package kiinse.plugins.ggo.gungalewiki.gui.menus;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.PluginData;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.*;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;


public class WorkBenchGUI extends CreatedGui {

    public WorkBenchGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        assert getPageManager() != null;
        assert getItem() != null;
        var pluginData = gunGaleWiki.getPluginData();
        pluginData.addToPlayerLastSeen(getPlayer(), getItem());
        var config = gunGaleWiki.getConfiguration();
        var recipe = getPageManager().getPageRecipe(getPage());

        if (recipe != null) {
            if (recipe instanceof ShapedRecipe rec) {
                shapedRecipe(rec, pluginData);
            } else if (recipe instanceof ShapelessRecipe rec) {
                shapelessRecipe(rec, pluginData);
            }
        }

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

        setCreatedItem(new AddToBookMarkButton(51, getPlayerLocale(), gunGaleWiki, this, getPlayer(), getItem()));

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


    private void shapedRecipe(@NotNull ShapedRecipe recipe, @NotNull PluginData pluginData) {
        var shape = String.join("", recipe.getShape()).toCharArray();
        var ingredients = recipe.getIngredientMap();
        for (var i = 0; i < shape.length; i++) {
            var itemStack = ingredients.get(shape[i]);
            setItems(pluginData, i, itemStack);
        }
    }

    private void shapelessRecipe(@NotNull ShapelessRecipe recipe, @NotNull PluginData pluginData) {
        var list = recipe.getIngredientList();
        for (var i = 0; i < list.size(); i++) {
            var itemStack = list.get(i);
            setItems(pluginData, i, itemStack);
        }
    }

    private void setItems(@NotNull PluginData pluginData, int i, ItemStack itemStack) {
        var craft = new int[]{11, 12, 13, 20, 21, 22, 29, 30, 31};
        if (itemStack != null && itemStack.getType() != Material.AIR) {
            var custom = CustomStack.byItemStack(itemStack);
            if (custom != null) {
                setCreatedItem(new CustomItem(custom, craft[i], pluginData, this));
            } else {
                setCreatedItem(new StandartItem(craft[i], itemStack));
            }
        }
    }
}
