package kiinse.plugins.ggo.gungalewiki.enums;

import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.menus.*;
import org.jetbrains.annotations.NotNull;

public enum Gui {
    FURNACE(FurnaceGUI.class),
    WORKBENCH(WorkBenchGUI.class),
    HOME(MainGUI.class),
    LAST_SEEN(LastCraftsGUI.class),
    ITEMS(ItemsGUI.class),
    FROM_THIS_ITEM_CRAFT(FromThisItemCraftGUI.class),
    BOOKMARKS(BookMarksGUI.class);

    private final Class<? extends CreatedGui> darkGUI;

    Gui(@NotNull Class<? extends CreatedGui> darkGUI) {
        this.darkGUI = darkGUI;
    }

    public @NotNull Class<? extends CreatedGui> get() {
        return darkGUI;
    }
}
