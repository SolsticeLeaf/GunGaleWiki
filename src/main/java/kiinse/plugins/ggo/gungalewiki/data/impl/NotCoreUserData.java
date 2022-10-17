package kiinse.plugins.ggo.gungalewiki.data.impl;

import kiinse.plugins.ggo.gungalewiki.data.interfaces.UserData;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NotCoreUserData implements UserData {

    private final Player player;
    private final List<String> bookmarks = new ArrayList<>();
    private final List<String> lastSeen = new ArrayList<>();

    public NotCoreUserData(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull List<String> getBookmarks() {
        return new ArrayList<>(bookmarks);
    }

    @Override
    public @NotNull List<String> getLastSeen() {
        return new ArrayList<>(lastSeen);
    }

    @Override
    public boolean hasItemInBookmarks(@NotNull String item) {
        return bookmarks.contains(item);
    }

    @Override
    public @NotNull UserData addToBookmarks(@NotNull String item) {
        bookmarks.add(item);
        return this;
    }

    @Override
    public @NotNull UserData removeFromBookmarks(@NotNull String item) {
        bookmarks.remove(item);
        return this;
    }

    @Override
    public @NotNull UserData addToLastSeen(@NotNull String item) {
        if (lastSeen.isEmpty()) {
            lastSeen.add(item);
        } else {
            if (!lastSeen.get(lastSeen.size() - 1).equals(item)) {
                if (lastSeen.size() == 36) {
                    lastSeen.remove(0);
                }
                lastSeen.add(item);
            }
        }
        return this;
    }

    @Override
    public @NotNull UserData saveLastGui(@NotNull CreatedGui createdGui) {
        return this;
    }

    @Override
    public @NotNull CreatedGui getLastGui() {
        return GuiUtils.getMainGui(player);
    }

    @Override
    public void save() {}
}
