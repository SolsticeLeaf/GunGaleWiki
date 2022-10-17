package kiinse.plugins.ggo.gungalewiki.data;

import kiinse.plugins.ggo.gungalewiki.data.impl.CoreUserData;
import kiinse.plugins.ggo.gungalewiki.data.impl.NotCoreUserData;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.GGWikiData;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.UserData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class GGWikiDataImpl implements GGWikiData {

    private final boolean isCoreEnabled;
    private final HashMap<UUID, UserData> userDataHashMap;

    public GGWikiDataImpl(boolean isCoreEnabled) {
        this.userDataHashMap = new HashMap<>();
        this.isCoreEnabled = isCoreEnabled;
    }

    @Override
    public @NotNull UserData getUserData(@NotNull Player player) {
        if (isCoreEnabled) return new CoreUserData(player);
        var uuid = player.getUniqueId();
        if (userDataHashMap.containsKey(uuid)) return userDataHashMap.get(uuid);
        return new NotCoreUserData(player);
    }

    @Override
    public @NotNull GGWikiData saveData(@NotNull UserData userData) {
        if (isCoreEnabled) {
            userData.save();
            return this;
        }
        var uuid = userData.getPlayer().getUniqueId();
        userDataHashMap.remove(uuid);
        userDataHashMap.put(uuid, userData);
        return this;
    }
}
