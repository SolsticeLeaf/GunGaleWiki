package kiinse.plugins.ggo.gungalewiki.gui.items;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungaleapi.core.files.messages.DarkMessagesUtils;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.PluginData;
import kiinse.plugins.ggo.gungalewiki.enums.*;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.menus.BookMarksGUI;
import kiinse.plugins.ggo.gungalewiki.gui.menus.FurnaceGUI;
import kiinse.plugins.ggo.gungalewiki.gui.menus.WorkBenchGUI;
import kiinse.plugins.ggo.gungalewiki.managers.pagemanager.PageManager;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Sound;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomItem implements GuiItem {

    private final GunGaleWiki gunGaleWiki = GunGaleWiki.getInstance();
    private final String item;
    private final ItemStack itemStack;
    private final PluginData pluginData;
    private final CreatedGui fromGui;
    private final int pos;

    public CustomItem(@NotNull String item, int pos, @NotNull PluginData pluginData, @NotNull CreatedGui fromGui) {
        this.itemStack = CustomStack.getInstance(item).getItemStack();
        this.item = item;
        this.pos = pos;
        this.pluginData = pluginData;
        this.fromGui = fromGui;
    }

    public CustomItem(@NotNull CustomStack customStack, int pos, @NotNull PluginData pluginData, @NotNull CreatedGui fromGui) {
        this.itemStack = customStack.getItemStack();
        this.item = customStack.getNamespacedID();
        this.pos = pos;
        this.pluginData = pluginData;
        this.fromGui = fromGui;
    }

    @Override
    public int slot() {
        return pos;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public @NotNull String name() {
        return itemStack.getDisplayName();
    }

    @Override
    public @NotNull GuiAction action() {
        return (clickType, player) -> {
            var config = gunGaleWiki.getConfiguration();
            if (clickType == ClickType.SHIFT_LEFT) {
                var messages = new DarkMessagesUtils(gunGaleWiki);
                if (pluginData.hasPlayerItemInBookmarks(player, item)) {
                    pluginData.removeFromPlayerBookmarks(player, item);
                    DarkPlayerUtils.playSound(player, Sound.valueOf(config.getString(Config.BOOKMARK_REMOVED_SOUND)));
                    messages.sendMessage(player, Message.BOOKMARK_REMOVED, Replace.ITEM, CustomStack.getInstance(item).getItemStack().getDisplayName());
                } else {
                    pluginData.addToPlayerBookmarks(player, item);
                    DarkPlayerUtils.playSound(player, Sound.valueOf(config.getString(Config.BOOKMARK_ADDED_SOUND)));
                    messages.sendMessage(player, Message.BOOKMARK_ADDED, Replace.ITEM, CustomStack.getInstance(item).getItemStack().getDisplayName());
                }
                if (fromGui instanceof BookMarksGUI || fromGui instanceof WorkBenchGUI || fromGui instanceof FurnaceGUI) {
                    fromGui.delete();
                    assert fromGui.getPageManager() != null;
                    new GuiBuilder(player)
                            .setPage(fromGui.getPage())
                            .setItem(fromGui.getItem())
                            .getGui(fromGui.getType())
                            .setPageManager(fromGui.getPageManager())
                            .setLastGui(fromGui.getLastGui())
                            .setStringItem(fromGui.getItem())
                            .setGuiName(fromGui.getName())
                            .open(player);
                }
                return;
            }
            if (clickType == ClickType.LEFT) {
                var pages = new PageManager(PageType.CRAFT).setRecipes(getRecipes(item));
                if (pages.hasPage(1)) {
                    fromGui.delete();
                    var isFurnace = pages.getPageRecipe(1) instanceof FurnaceRecipe;
                    new GuiBuilder(player)
                            .setPage(1)
                            .getGui(isFurnace ? Gui.FURNACE : Gui.WORKBENCH)
                            .setLastGui(fromGui)
                            .setPageManager(pages)
                            .setStringItem(item)
                            .setName(config.getString(isFurnace ? Config.MENU_FURNACE_NAME : Config.MENU_WORKBENCH_NAME))
                            .open(player);
                }
            }
            if (clickType == ClickType.RIGHT) {
                var pages = new PageManager(PageType.CRAFT).setStackItems(getItemsFromThis(item));
                fromGui.delete();
                new GuiBuilder(player)
                        .setPage(1)
                        .setItem(item)
                        .getGui(Gui.FROM_THIS_ITEM_CRAFT)
                        .setLastGui(fromGui)
                        .setPageManager(pages)
                        .setStringItem(item)
                        .setName(config.getString(Config.MENU_FROMITEM_NAME))
                        .open(player);
            }
        };
    }

    private @NotNull List<ItemStack> getItemsFromThis(@NotNull String item) {
        var cacheName = item.split(":");
        var itemName = cacheName[cacheName.length-1];
        var result = new ArrayList<ItemStack>();
        var iterator = Bukkit.getServer().recipeIterator();
        while (iterator.hasNext()) {
            var recipe = iterator.next();
            if (recipe instanceof Keyed keyed) {
                if (ItemsAdder.isCustomRecipe(keyed.getKey()) && !result.contains(recipe.getResult())) {
                    if (recipe instanceof ShapelessRecipe shapelessRecipe) {
                        if (hasItemInRecipe(shapelessRecipe, itemName)) result.add(recipe.getResult());
                    } else if (recipe instanceof ShapedRecipe shapedRecipe) {
                        if (hasItemInRecipe(shapedRecipe, itemName)) result.add(recipe.getResult());
                    } else if (recipe instanceof FurnaceRecipe furnaceRecipe) {
                        if (hasItemInRecipe(furnaceRecipe, itemName)) result.add(recipe.getResult());
                    }
                }
            }
        }
        return result;
    }

    private boolean hasItemInRecipe(@NotNull ShapelessRecipe recipe, @NotNull String itemName) {
        for (var ingredient : recipe.getIngredientList()) {
            var custom = CustomStack.byItemStack(ingredient);
            if (custom != null && custom.getId().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasItemInRecipe(@NotNull ShapedRecipe recipe, @NotNull String itemName) {
        for (var ingredient : recipe.getIngredientMap().values()) {
            var custom = CustomStack.byItemStack(ingredient);
            if (custom != null && custom.getId().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasItemInRecipe(@NotNull FurnaceRecipe recipe, @NotNull String itemName) {
        var custom = CustomStack.byItemStack(recipe.getInput());
        return custom != null && custom.getId().equalsIgnoreCase(itemName);
    }


    private @NotNull List<Recipe> getRecipes(@NotNull String item) {
        var result = new ArrayList<Recipe>();
        var cacheName = item.split(":");
        var itemName = cacheName[cacheName.length-1];

        for (var recipe : Bukkit.getServer().getRecipesFor(CustomStack.getInstance(item).getItemStack())) {

            var custom = CustomStack.byItemStack(recipe.getResult());

            if (recipe instanceof Keyed keyed && custom != null && custom.getId().equalsIgnoreCase(itemName) && ItemsAdder.isCustomRecipe(keyed.getKey())) {
                    result.add(recipe);
            }

        }
        return result;
    }
}
