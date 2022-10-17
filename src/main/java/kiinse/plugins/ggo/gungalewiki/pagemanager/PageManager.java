package kiinse.plugins.ggo.gungalewiki.pagemanager;

import kiinse.plugins.ggo.gungalewiki.enums.PageType;
import kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces.WikiPageManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageManager implements WikiPageManager {


    private final PageType pageType;
    private final HashMap<Integer, Recipe> pagesRecipes = new HashMap<>();
    private final HashMap<Integer, String> dropItems = new HashMap<>();
    private HashMap<Integer, List<ItemStack>> pagesItemsItemstack = new HashMap<>();
    private HashMap<Integer, List<String>> pagesItems = new HashMap<>();

    public PageManager(@NotNull PageType pageType) {
        this.pageType = pageType;
    }

    @Override
    public @NotNull PageManager setStackItems(@NotNull List<ItemStack> items) {
        pagesItemsItemstack = createPageStack(items);
        return this;
    }

    @Override
    public @NotNull PageManager setOreItems(@NotNull List<String> items) {
        int i = 0;
        for (var item : items) {
            dropItems.put(i, item);
            i++;
        }
        return this;
    }

    @Override
    public @NotNull PageManager setItems(@NotNull List<String> items) {
        pagesItems = createPageString(items);
        return this;
    }

    @Override
    public @NotNull PageManager setRecipes(@NotNull List<Recipe> recipes) {
        int i = 0;
        for (var recipe : recipes) {
            pagesRecipes.put(i, recipe);
            i++;
        }
        return this;
    }

    private @NotNull HashMap<Integer, List<String>> createPageString(@NotNull List<String> fullList) {
        var result = new HashMap<Integer, List<String>>();
        var tmpList = new ArrayList<String>();
        int itemsCount = 0;
        int pageNum = 0;
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
        int pageNum = 0;
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

    @Override
    public boolean hasPage(int page) {
        if (pageType == PageType.FROM_THIS_ITEM) return pagesItemsItemstack.containsKey(page);
        if (pageType == PageType.CRAFT) return pagesRecipes.containsKey(page);
        if (pageType == PageType.DROP) return dropItems.containsKey(page);
        return pagesItems.containsKey(page);
    }

    @Override
    public @Nullable Recipe getPageRecipe(int page) {
        return pagesRecipes.get(page);
    }

    @Override
    public @NotNull String getItem(int page) {
        return dropItems.get(page);
    }

    @Override
    public @NotNull List<ItemStack> getPageItemStackList(int page) {
        var result = pagesItemsItemstack.get(page);
        if (result != null) return result;
        return new ArrayList<>();
    }

    @Override
    public @NotNull List<String> getPageList(int page) {
        var result = pagesItems.get(page);
        if (result != null) return result;
        return new ArrayList<>();
    }
}
