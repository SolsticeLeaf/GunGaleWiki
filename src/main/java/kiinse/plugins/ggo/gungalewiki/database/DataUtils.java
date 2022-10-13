package kiinse.plugins.ggo.gungalewiki.database;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.PluginData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DataUtils implements PluginData {

    private final HashMap<UUID, List<String>> bookmarks = new HashMap<>();
    private final HashMap<UUID, List<String>> lastSeen = new HashMap<>();
    private final GunGaleJavaPlugin plugin;

    public DataUtils(@NotNull Connection connection, @NotNull GunGaleJavaPlugin plugin) {
        this.plugin = plugin;
    }

    public DataUtils(@NotNull GunGaleJavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull PluginData loadFromDB() {
        return null;
    }

    @Override
    public @NotNull PluginData saveToDB() {
        return null;
    }

    @Override
    public @NotNull List<String> getPlayerBookmarks(@NotNull Player player) {
        var uuid = player.getUniqueId();
        if (bookmarks.containsKey(uuid)) return bookmarks.get(uuid);
        return new ArrayList<>();
    }

    @Override
    public @NotNull List<String> getPlayerLastSeen(@NotNull Player player) {
        var uuid = player.getUniqueId();
        if (lastSeen.containsKey(uuid)) return lastSeen.get(uuid);
        return new ArrayList<>();
    }

    @Override
    public boolean hasPlayerItemInBookmarks(@NotNull Player player, @NotNull String item) {
        return getPlayerBookmarks(player).contains(item);
    }

    @Override
    public @NotNull PluginData addToPlayerBookmarks(@NotNull Player player, @NotNull String item) {
        var marks = getPlayerBookmarks(player);
        marks.add(item);
        bookmarks.put(player.getUniqueId(), marks);
        return this;
    }

    @Override
    public @NotNull PluginData removeFromPlayerBookmarks(@NotNull Player player, @NotNull String item) {
        var marks = getPlayerBookmarks(player);
        marks.remove(item);
        bookmarks.put(player.getUniqueId(), marks);
        return this;
    }

    @Override
    public @NotNull PluginData addToPlayerLastSeen(@NotNull Player player, @NotNull String item) {
        //TODO: Оптимизировать
        var playerLastSeen = getPlayerLastSeen(player);
        playerLastSeen.remove(item);
        if (playerLastSeen.size() >= 35) {
            var newArr = new ArrayList<String>();
            for (var i = 1; i < playerLastSeen.size(); i++) {
                newArr.add(playerLastSeen.get(i));
            }
            playerLastSeen = newArr;
        }
        playerLastSeen.add(item);
        lastSeen.put(player.getUniqueId(), playerLastSeen);
        return this;
    }

}
