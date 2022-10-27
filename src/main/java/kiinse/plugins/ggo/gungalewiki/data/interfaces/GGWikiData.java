package kiinse.plugins.ggo.gungalewiki.data.interfaces;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface GGWikiData {

    @NotNull UserData getUserData(@NotNull Player player);

    @NotNull GGWikiData saveData(@NotNull UserData userData);

    @NotNull GGWikiData save(@NotNull Player player);

    void saveAll();

}
