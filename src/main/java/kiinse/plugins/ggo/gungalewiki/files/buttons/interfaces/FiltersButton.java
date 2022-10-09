package kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FiltersButton {

    @NotNull String getName();

    @NotNull List<String> getItems();
}
