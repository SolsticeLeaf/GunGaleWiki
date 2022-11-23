package kiinse.plugins.ggo.gungalewiki.data.utils;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.files.buttons.Button;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces.WikiPageManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class DataUtils {

    private DataUtils() {}

    public static @NotNull WikiPageManager getPageManager(@NotNull Gui gui, @NotNull String item, @NotNull Player player,
                                                          @NotNull List<String> bookmarks, @NotNull List<String> lastSeen) {
        var gunGaleWiki = GunGaleWiki.getInstance();
        switch (gui) {
            case FURNACE, WORKBENCH -> {
                return new PageManager().setItems(GuiUtils.getRecipes(item));
            }
            case ITEMS -> {
                var filtersButtons = gunGaleWiki.getFilterButtons();
                return new PageManager().setItemsList(
                        filtersButtons.getButton(Button.valueOf(item), gunGaleWiki.getGunGaleAPI().getPlayerLocales().getLocale(player)).getItems());
            }
            case FROMITEM -> {
                return new PageManager().setItemsList(GuiUtils.getItemsFromThis(item));
            }
            case BOOKMARKS -> {
                Collections.reverse(bookmarks);
                return new PageManager().setItemsList(bookmarks);
            }
            case ORES -> {
                return new PageManager().setItems(gunGaleWiki.getOresData().getOresByDrop(item));
            }
            default -> {
                return new PageManager().setItemsList(lastSeen);
            }
        }
    }
}
