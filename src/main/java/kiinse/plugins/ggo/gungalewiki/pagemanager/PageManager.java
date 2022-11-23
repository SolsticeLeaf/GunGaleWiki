package kiinse.plugins.ggo.gungalewiki.pagemanager;

import kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces.WikiPageManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageManager implements WikiPageManager {

    private HashMap<Integer, ?> hashMap = new HashMap<>();

    @Override
    public @NotNull <T> WikiPageManager setItemsList(@NotNull List<T> items) {
        var result = new HashMap<Integer, ArrayList<T>>();
        var tmpList = new ArrayList<T>();
        int itemsCount = 0;
        int pageNum = 0;
        for (var item : items) {
            itemsCount++;
            tmpList.add(item);
            result.put(pageNum, new ArrayList<>(tmpList));
            if (itemsCount == 36) {
                pageNum++;
                itemsCount = 0;
                tmpList.clear();
            }
        }
        hashMap = result;
        return this;
    }

    @Override
    public @NotNull <T> WikiPageManager setItems(@NotNull List<T> items) {
        var map = new HashMap<Integer, T>();
        int i = 0;
        for (var item : items) {
            map.put(i, item);
            i++;
        }
        this.hashMap = map;
        return this;
    }

    @Override
    public boolean hasPage(int page) {
        return hashMap.containsKey(page);
    }

    @Override
    public @NotNull Object get(int page) {
        return hashMap.get(page);
    }


    @SuppressWarnings("unchecked")
    @Override
    public @NotNull <T> T get(int page, T type) {
        if (!hashMap.containsKey(page)) return type;
        T result;
        try {
            result = (T) hashMap.get(page);
        } catch (Exception ignored) {
            result = type;
        }
        return result;
    }
}
