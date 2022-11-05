package kiinse.plugins.ggo.gungalewiki.gui.menus;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.*;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.bukkit.inventory.FurnaceRecipe;
import org.jetbrains.annotations.NotNull;

public class FurnaceGUI extends CreatedGui {

    public FurnaceGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        assert getItem() != null;
        if (getPageManager() == null) {
            setPageManager(new PageManager().setRecipes(GuiUtils.getRecipes(getItem())));
        }

        var pluginData = gunGaleWiki.getPluginData();
        var userData = pluginData.getUserData(getPlayer());
        userData.addToLastSeen(getItem());
        pluginData.saveData(userData);
        var config = gunGaleWiki.getConfiguration();
        var recipe = getPageManager().getPageRecipe(getPage());

        if (recipe != null) {
            var input = ((FurnaceRecipe) recipe).getInput();
            var custom = CustomStack.byItemStack(input);
            if (custom != null) {
                setCreatedItem(new CustomItem(custom, 11, userData, this));
            } else {
                setCreatedItem(new StandartItem(11, input));
            }
        }

        setCreatedItem(new FireItem(20, gunGaleWiki, getPlayerLocale()));
        setCreatedItem(new CoalItem(29, gunGaleWiki, getPlayerLocale()));
        setCreatedItem(new CustomItem(getItem(), 24, userData, this));
        setCreatedItem(new CustomItem(getItem(), 24, userData, this));
        setCreatedItem(GuiUtils.nextCraftPageButton(getPageManager(), getPage(), getPlayerLocale(), gunGaleWiki, this, config));
        setCreatedItem(new AddToBookMarkButton(51, getPlayerLocale(), gunGaleWiki, this, getPlayer(), getItem()));
        setCreatedItem(GuiUtils.prevCraftPageButton(getPageManager(), getPage(), getPlayerLocale(), gunGaleWiki, this, config));
        setCreatedItem(new HomeButton(getPlayerLocale(), gunGaleWiki, 47, this));

        for (var i : new int[]{45, 46}) {
            setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, i, this));
        }
    }
}
