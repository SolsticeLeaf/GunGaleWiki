package kiinse.plugins.ggo.gungalewiki.gui.builder;

import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.menus.*;
import org.jetbrains.annotations.NotNull;

public enum Gui {
    FURNACE(FurnaceGUI.class), // Рецепт в печке
    WORKBENCH(WorkBenchGUI.class), // Рецепт в верстаке
    HOME(MainGUI.class), // Домашнее меню
    LASTSEEN(LastCraftsGUI.class), // Последние просмотренные
    ITEMS(ItemsGUI.class), // Список предметов (В фильтрах)
    FROMITEM(FromThisItemCraftGUI.class), // Список крафтов из предмета
    ORES(OreGUI.class), // Меню с рудой
    BOOKMARKS(BookMarksGUI.class); // Меню с закладками

    private final Class<? extends CreatedGui> darkGUI;

    Gui(@NotNull Class<? extends CreatedGui> darkGUI) {
        this.darkGUI = darkGUI;
    }

    public @NotNull Class<? extends CreatedGui> get() {
        return darkGUI;
    }
}
