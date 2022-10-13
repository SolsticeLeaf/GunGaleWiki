package kiinse.plugins.ggo.gungalewiki.managers.pagemanager;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.PageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageManager {

    private final PageType pageType;
    private final HashMap<Integer, Recipe> pages_recipes = new HashMap<>();
    private HashMap<Integer, List<ItemStack>> pages_items_itemstack = new HashMap<>();
    private HashMap<Integer, List<String>> pages_items = new HashMap<>();

    public PageManager(@NotNull PageType pageType) {
        this.pageType = pageType;
    }

    public @NotNull PageManager setStackItems(@NotNull List<ItemStack> items) {
        pages_items_itemstack = createPageStack(items);
        return this;
        // Из этого предмета
    }

    public @NotNull PageManager setItems(@NotNull List<String> items) {
        pages_items = createPageString(items);
        return this;
        // Различные списки
    }

    public @NotNull PageManager setRecipes(@NotNull List<Recipe> recipes) {
        int i = 1;
        for (var recipe : recipes) {
            pages_recipes.put(i, recipe);
            i++;
        }
        return this;
        // Печка и верстак
    }

    private @NotNull HashMap<Integer, List<String>> createPageString(@NotNull List<String> fullList) {
        var result = new HashMap<Integer, List<String>>();
        var tmpList = new ArrayList<String>();
        int itemsCount = 0;
        int pageNum = 1;
        for (var item : fullList) {
            tmpList.add(item);
            result.put(pageNum, new ArrayList<>(tmpList));
            if (itemsCount == 35) {
                pageNum++;
                itemsCount = 0;
                tmpList.clear();
            }
            itemsCount++;
        }
        return result;
    }

    private @NotNull HashMap<Integer, List<ItemStack>> createPageStack(@NotNull List<ItemStack> fullList) {
        var result = new HashMap<Integer, List<ItemStack>>();
        var tmpList = new ArrayList<ItemStack>();
        int itemsCount = 0;
        int pageNum = 1;
        for (var item : fullList) {
            tmpList.add(item);
            result.put(pageNum, new ArrayList<>(tmpList));
            if (itemsCount == 35) {
                pageNum++;
                itemsCount = 0;
                tmpList.clear();
            }
            itemsCount++;
        }
        return result;
    }

    public boolean hasPage(int page) {
        if (pageType == PageType.FROM_THIS_ITEM) {
            return pages_items_itemstack.containsKey(page);
        }
        if (pageType == PageType.CRAFT) {
            return pages_recipes.containsKey(page);
        }
        return pages_items.containsKey(page);
    }

    public @Nullable Recipe getPageRecipe(int page) {
        return pages_recipes.get(page);
    }

    public @NotNull List<ItemStack> getPageItemStackList(int page) {
        var result = pages_items_itemstack.get(page);
        if (result != null) return result;
        return new ArrayList<>();
    }

    public @NotNull List<String> getPageList(int page) {
        var result = pages_items.get(page);
        if (result != null) return result;
        return new ArrayList<>();
    }
}
