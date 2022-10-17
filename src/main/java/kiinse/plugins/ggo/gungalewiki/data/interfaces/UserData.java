package kiinse.plugins.ggo.gungalewiki.data.interfaces;

import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UserData {

    @NotNull Player getPlayer();

    @NotNull List<String> getBookmarks();

    boolean hasItemInBookmarks(@NotNull String item);

    @NotNull UserData addToBookmarks(@NotNull String item);

    @NotNull UserData removeFromBookmarks(@NotNull String item);

    @NotNull UserData addToLastSeen(@NotNull String item);

    @NotNull List<String> getLastSeen();

    @NotNull UserData saveLastGui(@NotNull CreatedGui createdGui);

    @NotNull CreatedGui getLastGui();

    void save();
}
