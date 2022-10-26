package kiinse.plugins.ggo.gungalewiki.data.impl;

import com.github.danirod12.gungalecore.api.GGCoreProvider;
import com.github.danirod12.gungalecore.api.stat.VPWikiData;
import com.github.danirod12.gungalecore.api.user.GGUser;
import com.github.danirod12.gungalecore.api.user.PlayerStat;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.UserData;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.files.buttons.Button;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces.WikiPageManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class CoreUserData implements UserData {

    private final Player player;
    private final GGUser user;
    private final VPWikiData vpWikiData;

    public CoreUserData(@NotNull Player player) {
        this.player = player;
        this.user = GGCoreProvider.get().getUserManager().getPlayer(DarkPlayerUtils.getPlayerName(player));
        this.vpWikiData = user.getStat(PlayerStat.VP_WIKI_DATA);
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull List<String> getBookmarks() {
        return vpWikiData.getBookmarks();
    }

    @Override
    public boolean hasItemInBookmarks(@NotNull String item) {
        return vpWikiData.hasItemInBookmarks(item);
    }

    @Override
    public @NotNull UserData addToBookmarks(@NotNull String item) {
        vpWikiData.addToBookmarks(item);
        return this;
    }

    @Override
    public @NotNull UserData removeFromBookmarks(@NotNull String item) {
        vpWikiData.removeFromBookmarks(item);
        return this;
    }

    @Override
    public @NotNull UserData addToLastSeen(@NotNull String item) {
        vpWikiData.addToLastSeen(item);
        return this;
    }

    @Override
    public @NotNull List<String> getLastSeen() {
        return vpWikiData.getLastSeen();
    }

    @Override
    public @NotNull UserData saveLastGui(@NotNull CreatedGui createdGui) {
        vpWikiData.setLastGui(new VPWikiData.LastGUI(createdGui.getType().toString(), createdGui.getItem(), createdGui.getPage()));
        return this;
    }

    @Override
    public @NotNull CreatedGui getLastGui() {
        var last = vpWikiData.getLastGui();
        var type = Gui.valueOf(last.getType());
        try {
            var gunGaleWiki = GunGaleWiki.getInstance();
            var button = Button.valueOf(last.getItem().toUpperCase());
            var filterButton = gunGaleWiki.getFilterButtons().getButton(button, gunGaleWiki.getGunGaleAPI().getPlayerLocales().getLocale(player));
            return new GuiBuilder(player)
                    .setItem(button.toString())
                    .setPage(last.getPage())
                    .setGui(Gui.ITEMS)
                    .setType(type)
                    .setStringItem(button.toString())
                    .setPageManager(new PageManager().setItems(filterButton.getItems()))
                    .setLastGui(GuiUtils.getMainGui(player))
                    .setGuiName(filterButton.getMenuName());
        } catch (Exception ignored) {
        }
        return new GuiBuilder(player)
                .setItem(last.getItem())
                .setPage(last.getPage())
                .setGui(type)
                .setType(type)
                .setLastGui(GuiUtils.getMainGui(player))
                .setStringItem(last.getItem())
                .setPageManager(getPageManager(type, last.getItem()))
                .setGuiName(GunGaleWiki.getInstance().getConfiguration().getString(Config.valueOf("MENU_" + type + "_NAME")));
    }

    private @NotNull WikiPageManager getPageManager(@NotNull Gui gui, @NotNull String item) {
        var gunGaleWiki = GunGaleWiki.getInstance();
        switch (gui) {
            case FURNACE, WORKBENCH -> {
                return new PageManager().setRecipes(GuiUtils.getRecipes(item));
            }
            case ITEMS -> {
                var filtersButtons = gunGaleWiki.getFilterButtons();
                return new PageManager().setItems(
                        filtersButtons.getButton(Button.valueOf(item), gunGaleWiki.getGunGaleAPI().getPlayerLocales().getLocale(player)).getItems());
            }
            case FROMITEM -> {
                return new PageManager().setStackItems(GuiUtils.getItemsFromThis(item));
            }
            case BOOKMARKS -> {
                var bookmarks = gunGaleWiki.getPluginData().getUserData(player).getBookmarks();
                Collections.reverse(bookmarks);
                return new PageManager().setItems(getBookmarks());
            }
            default -> {
                return new PageManager().setItems(getLastSeen());
            }
        }
    }

    @Override
    public void save() {
        user.updateStat(PlayerStat.VP_WIKI_DATA, vpWikiData);
    }
}
