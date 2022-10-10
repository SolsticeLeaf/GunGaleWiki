package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Button;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButtons;
import kiinse.plugins.ggo.gungalewiki.gui.items.BookMarkButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.FilterButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.LastCraftsButton;
import kiinse.plugins.ggo.gungalewiki.gui.items.PlayerHead;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MainGUI extends DarkGUI {

    // 53 slots
    private final GunGaleWiki gunGaleWiki;
    private final FiltersButtons filtersButtons;
    private final Player player;
    private final HashMap<Button, int[]> filters = new HashMap<>();

    public MainGUI(@NotNull GunGaleWiki gunGaleWiki, @NotNull Player player) {
        super(gunGaleWiki);
        this.gunGaleWiki = gunGaleWiki;
        this.filtersButtons = gunGaleWiki.getFilterButtons();
        this.player = player;
        filters.put(Button.BUTTON1, new int[] {18, 19, 20, 27, 28, 29});
        filters.put(Button.BUTTON2, new int[] {21, 22, 23, 30, 31, 32});
        filters.put(Button.BUTTON3, new int[] {24, 25, 26, 33, 34, 35});
        filters.put(Button.BUTTON4, new int[] {36, 37, 38, 45, 46, 47});
        filters.put(Button.BUTTON5, new int[] {39, 40, 41, 48, 49, 50});
        filters.put(Button.BUTTON6, new int[] {42, 43, 44, 51, 52, 53});
    }

    @Override
    protected void inventory(@NotNull DarkWaterJavaPlugin darkWaterJavaPlugin) {
        setItem(new PlayerHead(player));
        for (var pos : new int[] {0, 1, 2, 9, 10, 11}) {
            setItem(new LastCraftsButton(getPlayerLocale(), gunGaleWiki, this, pos));
        }
        for (var pos : new int[] {6, 7, 8, 15, 16, 17}) {
            setItem(new BookMarkButton(getPlayerLocale(), gunGaleWiki, this, pos));
        }
        for (var set : filters.entrySet()) {
            var button = filtersButtons.getButton(set.getKey(), getPlayerLocale());
            for (var pos : set.getValue()) {
                setItem(new FilterButton(button, gunGaleWiki, this, pos));
            }
        }
    }
}
