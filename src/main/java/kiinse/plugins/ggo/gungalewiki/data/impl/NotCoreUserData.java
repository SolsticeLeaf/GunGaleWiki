package kiinse.plugins.ggo.gungalewiki.data.impl;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.UserData;
import kiinse.plugins.ggo.gungalewiki.data.utils.DataUtils;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.files.buttons.Button;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NotCoreUserData implements UserData {

    private final Player player;
    private final List<String> bookmarks = new ArrayList<>();
    private final List<String> lastSeen = new ArrayList<>();
    private String lastGuiItem;
    private String lastGuiType;
    private int lastGuiPage;

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
        this.lastGuiItem = createdGui.getItem();
        this.lastGuiPage = createdGui.getPage();
        this.lastGuiType = createdGui.getType().toString();
        return this;
    }

    @Override
    public @NotNull CreatedGui getLastGui() {
        if (lastGuiType == null) {
            return GuiUtils.getMainGui(player);
        }
        var type = Gui.valueOf(lastGuiType);
        try {
            var gunGaleWiki = GunGaleWiki.getInstance();
            var button = Button.valueOf(lastGuiItem);
            var filterButton = gunGaleWiki.getFilterButtons().getButton(button, gunGaleWiki.getGunGaleAPI().getPlayerLocales().getLocale(player));
            return new GuiBuilder(player)
                    .setItem(button.toString())
                    .setPage(lastGuiPage)
                    .setGui(Gui.ITEMS)
                    .setType(type)
                    .setStringItem(button.toString())
                    .setPageManager(new PageManager().setItemsList(filterButton.getItems()))
                    .setLastGui(GuiUtils.getMainGui(player))
                    .setGuiName(filterButton.getMenuName());
        } catch (Exception ignored) {
        }
        return new GuiBuilder(player)
                .setItem(lastGuiItem)
                .setPage(lastGuiPage)
                .setGui(type)
                .setType(type)
                .setLastGui(GuiUtils.getMainGui(player))
                .setStringItem(lastGuiItem)
                .setPageManager(DataUtils.getPageManager(type, lastGuiItem, player, getBookmarks(), getLastSeen()))
                .setGuiName(GunGaleWiki.getInstance().getConfiguration().getString(Config.valueOf("MENU_" + type + "_NAME")));
    }

    @Override
    public void save() {}
}
