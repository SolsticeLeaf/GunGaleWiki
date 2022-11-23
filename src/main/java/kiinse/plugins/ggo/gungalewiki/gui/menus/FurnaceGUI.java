package kiinse.plugins.ggo.gungalewiki.gui.menus;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.*;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FurnaceGUI extends CreatedGui {

    public FurnaceGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        if (getItem() == null) {
            return;
        }
        if (getPageManager() == null) {
            setPageManager(new PageManager().setItems(GuiUtils.getRecipes(getItem()), 1));
        }

        var pluginData = gunGaleWiki.getPluginData();
        var userData = pluginData.getUserData(getPlayer());
        userData.addToLastSeen(getItem());
        pluginData.saveData(userData);
        var config = gunGaleWiki.getConfiguration();

        if (getPageManager().get(getPage(), new ArrayList<Recipe>()).get(0) instanceof FurnaceRecipe recipe) {
            var input = (recipe).getInput();
            var custom = CustomStack.byItemStack(input);
            if (custom != null) {
                setCreatedItem(new CustomItem(custom, 11, userData, this));
            } else {
                setCreatedItem(new StandartItem(11, input));
            }
        }

        setCreatedItem(new FireItem(20, gunGaleWiki, getPlayerLocale()));
        setCreatedItem(new CoalItem(29, gunGaleWiki, getPlayerLocale()));

        setCreatedItem(new RecipeResultItem(getItem(), 24, userData, this));

        setCreatedItem(GuiUtils.nextCraftPageButton(getPageManager(), getPage(), getPlayerLocale(), gunGaleWiki, this, config));
        setCreatedItem(new AddToBookMarkButton(51, getPlayerLocale(), gunGaleWiki, this, getPlayer(), getItem()));
        setCreatedItem(GuiUtils.prevCraftPageButton(getPageManager(), getPage(), getPlayerLocale(), gunGaleWiki, this, config));
        setCreatedItem(new HomeButton(getPlayerLocale(), gunGaleWiki, 47, this));

        for (var i : new int[]{45, 46}) {
            setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, i, this));
        }
    }
}
