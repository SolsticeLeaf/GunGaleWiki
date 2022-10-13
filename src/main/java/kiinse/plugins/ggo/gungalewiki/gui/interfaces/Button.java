package kiinse.plugins.ggo.gungalewiki.gui.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Button {

    @NotNull List<String> getPage(int page);

    int getPageNumber();

    boolean hasPage(int page);
}
