package kiinse.plugins.ggo.gungalewiki.data.interfaces;

import org.jetbrains.annotations.NotNull;

public interface TotemsData {

    boolean hasItem(@NotNull String item);

    @NotNull String getTotem(@NotNull String item);

    int getDelay();
}
