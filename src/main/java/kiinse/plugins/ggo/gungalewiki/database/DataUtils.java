package kiinse.plugins.ggo.gungalewiki.database;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.PluginData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.*;

public class DataUtils implements PluginData {

    private final HashMap<UUID, List<String>> bookmarks = new HashMap<>();
    private final HashMap<UUID, List<String>> lastseen = new HashMap<>();
    private final Connection connection;
    private final DarkWaterJavaPlugin plugin;

    public DataUtils(@NotNull Connection connection, @NotNull DarkWaterJavaPlugin plugin) {
        this.connection = connection;
        this.plugin = plugin;
    }

    @Override
    public @NotNull PluginData loadFromDB() {
        return null;
        //TODO: Реализовать загрузку и сейв
    }

    @Override
    public @NotNull PluginData saveToDB() {
        return null;
    }

    @Override
    public @NotNull List<String> getPlayerBookmarks(@NotNull Player player) {
        var uuid = player.getUniqueId();
        if (!bookmarks.containsKey(uuid)) bookmarks.put(uuid, new ArrayList<>());
        return bookmarks.get(uuid);
    }

    @Override
    public @NotNull List<String> getPlayerLastSeen(@NotNull Player player) {
        var uuid = player.getUniqueId();
        if (!lastseen.containsKey(uuid)) lastseen.put(uuid, new ArrayList<>());
        return lastseen.get(uuid);
    }

    @Override
    public boolean hasPlayerItemInBookmarks(@NotNull Player player, @NotNull String item) {
        return getPlayerBookmarks(player).contains(item);
    }

    @Override
    public @NotNull PluginData addToPlayerBookmarks(@NotNull Player player, @NotNull String item) {
        var marks = getPlayerBookmarks(player);
        marks.add(item);
        updateBookmarks(player.getUniqueId(), marks);
        return this;
    }

    @Override
    public @NotNull PluginData removeFromPlayerBookmarks(@NotNull Player player, @NotNull String item) {
        var marks = getPlayerBookmarks(player);
        marks.remove(item);
        updateBookmarks(player.getUniqueId(), marks);
        return this;
    }

    @Override
    public @NotNull PluginData addToPlayerLastSeen(@NotNull Player player, @NotNull String item) {
        //TODO: Оптимизировать
        var playerLastSeen = getPlayerLastSeen(player);
        if (playerLastSeen.size() >= 35) {
            var newArr = new ArrayList<String>();
            for (var i = 1; i < playerLastSeen.size(); i++) {
                newArr.add(playerLastSeen.get(i));
            }
            playerLastSeen = newArr;
        }
        playerLastSeen.add(item);
        updateLastSeen(player.getUniqueId(), playerLastSeen);
        return this;
    }

    private void updateLastSeen(@NotNull UUID player, @NotNull List<String> list) {
        lastseen.remove(player);
        lastseen.put(player, list);
    }

    private void updateBookmarks(@NotNull UUID player, @NotNull List<String> list) {
        bookmarks.remove(player);
        bookmarks.put(player, list);
    }
}
