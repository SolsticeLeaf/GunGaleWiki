package kiinse.plugins.ggo.gungalewiki.database.interfaces;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PluginData {

    @NotNull PluginData loadFromDB();

    @NotNull PluginData saveToDB();

    @NotNull List<String> getPlayerBookmarks(@NotNull Player player);

    boolean hasPlayerItemInBookmarks(@NotNull Player player, @NotNull String item);

    @NotNull PluginData addToPlayerBookmarks(@NotNull Player player, @NotNull String item);

    @NotNull PluginData removeFromPlayerBookmarks(@NotNull Player player, @NotNull String item);

    @NotNull PluginData addToPlayerLastSeen(@NotNull Player player, @NotNull String item);

    @NotNull List<String> getPlayerLastSeen(@NotNull Player player);
}
