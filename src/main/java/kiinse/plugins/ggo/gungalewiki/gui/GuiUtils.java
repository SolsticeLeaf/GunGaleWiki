package kiinse.plugins.ggo.gungalewiki.gui;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import kiinse.plugins.ggo.gungaleapi.api.files.filemanager.YamlFile;
import kiinse.plugins.ggo.gungaleapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.NextPageButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.PrevPageButton;
import kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces.WikiPageManager;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuiUtils {

    private GuiUtils() {}

    public static @NotNull CreatedGui getMainGui(@NotNull Player player) {
        return new GuiBuilder(player)
                .setPage(0)
                .setGui(Gui.HOME)
                .setGuiName(GunGaleWiki.getInstance().getConfiguration().getString(Config.MENU_HOME_NAME));
    }

    public static @Nullable String getNameByItemStack(@NotNull ItemStack itemStack) {
        var meta = itemStack.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return null;
        return PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(meta.displayName()));
    }

    public static @NotNull List<ItemStack> getItemsFromThis(@NotNull String item) {
        var cacheName = item.split(":");
        var itemName = cacheName[cacheName.length - 1];
        var result = new ArrayList<ItemStack>();
        var oresData = GunGaleWiki.getInstance().getOresData();
        Bukkit.getServer().recipeIterator().forEachRemaining(recipe -> {
            if ((recipe instanceof Keyed keyed)) {
                if (ItemsAdder.isCustomRecipe(keyed.getKey()) && !result.contains(recipe.getResult())) {
                    if (recipe instanceof ShapelessRecipe shapelessRecipe) {
                        if (hasItemInRecipe(shapelessRecipe, itemName)) result.add(recipe.getResult());
                    } else if (recipe instanceof ShapedRecipe shapedRecipe) {
                        if (hasItemInRecipe(shapedRecipe, itemName)) result.add(recipe.getResult());
                    } else if (recipe instanceof FurnaceRecipe furnaceRecipe) {
                        if (hasItemInRecipe(furnaceRecipe, itemName)) result.add(recipe.getResult());
                    } else if (recipe instanceof BlastingRecipe blastingRecipe) {
                        if (hasItemInRecipe(blastingRecipe, itemName)) result.add(recipe.getResult());
                    }
                }
            }
        });
        var drop = CustomStack.getInstance(oresData.getDropByOre(itemName));
        if (drop != null) result.add(drop.getItemStack());
        return result;
    }

    private static boolean hasItemInRecipe(@NotNull ShapelessRecipe recipe, @NotNull String itemName) {
        for (var ingredient : recipe.getIngredientList()) {
            var custom = CustomStack.byItemStack(ingredient);
            if (custom != null && custom.getId().equalsIgnoreCase(itemName)) return true;
        }
        return false;
    }

    private static boolean hasItemInRecipe(@NotNull ShapedRecipe recipe, @NotNull String itemName) {
        for (var ingredient : recipe.getIngredientMap().values()) {
            var custom = CustomStack.byItemStack(ingredient);
            if (custom != null && custom.getId().equalsIgnoreCase(itemName)) return true;
        }
        return false;
    }

    private static boolean hasItemInRecipe(@NotNull FurnaceRecipe recipe, @NotNull String itemName) {
        var custom = CustomStack.byItemStack(recipe.getInput());
        return custom != null && custom.getId().equalsIgnoreCase(itemName);
    }

    private static boolean hasItemInRecipe(@NotNull BlastingRecipe recipe, @NotNull String itemName) {
        var custom = CustomStack.byItemStack(recipe.getInput());
        return custom != null && custom.getId().equalsIgnoreCase(itemName);
    }


    public static @NotNull List<Recipe> getRecipes(@NotNull String item) {
        var result = new ArrayList<Recipe>();
        var cacheName = item.split(":");
        var itemName = cacheName[cacheName.length - 1];
        Bukkit.getServer().getRecipesFor(CustomStack.getInstance(item).getItemStack()).forEach(recipe -> {
            var custom = CustomStack.byItemStack(recipe.getResult());
            if (isRecipeNotEmpty(recipe) && recipe instanceof Keyed keyed && custom != null && custom.getId().equalsIgnoreCase(itemName) && ItemsAdder.isCustomRecipe(
                    keyed.getKey())) result.add(recipe);
        });
        return result;
    }

    private static boolean isRecipeNotEmpty(@NotNull Recipe recipe) {
        if (recipe instanceof ShapedRecipe shapedRecipe) return !shapedRecipe.getIngredientMap().isEmpty();
        if (recipe instanceof ShapelessRecipe shapelessRecipe) return !shapelessRecipe.getIngredientList().isEmpty();
        return recipe instanceof FurnaceRecipe;
    }

    public static @NotNull PrevPageButton prevCraftPageButton(@NotNull WikiPageManager pageManager, int page,
                                                              @NotNull PlayerLocale playerLocale,
                                                              @NotNull GunGaleWiki gunGaleWiki, @NotNull CreatedGui fromGui,
                                                              @NotNull YamlFile config) {
        return new PrevPageButton(pageManager.hasPage(page - 1), playerLocale, gunGaleWiki, 50, ((clickType, player) -> {
            var isFurnace = pageManager.get(page - 1) instanceof FurnaceRecipe;
            fromGui.delete();
            new GuiBuilder(player)
                    .setPage(page - 1)
                    .setGui(isFurnace ? Gui.FURNACE : Gui.WORKBENCH)
                    .setLastGui(fromGui.getLastGui())
                    .setPageManager(pageManager)
                    .setStringItem(fromGui.getItem())
                    .setName(config.getString(isFurnace ? Config.MENU_FURNACE_NAME : Config.MENU_WORKBENCH_NAME))
                    .open(player);
        }));
    }

    public static @NotNull NextPageButton nextCraftPageButton(@NotNull WikiPageManager pageManager, int page,
                                                              @NotNull PlayerLocale playerLocale,
                                                              @NotNull GunGaleWiki gunGaleWiki, @NotNull CreatedGui fromGui,
                                                              @NotNull YamlFile config) {
        return new NextPageButton(pageManager.hasPage(page + 1), playerLocale, gunGaleWiki, 52, ((clickType, player) -> {
            var isFurnace = pageManager.get(page + 1) instanceof FurnaceRecipe;
            fromGui.delete();
            new GuiBuilder(player)
                    .setPage(page + 1)
                    .setGui(isFurnace ? Gui.FURNACE : Gui.WORKBENCH)
                    .setLastGui(fromGui.getLastGui())
                    .setPageManager(pageManager)
                    .setStringItem(fromGui.getItem())
                    .setName(config.getString(isFurnace ? Config.MENU_FURNACE_NAME : Config.MENU_WORKBENCH_NAME))
                    .open(player);
        }));
    }
}
