package kiinse.plugins.ggo.gungalewiki.data.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface OresData {

    boolean hasOre(@NotNull String ore);

    boolean hasDrop(@NotNull String drop);

    @NotNull List<String> getOresByDrop(@NotNull String drop);

    @NotNull String getDropByOre(@NotNull String ore);

    @NotNull String getAmountByOre(@NotNull String ore);
}
