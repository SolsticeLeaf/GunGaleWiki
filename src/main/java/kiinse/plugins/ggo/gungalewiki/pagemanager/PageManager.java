package kiinse.plugins.ggo.gungalewiki.pagemanager;

import kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces.WikiPageManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс реализующий менеджер страниц для различных меню
 */
public class PageManager implements WikiPageManager {

    private final Map<Integer, List<?>> map = new HashMap<>();

    /**
     * Метод, высчитывающий по N предметов на каждую страницу.
     * @param items Список всех предметов
     * @param amount Количество предметов на каждой странице
     */
    @Override
    public @NotNull <T> WikiPageManager setItems(@NotNull List<T> items, int amount) {
        var tmpList = new ArrayList<>();
        int itemsCount = 0;
        int pageNum = 0;
        for (var item : items) {
            itemsCount++;
            tmpList.add(item);
            map.put(pageNum, new ArrayList<>(tmpList));
            if (itemsCount == amount) {
                pageNum++;
                itemsCount = 0;
                tmpList.clear();
            }
        }
        return this;
    }

    /**
     * Возвращает true если на указанной странице есть предметы
     * @param page Номер страницы
     * @return true если на указанной странице есть предметы
     */
    @Override
    public boolean hasPage(int page) {
        return map.containsKey(page);
    }

    /**
     * Метод, возвращающий предметы на странице
     * @param page Номер страницы
     * @param type Тип возвращаемого объекта и по совместительству default значение
     * @return Предметы на странице или default значение в случае ошибки
     */
    @SuppressWarnings("unchecked")
    @Override
    public @NotNull <T> List<T> get(int page, @NotNull List<T> type) {
        if (!map.containsKey(page)) return type;
        List<T> result;
        try {
            result = (List<T>) map.get(page);
        } catch (Exception ignored) {
            result = type;
        }
        return result;
    }
}
