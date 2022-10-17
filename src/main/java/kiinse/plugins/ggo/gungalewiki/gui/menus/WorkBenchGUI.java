package kiinse.plugins.ggo.gungalewiki.gui.menus;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.UserData;
import kiinse.plugins.ggo.gungalewiki.enums.PageType;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.*;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;


public class WorkBenchGUI extends CreatedGui {

    public WorkBenchGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        assert getItem() != null;
        if (getPageManager() == null) {
            setPageManager(new PageManager(PageType.CRAFT).setRecipes(GuiUtils.getRecipes(getItem())));
        }

        var pluginData = gunGaleWiki.getPluginData();
        var userData = pluginData.getUserData(getPlayer());
        userData.addToLastSeen(getItem());
        pluginData.saveData(userData);

        var config = gunGaleWiki.getConfiguration();
        var recipe = getPageManager().getPageRecipe(getPage());

        if (recipe != null) {
            if (recipe instanceof ShapedRecipe rec) {
                shapedRecipe(rec, userData);
            } else if (recipe instanceof ShapelessRecipe rec) {
                shapelessRecipe(rec, userData);
            }
        }

        setCreatedItem(new CustomItem(getItem(), 24, userData, this));
        setCreatedItem(GuiUtils.nextCraftPageButton(getPageManager(), getPage(), getPlayerLocale(), gunGaleWiki, this, config));
        setCreatedItem(new AddToBookMarkButton(51, getPlayerLocale(), gunGaleWiki, this, getPlayer(), getItem()));
        setCreatedItem(GuiUtils.prevCraftPageButton(getPageManager(), getPage(), getPlayerLocale(), gunGaleWiki, this, config));
        setCreatedItem(new HomeButton(getPlayerLocale(), gunGaleWiki, 47, this));

        for (var i : new int[]{45, 46}) {
            setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, i, this));
        }
    }


    private void shapedRecipe(@NotNull ShapedRecipe recipe, @NotNull UserData userData) {
        var shape = String.join("", recipe.getShape()).toCharArray();
        var ingredients = recipe.getIngredientMap();
        for (var i = 0; i < shape.length; i++) {
            var itemStack = ingredients.get(shape[i]);
            setItems(userData, i, itemStack);
        }
    }

    private void shapelessRecipe(@NotNull ShapelessRecipe recipe, @NotNull UserData userData) {
        var list = recipe.getIngredientList();
        for (var i = 0; i < list.size(); i++) {
            var itemStack = list.get(i);
            setItems(userData, i, itemStack);
        }
    }

    private void setItems(@NotNull UserData userData, int i, ItemStack itemStack) {
        var craft = new int[]{11, 12, 13, 20, 21, 22, 29, 30, 31};
        if (itemStack != null && itemStack.getType() != Material.AIR) {
            var custom = CustomStack.byItemStack(itemStack);
            if (custom != null) {
                setCreatedItem(new CustomItem(custom, craft[i], userData, this));
            } else {
                setCreatedItem(new StandartItem(craft[i], itemStack));
            }
        }
    }
}
