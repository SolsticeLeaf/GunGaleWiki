package kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface WikiPageManager {

    @NotNull <T> WikiPageManager setItemsList(@NotNull List<T> items);

    @NotNull <T> WikiPageManager setItems(@NotNull List<T> items);

    boolean hasPage(int page);

    @NotNull Object get(int page);

    @NotNull <T> T get(int page, T type);
}
