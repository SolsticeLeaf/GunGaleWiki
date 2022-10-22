package kiinse.plugins.ggo.gungalewiki.gui.builder;

import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.menus.*;
import org.jetbrains.annotations.NotNull;

public enum Gui {
    FURNACE(FurnaceGUI.class),
    WORKBENCH(WorkBenchGUI.class),
    HOME(MainGUI.class),
    LASTSEEN(LastCraftsGUI.class),
    ITEMS(ItemsGUI.class),
    FROMITEM(FromThisItemCraftGUI.class),
    ORES(OreGUI.class),
    BOOKMARKS(BookMarksGUI.class);

    private final Class<? extends CreatedGui> darkGUI;

    Gui(@NotNull Class<? extends CreatedGui> darkGUI) {
        this.darkGUI = darkGUI;
    }

    public @NotNull Class<? extends CreatedGui> get() {
        return darkGUI;
    }
}
