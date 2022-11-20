package kiinse.plugins.ggo.gungalewiki.pagemanager;

import kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces.WikiPageManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageManager implements WikiPageManager {

    private HashMap<Integer, ?> hashMap = new HashMap<>();

    @Override
    public @NotNull WikiPageManager setStackItems(@NotNull List<ItemStack> items) {
        hashMap = createPageStack(items);
        return this;
    }

    @Override
    public @NotNull WikiPageManager setOreItems(@NotNull List<String> items) {
        var map = new HashMap<Integer, String>();
        int i = 0;
        for (var item : items) {
            map.put(i, item);
            i++;
        }
        this.hashMap = map;
        return this;
    }

    @Override
    public @NotNull WikiPageManager setItems(@NotNull List<String> items) {
        hashMap = createPageString(items);
        return this;
    }

    @Override
    public @NotNull WikiPageManager setRecipes(@NotNull List<Recipe> recipes) {
        var map = new HashMap<Integer, Recipe>();
        int i = 0;
        for (var recipe : recipes) {
            map.put(i, recipe);
            i++;
        }
        this.hashMap = map;
        return this;
    }

    private @NotNull HashMap<Integer, List<String>> createPageString(@NotNull List<String> fullList) {
        var result = new HashMap<Integer, List<String>>();
        var tmpList = new ArrayList<String>();
        int itemsCount = 0;
        int pageNum = 0;
        for (var item : fullList) {
            itemsCount++;
            tmpList.add(item);
            result.put(pageNum, new ArrayList<>(tmpList));
            if (itemsCount == 36) {
                pageNum++;
                itemsCount = 0;
                tmpList.clear();
            }
        }
        return result;
    }

    private @NotNull HashMap<Integer, List<ItemStack>> createPageStack(@NotNull List<ItemStack> fullList) {
        var result = new HashMap<Integer, List<ItemStack>>();
        var tmpList = new ArrayList<ItemStack>();
        int itemsCount = 0;
        int pageNum = 0;
        for (var item : fullList) {
            itemsCount++;
            tmpList.add(item);
            result.put(pageNum, new ArrayList<>(tmpList));
            if (itemsCount == 36) {
                pageNum++;
                itemsCount = 0;
                tmpList.clear();
            }
        }
        return result;
    }

    @Override
    public boolean hasPage(int page) {
        return hashMap.containsKey(page);
    }

    @Override
    public @Nullable Recipe getPageRecipe(int page) {
        return (Recipe) hashMap.get(page);
    }

    @Override
    public @NotNull String getItem(int page) {
        return (String) hashMap.get(page);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull List<ItemStack> getPageItemStackList(int page) {
        var result = (List<ItemStack>) hashMap.get(page);
        if (result != null) return result;
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull List<String> getPageList(int page) {
        var result = (List<String>) hashMap.get(page);
        if (result != null) return result;
        return new ArrayList<>();
    }
}
