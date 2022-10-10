package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.BookMarks;
import kiinse.plugins.ggo.gungalewiki.gui.items.BackButton;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookMarksGUI extends DarkGUI implements BookMarks {

    private final GunGaleWiki gunGaleWiki;
    private final Player openedPlayer;
    public final List<String> items;

    public BookMarksGUI(@NotNull GunGaleWiki gunGaleWiki, @NotNull Player player, @NotNull List<String> items) {
        super(gunGaleWiki);
        this.gunGaleWiki = gunGaleWiki;
        this.openedPlayer = player;
        this.items = items;
    }

    @Override
    protected void inventory(@NotNull DarkWaterJavaPlugin darkWaterJavaPlugin) {
        setItem(new BackButton(49, ((clickType, player) -> {
            delete();
            //TODO: Сделать открытие предыдущего
        })));
    }
}
