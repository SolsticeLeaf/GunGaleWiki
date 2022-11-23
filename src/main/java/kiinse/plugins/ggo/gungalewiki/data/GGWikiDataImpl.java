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
        var uuid = player.getUniqueId();
        if (userDataHashMap.containsKey(uuid)) return userDataHashMap.get(uuid);
        return isCoreEnabled ? new CoreUserData(player) : new NotCoreUserData(player);
    }

    @Override
    public @NotNull GGWikiData saveData(@NotNull UserData userData) {
        var uuid = userData.getPlayer().getUniqueId();
        userDataHashMap.remove(uuid);
        userDataHashMap.put(uuid, userData);
        return this;
    }

    @Override
    public @NotNull GGWikiData save(@NotNull Player player) {
        var uuid = player.getUniqueId();
        if (userDataHashMap.containsKey(uuid)) {
            userDataHashMap.get(uuid).save();
            userDataHashMap.remove(uuid);
        }
        return this;
    }


    @Override
    public void saveAll() {
        userDataHashMap.forEach((uuid, userData) -> userData.save());
    }
}
