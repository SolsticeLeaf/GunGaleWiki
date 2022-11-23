package kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Класс реализующий менеджер страниц для различных меню
 */
public interface WikiPageManager {

    /**
     * Метод, высчитывающий по N предметов на каждую страницу
     * @param items Список всех предметов
     * @param amount Количество предметов на каждой странице
     */
    @NotNull <T> WikiPageManager setItems(@NotNull List<T> items, int amount);

    /**
     * Возвращает true если на указанной странице есть предметы
     * @param page Номер страницы
     * @return true если на указанной странице есть предметы
     */
    boolean hasPage(int page);

    /**
     * Метод, возвращающий предметы на странице
     * @param page Номер страницы
     * @param type Тип возвращаемого объекта и по совместительству default значение
     * @return Предметы на странице или default значение в случае ошибки
     */
    @NotNull <T> List<T> get(int page, @NotNull List<T> type);
}
