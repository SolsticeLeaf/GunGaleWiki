package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungalewiki.enums.Gui;
import kiinse.plugins.ggo.gungalewiki.enums.PageType;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButton;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.managers.pagemanager.PageManager;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FilterButton implements GuiItem {

    private final ItemStack itemStack;
    private final String menuName;
    private final CreatedGui lastGui;
    private final List<String> items;
    private final int slot;

    public FilterButton(@NotNull FiltersButton filtersButton,
                        @NotNull CreatedGui lastGui, int slot) {
        this.menuName = filtersButton.getMenuName();
        this.lastGui = lastGui;
        this.slot = slot;
        this.itemStack = filtersButton.getItemStack();
        this.items = filtersButton.getItems();
    }

    @Override
    public int slot() {
        return slot;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public @NotNull String name() {
        return itemStack.getDisplayName();
    }

    @Override
    public @NotNull GuiAction action() {
        return ((clickType, player) -> {
            lastGui.delete();
            new GuiBuilder(player)
                    .getGui(Gui.ITEMS)
                    .setPageManager(new PageManager(PageType.ITEMS).setItems(items))
                    .setLastGui(lastGui)
                    .setName(menuName)
                    .open(player);
        });
    }
}
